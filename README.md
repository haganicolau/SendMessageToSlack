# Sending Message to Slack

[![Maven Central](https://img.shields.io/maven-central/v/io.hndrs.slack/slack-spring-boot-starter?style=for-the-badge)](https://search.maven.org/artifact/io.hndrs.slack/slack-spring-boot-starter)
[![Supported Java Version](https://img.shields.io/badge/Supported%20Java%20Version-11%2B-informational?style=for-the-badge)]()

## Description


This library, developed by Haganicolau, provides an interface for sending messages to Slack with customizable blocks and attachments.
Using the `SlackClient` class, you can configure messages with different sections, headers, and attachments, using plain text or markdown formatting.
The library is designed to simplify integration with Slack's Incoming Webhooks and OAuth API, allowing for easy customization and error handling.
It supports adding sections with rich text (such as bold and italicized formatting) and color-coded attachments, making it suitable for various Slack notifications
and alerting applications.


## Prerequisites
To use this library to send messages to Slack, you'll need to set up two essential components:

### 1. Webhook URL
A Webhook URL is required to send messages directly to Slack through the webhooks API. This link must be configured in the Slack workspace where you wish to send messages. Follow these steps to create a Webhook URL:

- Visit the [Create Webhook in Slack](https://api.slack.com/messaging/webhooks#create_a_webhook) page.
- Choose or create an app for your workspace.
- Grant the necessary permissions and activate the webhook to receive a unique URL.
- This Webhook URL will be used to send JSON-formatted messages directly to Slack.

### 2. OAuth Token
An OAuth token authorizes sending messages on behalf of a specific user or app. This token can be obtained by configuring an app in Slack and adding the required permissions:

- Go to the [Slack Apps Portal](https://api.slack.com/apps) and create an app (if one does not already exist).
- In the app settings, select "OAuth & Permissions" and add the required scopes, such as `chat:write` to send messages.
- Install the app in your workspace to generate the OAuth Token.


## Custom Logger Interface 

This library provides a flexible `Logger` interface, allowing you to integrate it with any logging framework of your choice. You can implement this interface to capture log messages from the Slack client and send them to a preferred logging library such as Log4j, SLF4J, or Logback.

### Logger Interface

The `Logger` interface has a single method:

```java
public interface Logger {
    void log(String level, String message, Throwable t);
}
```

- level: The log level (e.g., "INFO", "ERROR").
- message: The log message.
- t: The throwable instance, if logging an error; otherwise, null.

### Log4j Implementation
To implement the Logger interface using Log4j:
```java
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4jLogger implements Logger {
    private static final Logger log = Logger.getLogger(Log4jLogger.class);

    @Override
    public void log(String level, String message, Throwable t) {
        Level logLevel = Level.toLevel(level);
        if (t != null) {
            log.log(logLevel, message, t);
        } else {
            log.log(logLevel, message);
        }
    }
}
```

### Using the Custom Logger in SlackClient
Once you’ve created an implementation of the Logger interface, you can use it with the SlackClient by passing an instance of your custom logger to capture messages.
```java
SlackClient slackClient = new SlackClient(
        System.getenv("SLACK_WEBHOOK_URL"),
        System.getenv("SLACK_TOKEN"),
        HttpClient.newHttpClient(),
        new Log4jLogger() // Or Slf4jLogger, depending on the implementation you prefer
);
```


## Example how to use
The `Main` class demonstrates the usage of the library by creating and sending Slack messages with multiple blocks and attachments.
Example methods like `sendHeaderAndSectionPlainText` and `sendHeaderAndSectionMarkdown` showcase different message formats that can be
sent using `SlackClient`.