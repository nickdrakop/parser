/**
 @author nick.drakopoulos
 */
package com.ef.parser.dao;

import com.ef.parser.data.BlockedIpEntity;
import com.ef.parser.repository.BlockedIpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlockedIpDao {

    private BlockedIpRepository blockedIpRepository;

    @Autowired
    public BlockedIpDao(BlockedIpRepository blockedIpRepository) {
        this.blockedIpRepository = blockedIpRepository;
    }

    public BlockedIpEntity create(BlockedIpEntity entity) {
        return blockedIpRepository.save(entity);
    }

}
