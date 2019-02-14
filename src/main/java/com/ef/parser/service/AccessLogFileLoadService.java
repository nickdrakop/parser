/**
 @author nick.drakopoulos
 */
package com.ef.parser.service;

import com.ef.parser.dao.AccessLogDao;
import com.ef.parser.data.AccessLogEntity;
import com.ef.parser.mapper.AccessLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AccessLogFileLoadService {
    private static final Logger LOG = LoggerFactory.getLogger(AccessLogFileLoadService.class);

    private final AccessLogDao accessLogDao;

    private final AccessLogCsvReader accessLogCsvReader;

    private final AccessLogMapper accessLogMapper;

    @Autowired
    public AccessLogFileLoadService(AccessLogDao accessLogDao, AccessLogCsvReader accessLogCsvReader, AccessLogMapper accessLogMapper) {
        this.accessLogDao = accessLogDao;
        this.accessLogCsvReader = accessLogCsvReader;
        this.accessLogMapper = accessLogMapper;
    }

    public void loadFileToDB(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            List<AccessLogEntity> accessLogEntries = stream
                    .map(accessLogCsvReader::loadEntry)
                    .map(accessLogMapper::mapToAccessLogEntity)
                    .collect(Collectors.toList());

            accessLogDao.createMany(accessLogEntries);
        } catch (Exception e) {
            LOG.error("Error when tried to load the whole file to db", e);
        }
    }
}
