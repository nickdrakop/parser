/**
 @author nick.drakopoulos
 */
package com.ef.parser.domain;

import java.util.Arrays;

public enum DurationType {
    
    DAILY(24), 
    HOURLY(1);
    
    public int hours;

    DurationType(int hours) {
        this.hours = hours;
    }

    public static boolean exists(String duration) {
        return Arrays.stream(DurationType.values())
                .anyMatch(durationType -> durationType.name().equalsIgnoreCase(duration));
    }
}
