/**
 @author nick.drakopoulos
 */
package com.ef.parser.domain;

import com.ef.parser.jackson.InstantDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.Instant;
import java.util.Objects;

public class AccessLogEntry {

    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant dateOfAccessLog;

    private String ipAddress;

    private String requestType;

    private String responseCode;

    private String agentInfo;

    public Instant getDateOfAccessLog() {
        return dateOfAccessLog;
    }

    public void setDateOfAccessLog(Instant dateOfAccessLog) {
        this.dateOfAccessLog = dateOfAccessLog;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getAgentInfo() {
        return agentInfo;
    }

    public void setAgentInfo(String agentInfo) {
        this.agentInfo = agentInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessLogEntry that = (AccessLogEntry) o;
        return Objects.equals(dateOfAccessLog, that.dateOfAccessLog) &&
                Objects.equals(ipAddress, that.ipAddress) &&
                Objects.equals(requestType, that.requestType) &&
                Objects.equals(responseCode, that.responseCode) &&
                Objects.equals(agentInfo, that.agentInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfAccessLog, ipAddress, requestType, responseCode, agentInfo);
    }

    @Override
    public String toString() {
        return "AccessLogEntry{" +
                "dateOfAccessLog='" + dateOfAccessLog + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", requestType='" + requestType + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", agentInfo='" + agentInfo + '\'' +
                '}';
    }
}
