/**
 @author nick.drakopoulos
 */
package com.ef.parser.data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "blocked_ip")
public class BlockedIpEntity extends AbstractEntity<String> {

    @Id
    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name ="number_of_requests")
    private Long numberOfRequests;

    @Column(name ="requests_start_date")
    private Instant requestsStartDate;

    @Column(name ="requests_end_date")
    private Instant requestsEndDate;

    @Column(name ="blocking_reason")
    private String blockingReason;

    public BlockedIpEntity() {
    }

    public BlockedIpEntity(String ipAddress,
                           Long numberOfRequests,
                           Instant requestsStartDate,
                           Instant requestsEndDate,
                           String blockingReason) {
        this.ipAddress = ipAddress;
        this.numberOfRequests = numberOfRequests;
        this.requestsStartDate = requestsStartDate;
        this.requestsEndDate = requestsEndDate;
        this.blockingReason = blockingReason;
    }

    @Override
    public String getId() {
        return ipAddress;
    }

    @Override
    public void setId(String id) {
        this.ipAddress = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(Long numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }

    public Instant getRequestsStartDate() {
        return requestsStartDate;
    }

    public void setRequestsStartDate(Instant requestsStartDate) {
        this.requestsStartDate = requestsStartDate;
    }

    public Instant getRequestsEndDate() {
        return requestsEndDate;
    }

    public void setRequestsEndDate(Instant requestsEndDate) {
        this.requestsEndDate = requestsEndDate;
    }

    public String getBlockingReason() {
        return blockingReason;
    }

    public void setBlockingReason(String blockingReason) {
        this.blockingReason = blockingReason;
    }
}
