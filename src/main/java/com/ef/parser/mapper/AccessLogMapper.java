/**
 @author nick.drakopoulos
 */
package com.ef.parser.mapper;

import com.ef.parser.data.AccessLogEntity;
import com.ef.parser.domain.AccessLogEntry;
import org.springframework.stereotype.Component;

@Component
public class AccessLogMapper {

    public AccessLogEntity mapToAccessLogEntity(AccessLogEntry accessLogEntry) {
        AccessLogEntity accessLogEntity = new AccessLogEntity();

        accessLogEntity.setDateOfAccessLog(accessLogEntry.getDateOfAccessLog());
        accessLogEntity.setIpAddress(accessLogEntry.getIpAddress());
        accessLogEntity.setRequestType(accessLogEntry.getRequestType());
        accessLogEntity.setResponseCode(accessLogEntry.getResponseCode());
        accessLogEntity.setAgentInfo(accessLogEntry.getAgentInfo());

        return accessLogEntity;
    }

    public AccessLogEntry mapToAccessLogEntry(AccessLogEntity entity) {
        AccessLogEntry entry = new AccessLogEntry();

        entry.setDateOfAccessLog(entity.getDateOfAccessLog());
        entry.setIpAddress(entity.getIpAddress());
        entry.setRequestType(entity.getRequestType());
        entry.setResponseCode(entity.getResponseCode());
        entry.setAgentInfo(entity.getAgentInfo());

        return entry;
    }
}
