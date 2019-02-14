/**
 @author nick.drakopoulos
 */
package com.ef.parser.service;

import com.ef.parser.domain.AccessLogEntry;
import com.ef.parser.exception.AppError;
import com.ef.parser.exception.ApplicationException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AccessLogCsvReader {

    private final Character columnSeparator;

    @Autowired
    public AccessLogCsvReader(@Value("${access.log.file.column.separator}") Character columnSeparator) {
        this.columnSeparator = columnSeparator;
    }


    public AccessLogEntry loadEntry(String line) {

        CsvMapper csvMapper = createCsvMapper();

        try (MappingIterator<AccessLogEntry> mappingIterator = csvMapper
                .readerWithSchemaFor(AccessLogEntry.class)
                .with(createCsvSchema())
                .readValues(line)) {
            return mappingIterator.readAll().get(0);
        } catch (IOException e) {
            throw new ApplicationException(AppError.INVALID_FILE_FORMAT, e.getMessage());
        }
    }

    private CsvMapper createCsvMapper() {
        CsvMapper csvMapper = new CsvMapper();
        csvMapper.enable(CsvParser.Feature.TRIM_SPACES);
        return csvMapper;
    }

    private CsvSchema createCsvSchema() {
        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder().setColumnSeparator(columnSeparator);
        csvSchemaBuilder
                .addColumn("dateOfAccessLog")
                .addColumn("ipAddress")
                .addColumn("requestType")
                .addColumn("responseCode")
                .addColumn("agentInfo");

        return csvSchemaBuilder
                .build()
                .withUseHeader(false);
    }
}
