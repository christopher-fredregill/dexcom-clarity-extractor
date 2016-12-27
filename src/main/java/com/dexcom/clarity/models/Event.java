package com.dexcom.clarity.models;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Created by chris on 12/23/16.
 */
public class Event {
    private String label;
    private LocalDateTime time;
    private ZonedDateTime systemTime;
    private EventData data;
    private String subLabel;

    public Event() {
        // no-args constructor
    }
}
