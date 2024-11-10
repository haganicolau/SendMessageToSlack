package com.haganicolau.slacklib;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SlackClientTest {

    private SlackClient slackClient;
    private HttpClient httpClientMock;
    private HttpResponse<String> httpResponseMock;
    private Logger loggerMock;
    private static final String WEBHOOK_URL = "https://slack.com/api/webhook";
    private static final String OAUTH_TOKEN = "xoxb-fake-token";

    @BeforeEach
    void setUp() {
        httpClientMock = mock(HttpClient.class);
        httpResponseMock = mock(HttpResponse.class);
        loggerMock = mock(Logger.class);

        slackClient = new SlackClient(WEBHOOK_URL, OAUTH_TOKEN, httpClientMock, loggerMock);
    }

    @Test
    void testSendMessage_SuccessfulResponse() throws Exception {
        when(httpResponseMock.statusCode()).thenReturn(200);
        when(httpResponseMock.body()).thenReturn("ok");
        when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponseMock);

        SlackMessage message = new SlackMessage.SlackMessageBuilder().setBlocks(null).setAttachments(null).build();

        Boolean result = slackClient.sendMessage(message);

        assertTrue(result, "The sendMessage method should return true on a successful response");
    }

    @Test
    void testSendMessage_TimeoutException() throws Exception {
        when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new HttpTimeoutException("Timeout occurred"));

        SlackMessage message = new SlackMessage.SlackMessageBuilder().setBlocks(null).setAttachments(null).build();

        Boolean result = slackClient.sendMessage(message);

        assertFalse(result, "The sendMessage method should return false on a timeout exception");
        verify(loggerMock).log(eq(Level.WARNING.getName()), eq("Request to Slack timed out."), any(HttpTimeoutException.class));
    }

    @Test
    void testSendMessage_IOException() throws Exception {
        when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new IOException("I/O error occurred"));

        SlackMessage message = new SlackMessage.SlackMessageBuilder().setBlocks(null).setAttachments(null).build();

        Boolean result = slackClient.sendMessage(message);

        assertFalse(result, "The sendMessage method should return false on an I/O exception");
        verify(loggerMock).log(eq(Level.SEVERE.getName()), eq("I/O error occurred when sending message to Slack."), any(IOException.class));
    }

    @Test
    void testSendMessage_UnexpectedException() throws Exception {
        when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        SlackMessage message = new SlackMessage.SlackMessageBuilder().setBlocks(null).setAttachments(null).build();

        Boolean result = slackClient.sendMessage(message);

        assertFalse(result, "The sendMessage method should return false on an unexpected exception");
        verify(loggerMock).log(eq(Level.SEVERE.getName()), eq("Unexpected error occurred while sending message to Slack."), any(RuntimeException.class));
    }

    @Test
    void testSendMessage_InterruptedException() throws Exception {
        when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenThrow(new InterruptedException("Interrupted error"));

        SlackMessage message = new SlackMessage.SlackMessageBuilder().setBlocks(null).setAttachments(null).build();

        Boolean result = slackClient.sendMessage(message);

        assertFalse(result, "The sendMessage method should return false on an InterruptedException");
        verify(loggerMock).log(eq(Level.WARNING.getName()), eq("Request was interrupted."), any(InterruptedException.class));
    }

    @Test
    void testAuthorizationHeader() throws Exception {
        when(httpResponseMock.statusCode()).thenReturn(200);
        when(httpResponseMock.body()).thenReturn("ok");
        when(httpClientMock.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(httpResponseMock);

        SlackMessage message = new SlackMessage.SlackMessageBuilder().setBlocks(null).setAttachments(null).build();

        slackClient.sendMessage(message);

        ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(httpClientMock).send(requestCaptor.capture(), any(HttpResponse.BodyHandler.class));

        HttpRequest request = requestCaptor.getValue();
        assertEquals("Bearer " + OAUTH_TOKEN, request.headers().firstValue("Authorization").orElse(null),
                "The Authorization header should be set with the OAuth token");
    }
}
