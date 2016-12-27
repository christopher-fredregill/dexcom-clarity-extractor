package com.dexcom.clarity.models;

import java.time.LocalDateTime;

/**
 * Created by chris on 12/23/16.
 */
public class TimeMagnitude {
    private LocalDateTime time;
    private double magnitude;
    private int transmitterTime;
    private LocalDateTime absoluteTime;

    public TimeMagnitude() {
        // no-args constructor
    }
}
