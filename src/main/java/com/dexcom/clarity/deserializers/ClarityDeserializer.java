package com.dexcom.clarity.deserializers;

import com.dexcom.clarity.models.ClarityLog;
import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

/**
 * Created by chris on 12/23/16.
 */
public class ClarityDeserializer {

    private static Logger logger = LogManager.getLogger(ClarityDeserializer.class);

    public static void main(String[] args) throws IOException {
        // TODO: This should be configurable
        Path jsonFile = Paths.get("clarity_extracts", "2014-12-23_2016-12-23");
        logger.info("Got a Path: " + jsonFile.getFileName());

        // TODO: Be able to process this directly from the HTTP response, if desired
        String jsonContent = new String(Files.readAllBytes(jsonFile));

        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer());
        gson.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        gson.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeDeserializer());

        ClarityLog clarityLog = gson.create().fromJson(jsonContent, ClarityLog.class);
        logger.info("Got a ClarityLog object: " + clarityLog.toString());
    }
}
