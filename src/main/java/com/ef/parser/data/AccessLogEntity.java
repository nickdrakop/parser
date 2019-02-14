/**
 @author nick.drakopoulos
 */
package com.ef.parser.data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "access_log")
public class AccessLogEntity extends AbstractEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name ="log_date")
    private Instant dateOfAccessLog;

    @Column(name ="ip_address")
    private String ipAddress;

    @Column(name ="request_type")
    private String requestType;

    @Column(name ="response_code")
    private String responseCode;

    @Column(name ="agent_info")
    private String agentInfo;

    public AccessLogEntity() {
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

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
}
