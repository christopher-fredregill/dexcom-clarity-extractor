package com.dexcom.clarity.models;

import java.time.LocalDateTime;

/**
 * Created by chris on 12/26/16.
 */
public class LowExceeded {
    private LocalDateTime time;
    private double threshold;
    private int transmitterTime;
    private LocalDateTime absoluteTime;

    public LowExceeded() {

    }
}
