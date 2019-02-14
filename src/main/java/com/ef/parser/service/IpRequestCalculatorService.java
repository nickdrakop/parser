/**
 @author nick.drakopoulos
 */
package com.ef.parser.service;

import com.ef.parser.domain.AccessLogEntry;
import com.ef.parser.domain.DurationType;
import com.ef.parser.exception.AppError;
import com.ef.parser.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class IpRequestCalculatorService {
    private static final Logger LOG = LoggerFactory.getLogger(IpRequestCalculatorService.class);

    private final AccessLogCsvReader accessLogCsvReader;

    private final BlockedIpService blockedIpService;

    @Autowired
    public IpRequestCalculatorService(AccessLogCsvReader accessLogCsvReader,
                                      BlockedIpService blockedIpService) {
        this.accessLogCsvReader = accessLogCsvReader;
        this.blockedIpService = blockedIpService;
    }

    public String fetchIPsExceedingThreshold(Integer threshold,
                                             String accessLogPath,
                                             Instant startDateToCheck,
                                             DurationType durationType ) {
        try {
            Map<String, Long> requestsPerIP = calculateRequestsPerIP(accessLogPath, startDateToCheck, durationType);

            Map<String, Long> ipsExceedingThreshold = filterIpsExceedingThreshold(requestsPerIP, threshold);
            blockedIpService.storeBlockedIps(ipsExceedingThreshold, startDateToCheck, durationType, threshold);

            return mapToResponse(ipsExceedingThreshold);
        } catch (ApplicationException e) {
            LOG.error("An application exception occurred during parsing: ", e);
            return e.getAppError().getDescription();
        } catch (Exception e) {
            LOG.error("A general error occurred during parsing: ", e);
            return AppError.GENERAL_ERROR.getDescription();
        }
    }

    private Map<String, Long> filterIpsExceedingThreshold(Map<String, Long> requestsPerIP, Integer threshold) {
        return requestsPerIP.entrySet().stream()
                .filter(e -> e.getValue() >= threshold)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private String mapToResponse(Map<String, Long> ipsExceedingThreshold) {
        return ipsExceedingThreshold.entrySet().stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));
    }

    private Map<String, Long> calculateRequestsPerIP(String fileName,
                                                     Instant startFrom,
                                                     DurationType durationType) throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return stream
                    .map(accessLogCsvReader::loadEntry)
                    .filter(filterBasedOnDates(startFrom, durationType))
                    .collect(Collectors.groupingBy(AccessLogEntry::getIpAddress, Collectors.counting()));
        }
    }

    private Predicate<? super AccessLogEntry> filterBasedOnDates(Instant startFrom,
                                                                 DurationType durationType){
        return entry -> {
            Instant dateToCheck = entry.getDateOfAccessLog();
            Instant endTo = startFrom.plus(durationType.hours, ChronoUnit.HOURS);
            return (dateToCheck.isAfter(startFrom) || dateToCheck.equals(startFrom))
                    && dateToCheck.isBefore(endTo);
        };
    }

}
