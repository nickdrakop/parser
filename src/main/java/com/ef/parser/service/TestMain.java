/**
 @author nick.drakopoulos
 */
package com.ef.parser.service;

import com.ef.parser.domain.AccessLogEntry;
import com.ef.parser.domain.DurationType;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestMain {

    public static void main(String[] args) {
        TestMain rf = new TestMain();
        File file = new File("/home/nickdi/Downloads/access.log");
        // calling method
        long start = System.currentTimeMillis();
        //rf.reverseLines(file);
        rf.read("/home/nickdi/Downloads/access.log");
        long durationMillis = (System.currentTimeMillis() - start);
        System.out.println("File loaded successfully in "+durationMillis+" millis");


    }

    public void read(String fileName){
        AccessLogCsvReader reader = new AccessLogCsvReader(
                '|'
        );
        int lines = 0;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            //1. filter line 3
            //2. convert all content to upper case
            //3. convert it into a List
            List list = stream
                    .map(reader::loadEntry)
                    //.filter(entry -> entry)
                    .collect(Collectors.toList());
            System.out.println("lines " + list.size());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    Function<AccessLogEntry, Boolean> filterBasedOnDates(Instant startFrom, DurationType durationType){
        return entry -> {
            Instant dateToCheck = entry.getDateOfAccessLog();
            Instant endTo = startFrom.plus(durationType.hours, ChronoUnit.HOURS);
            return (dateToCheck.isAfter(startFrom) || dateToCheck.equals(startFrom))
                    && dateToCheck.isBefore(endTo);
        };
    }

    public void reverseLines(File file){
        AccessLogCsvReader reader = new AccessLogCsvReader(
                '|'
        );
        ReversedLinesFileReader object = null;
        int lines = 0;
        String currentLine;
        try {
            object = new ReversedLinesFileReader(file);
            while((currentLine = object.readLine()) != null) {
                System.out.println("Line - " + reader.loadEntry(currentLine).toString());
                lines++;
                if(lines == 500) break;
            }
            System.out.println("Total lines " + lines);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                object.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}