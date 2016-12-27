package com.dexcom.clarity.models;

import java.time.LocalTime;

/**
 * Created by chris on 12/23/16.
 */
public class GlucoseTargetRange {
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private GlucoseRange glucoseRange;

    public GlucoseTargetRange() {
        // no-args constructor
    }
}
