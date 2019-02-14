/**
 @author nick.drakopoulos
 */
package com.ef.parser.service;

import com.ef.parser.dao.BlockedIpDao;
import com.ef.parser.data.BlockedIpEntity;
import com.ef.parser.domain.DurationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Component
public class BlockedIpService {

    private final BlockedIpDao blockedIpDao;

    @Autowired
    public BlockedIpService(BlockedIpDao blockedIpDao) {
        this.blockedIpDao = blockedIpDao;
    }

    public void storeBlockedIps (Map<String, Long> ipsExceedingThreshold,
                                 Instant startDate,
                                 DurationType durationType,
                                 Integer threshold) {
        ipsExceedingThreshold.forEach((key, value) ->
                blockedIpDao.create(constructBlockedIpEntity(key, value, startDate, durationType, threshold)));
    }

    private BlockedIpEntity constructBlockedIpEntity (String ipAddress,
                                                      Long requestsNumber,
                                                      Instant startDate,
                                                      DurationType durationType,
                                                      Integer threshold) {
        Instant endDate = startDate.plus(durationType.hours, ChronoUnit.HOURS);
        String reason = String.format("IP %s has been blocked cause there were %s requests from %s up to %s while the limit is %s",
                ipAddress,
                requestsNumber,
                startDate,
                endDate,
                threshold);
        return new BlockedIpEntity(ipAddress, requestsNumber, startDate, endDate, reason);
    }
}
