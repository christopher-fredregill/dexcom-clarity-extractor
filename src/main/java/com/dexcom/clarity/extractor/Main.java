package com.dexcom.clarity.extractor;

import com.dexcom.clarity.config.AppConfig;
import com.intellij.notification.EventLog;
import kotlin.reflect.jvm.internal.impl.descriptors.EffectiveVisibility;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;

import static java.lang.System.exit;

/**
 * Created by chris on 12/22/16.
 */
public class Main {
    public static void main(String[] args) {
        AppConfig appConfig = ConfigFactory.create(AppConfig.class);
        Logger logger = LogManager.getLogger(Main.class);

        LocalDate startDate, endDate;

        logger.info("Starting...");
        if(appConfig.accessToken() == null) {
            logger.error("Dexcom Access-Token required; please set the accessToken property in AppConfig.properties.");
            exit(1);
        } else {
            logger.info("Dexcom Access-Token set; proceeding to extract logs.");
        }

        if(appConfig.startDate() == null) {
            logger.warn("No start date provided; defaulting to one year ago.");
            startDate = LocalDate.now().minusYears(1);
        } else {
            startDate = LocalDate.parse(appConfig.startDate());
        }

        if(appConfig.endDate() == null) {
            logger.warn("No end date provided; defaulting to today's date.");
            endDate = LocalDate.now();
        } else {
            endDate = LocalDate.parse(appConfig.endDate());
        }

        ClarityExtractor clarityExtractor = new ClarityExtractor(appConfig, startDate, endDate);

        try {
            clarityExtractor.run();
        } catch(IOException e) {
            logger.error("Failed to extract Clarity logs: " + e.getMessage());
            exit(1);
        }
    }
}
