package com.dexcom.clarity.extractor;

import com.dexcom.clarity.config.AppConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by chris on 12/22/16.
 */
public class Main {

    public static final AppConfig appConfig = ConfigFactory.create(AppConfig.class);
    public static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting...");

        ClarityExtractor clarityExtractor = new ClarityExtractor(appConfig);
        clarityExtractor.run();
    }
}
