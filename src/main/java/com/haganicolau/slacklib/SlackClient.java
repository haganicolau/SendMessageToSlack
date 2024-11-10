package com.haganicolau.slacklib;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.util.logging.Level;


public class SlackClient {

    private final Logger logger;

    private final String webhookUrl;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String oauthToken;
    private final String OK = "ok";


    public SlackClient(String webhookUrl, String oauthToken, HttpClient httpClient, Logger logger) {
        this.webhookUrl = webhookUrl;
        this.oauthToken = oauthToken;
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();
        this.logger = logger;
    }

    public Boolean sendMessage(SlackMessage message) {
        try {
            String payload = objectMapper.writeValueAsString(message);

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(webhookUrl))
                    .header("Content-Type", "application/json; charset=UTF-8");

            if (oauthToken != null && !oauthToken.isEmpty()) {
                requestBuilder.header("Authorization", "Bearer " + oauthToken);
            }

            HttpRequest request = requestBuilder
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String logMessage = String.format("Status Code= %d, Response Body= %s",
                    response.statusCode(), response.body());
            logger.log(Level.FINE.getName(), logMessage, null);

            return response.statusCode() == 200  && OK.equals(response.body());
        } catch (HttpTimeoutException e) {
            logger.log(Level.WARNING.getName(), "Request to Slack timed out.", e);
            return false;

        } catch (IOException e) {
            logger.log(Level.SEVERE.getName(), "I/O error occurred when sending message to Slack.", e);
            return false;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.WARNING.getName(), "Request was interrupted.", e);
            return false;

        } catch (Exception e) {
            logger.log(Level.SEVERE.getName(), "Unexpected error occurred while sending message to Slack.", e);
            return false;
        }
    }
}
