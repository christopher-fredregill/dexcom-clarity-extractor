package com.dexcom.clarity.extractor;

import com.dexcom.clarity.config.AppConfig;
import com.google.api.client.http.*;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Created by chris on 12/22/16.
 * Sends HTTP request to Dexcom
 */
public class ClarityExtractor {

    private String accessToken;
    private String subjectId;
    private String analysisSession;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numThreads;

    private Logger logger;

    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static final GsonFactory GSON_FACTORY = new GsonFactory();

    public ClarityExtractor(AppConfig appConfig, LocalDate startDate, LocalDate endDate) {
        this.accessToken = appConfig.accessToken();
        this.subjectId = appConfig.subjectId();
        this.analysisSession = appConfig.analysisSession();
        this.numThreads = appConfig.numThreads();
        this.startDate = startDate;
        this.endDate = endDate;
        this.logger = LogManager.getLogger(ClarityExtractor.class);
    }

    private GenericUrl dataUrl() {
        return new GenericUrl("https://clarity.dexcom.com/api/subject/" +
                this.subjectId + "/analysis_session/" +
                this.analysisSession + "/data/");
    }

    public void run() throws IOException {
        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
                request.setParser(GSON_FACTORY.createJsonObjectParser());
            }
        });

        String requestBody = "{'localDateTimeInterval':['" + this.startDate + "/" + this.endDate + "']}";

        logger.info("requestBody: " + requestBody);

        HttpRequest request = requestFactory.buildPostRequest(dataUrl(), ByteArrayContent.fromString("application/json", requestBody));
        request.getHeaders().setContentType("application/json").set("Access-Token", this.accessToken);

        logger.info("Formed HTTP request method: " + request.getRequestMethod());
        logger.info("Formed HTTP headers: " + request.getHeaders());
        logger.info("Formed HTTP request URL: " + request.getUrl());
        logger.info("Formed HTTP request content: " + request.getContent());

        HttpResponse response = request.execute();
        logger.info(response.parseAsString());
    }
}
