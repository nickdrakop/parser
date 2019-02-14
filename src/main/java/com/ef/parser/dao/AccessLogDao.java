/**
 @author nick.drakopoulos
 */
package com.ef.parser.dao;

import com.ef.parser.data.AccessLogEntity;
import com.ef.parser.repository.AccessLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccessLogDao {

    private AccessLogRepository accessLogRepository;

    @Autowired
    public AccessLogDao(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public List<AccessLogEntity> findAll() {
        return accessLogRepository.findAll();
    }

    public AccessLogEntity findOne(Integer id) {
        return accessLogRepository.findById(id).orElse(null);
    }

    public AccessLogEntity updateOrCreate(AccessLogEntity entity) {
        return accessLogRepository.save(entity);
    }

    public List<AccessLogEntity> createMany(List<AccessLogEntity> entities) {
        return accessLogRepository.saveAll(entities);
    }

    public void delete(Integer id) {
        accessLogRepository.deleteById(id);
    }
}
