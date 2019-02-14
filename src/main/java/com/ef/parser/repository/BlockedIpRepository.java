/**
 @author nick.drakopoulos
 */
package com.ef.parser.repository;

import com.ef.parser.data.BlockedIpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedIpRepository extends JpaRepository<BlockedIpEntity, Integer> {
}
