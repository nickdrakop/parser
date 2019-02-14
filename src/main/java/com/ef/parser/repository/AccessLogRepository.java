/**
 @author nick.drakopoulos
 */
package com.ef.parser.repository;

import com.ef.parser.data.AccessLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLogEntity, Integer> {
}
