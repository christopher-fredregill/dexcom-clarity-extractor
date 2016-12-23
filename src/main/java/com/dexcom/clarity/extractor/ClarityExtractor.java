package com.dexcom.clarity.extractor;

import com.dexcom.clarity.config.AppConfig;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

/**
 * Created by chris on 12/22/16.
 * Sends HTTP request to Dexcom
 */
public class ClarityExtractor {

    private AppConfig appConfig;
    private LocalDate startDate;
    private LocalDate endDate;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final Logger logger = LogManager.getLogger(ClarityExtractor.class);
    private static final OkHttpClient client = new OkHttpClient();

    ClarityExtractor(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.validateConfig();
    }

    private void validateConfig() {
        if(this.appConfig.accessToken() == null) {
            logger.error("Dexcom Access-Token required; please set the accessToken property in AppConfig.properties.");
        } else {
            logger.info("Dexcom Access-Token set; proceeding to extract logs.");
        }

        if(this.appConfig.startDate() == null) {
            logger.warn("No start date provided; defaulting to one year ago.");
            this.setStartDate(LocalDate.now().minusYears(1));
        } else {
            this.setStartDate(LocalDate.parse(this.appConfig.startDate()));
        }

        if(this.appConfig.endDate() == null) {
            logger.warn("No end date provided; defaulting to today's date.");
            this.setEndDate(LocalDate.now());
        } else {
            this.setEndDate(LocalDate.parse(this.appConfig.endDate()));
        }
    }

    private String dataUrl() {
        return "https://clarity.dexcom.com/api/subject/" + this.appConfig.subjectId() +
               "/analysis_session/" + this.appConfig.analysisSession() +
               "/data";
    }

    private String post(String url, String json) throws IOException {
        logger.info("Using dataUrl: " + this.dataUrl());
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).header("Access-Token", this.appConfig.accessToken()).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private void toFile(String httpResponse) {
        Path extractFolder = Paths.get(this.appConfig.extractFolder());
        if(Files.isWritable(extractFolder)) {
            try {
                Path extractFile = extractFolder.resolve(this.getStartDate() + "_" + this.getEndDate());
                Files.write(extractFile, httpResponse.getBytes());
            } catch(IOException e) {
                logger.error("An exception occurred writing logs to file: " + e.getMessage());
            }
        } else {
            logger.error("Unable to write to extract file location " + extractFolder);
        }
    }

    public void run() {
        try {
            String res = this.post(this.dataUrl(), "{\"localDateTimeInterval\":[\"" + this.getStartDate() + "/" + this.getEndDate() + "\"]}");
            this.toFile(res);
        } catch(IOException e) {
            logger.error("An exception occurred: " + e.getMessage());
        }
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
