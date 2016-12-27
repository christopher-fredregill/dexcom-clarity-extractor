package com.dexcom.clarity.models;

/**
 * Created by chris on 12/23/16.
 */
public class EventData {
    private double value;
    private String units;
    private String entryType;
    private String transmitterId;
    private boolean isMmolDisplayMode;
    private String softwareVersion;
    private String softwareNumber;
    private String language;
    private int displayTimeOffset;
    private boolean is24HourMode;
    private int systemTimeOffset;
    private boolean isBlindedMode;

    public EventData() {
        // no-args constructor
    }
}
