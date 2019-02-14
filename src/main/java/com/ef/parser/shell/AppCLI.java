/**
 @author nick.drakopoulos
 */
package com.ef.parser.shell;

import com.ef.parser.domain.DurationType;
import com.ef.parser.service.AccessLogFileLoadService;
import com.ef.parser.service.IpRequestCalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Component
public class AppCLI implements CommandMarker {
    private static final Logger LOG = LoggerFactory.getLogger(AppCLI.class);

    private static final String HELP_MESSAGE_ACCESS_LOG = "The path which indicates where the file is located. In case parameter is not given, the default value will be used.";
    private static final String HELP_MESSAGE_START_DATE = "The start date of written logs the parser should check for IPs. It should be of format `yyyy-MM-dd.HH:mm:ss`. In case parameter is not given, the default value will be used.";
    private static final String HELP_MESSAGE_DURATION = "The duration must be `daily` or `hourly`. In case parameter is not given, the default value will be used.";
    private static final String HELP_MESSAGE_THRESHOLD = "Indicates how many request the IP's should have exceeded in order to be marked. In case parameter is not given, the default value will be used.";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern( "yyyy-MM-dd.HH:mm:ss" , Locale.US );
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    private final IpRequestCalculatorService ipRequestCalculatorService;
    private final AccessLogFileLoadService accessLogFileLoadService;

    private final String defaultFilePath;
    private final String defaultStartDate;
    private final DurationType defaultDurationType;
    private final Integer defaultThreshold;

    @Autowired
    public AppCLI(IpRequestCalculatorService ipRequestCalculatorService,
                  AccessLogFileLoadService accessLogFileLoadService,
                  @Value("${access.log.file.location}") String defaultFilePath,
                  @Value("${access.log.parser.start.date}") String defaultStartDate,
                  @Value("${access.log.parser.duration.type}") DurationType defaultDurationType,
                  @Value("${access.log.parser.threshold}") Integer defaultThreshold) {
        this.ipRequestCalculatorService = ipRequestCalculatorService;
        this.accessLogFileLoadService = accessLogFileLoadService;
        this.defaultFilePath = defaultFilePath;
        this.defaultStartDate = defaultStartDate;
        this.defaultDurationType = defaultDurationType;
        this.defaultThreshold = defaultThreshold;
    }

    @CliCommand(value = { "parse" }, help = "Parse an access server log file.")
    public String parse(
            @CliOption(key = "accesslog", help = HELP_MESSAGE_ACCESS_LOG) String givenPath,
            @CliOption(key = "startDate", help = HELP_MESSAGE_START_DATE) String givenStartDate,
            @CliOption(key = "duration", help = HELP_MESSAGE_DURATION) String givenDuration,
            @CliOption(key = "threshold", help = HELP_MESSAGE_THRESHOLD) String givenThreshold
            ) {
        long start = System.currentTimeMillis();
        String result = ipRequestCalculatorService.fetchIPsExceedingThreshold(
                getThreshold(givenThreshold),
                getPath(givenPath),
                parseDate(givenStartDate),
                getDurationType(givenDuration));

        long durationMillis = (System.currentTimeMillis() - start);
        LOG.info("Process finished in {} millis", durationMillis);

        return result;
    }

    @CliCommand(value = { "load-file" }, help = "Parse an access server log file and load it to DB.")
    public void loadFile(
            @CliOption(key = "accesslog", help = HELP_MESSAGE_ACCESS_LOG) String givenPath
            ) {
        long start = System.currentTimeMillis();
        accessLogFileLoadService.loadFileToDB(getPath(givenPath));

        long durationMillis = (System.currentTimeMillis() - start);
        LOG.info("Process finished in {} millis", durationMillis);
    }

    private String getPath(String givenPath) {
        if(!Optional.ofNullable(givenPath).isPresent()) {
            LOG.info("accesslog was not given. The default value: {} will be used.", defaultFilePath);
            return defaultFilePath;
        }
        return givenPath;
    }

    private Instant parseDate(String givenDate) {
        String dateString = givenDate;
        if(!Optional.ofNullable(givenDate).isPresent()) {
            LOG.info("startDate was not given. The default value: {} will be used.", defaultStartDate);
            dateString = defaultStartDate;
        }
        return LocalDateTime.parse(dateString, DATE_TIME_FORMATTER).atZone(ZONE_ID).toInstant();
    }

    private DurationType getDurationType(String givenDuration) {
        if(!Optional.ofNullable(givenDuration).isPresent()) {
            LOG.info("duration was not given. The default value: {} will be used.", defaultDurationType.name());
            return defaultDurationType;
        }
        if (!DurationType.exists(givenDuration.toUpperCase())) {
            LOG.info("duration that was given does not exist as a type. The default value: {} will be used.", defaultDurationType.name());
            return defaultDurationType;
        }
        return DurationType.valueOf(givenDuration.toUpperCase());
    }

    private Integer getThreshold(String givenThreshold) {
        if(!Optional.ofNullable(givenThreshold).isPresent()) {
            LOG.info("threshold was not given. The default value: {} will be used.", defaultThreshold);
            return defaultThreshold;
        }
        try {
            return Integer.valueOf(givenThreshold);
        } catch (Exception e) {
            LOG.info("threshold that was given is not a number. The default value: {} will be used.", defaultThreshold);
            return defaultThreshold;
        }
    }
}
