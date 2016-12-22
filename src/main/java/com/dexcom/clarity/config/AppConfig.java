package com.dexcom.clarity.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

/**
 * Created by chris on 12/22/16.
 */

@Sources({ "file:AppConfig.properties" })
public interface AppConfig extends Config {
    String accessToken();
    String subjectId();
    String analysisSession();
    String startDate();
    String endDate();
    String extractFolder();
    int numThreads();
}
