package com.haganicolau.slacklib;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        SlackClient slackClient = new SlackClient(
                System.getenv("SLACK_WEBHOOK_URL"),
                System.getenv("SLACK_TOKEN"),
                HttpClient.newHttpClient(),
                new Logger() {
                    @Override
                    public void log(String level, String message, Throwable t) {
                        System.out.println("[" + level + "] " + message);
                        if (t != null) {
                            t.printStackTrace();
                        }
                    }
                }
        );

        sendHeaderAndSectionPlainText(slackClient);
        sendHeaderAndSectionMarkdown(slackClient);
    }

    public static void sendHeaderAndSectionPlainText(SlackClient slackClient) {

        Block sectionBlock = new Block(BlockType.SECTION,
                new TextObject(TextType.PLAIN_TEXT, "This is a section block"));

        SlackMessage message = new SlackMessage.SlackMessageBuilder()
                .setBlocks(List.of(sectionBlock))
                .setAttachments(new ArrayList<>())
                .build();

        boolean result = slackClient.sendMessage(message);
        System.out.println("Message sent. Successfully: " + result);
    }

    public static void sendHeaderAndSectionMarkdown(SlackClient slackClient) {
        Block headerBlock = new Block(BlockType.HEADER,
                new TextObject(TextType.PLAIN_TEXT, "This is a header block"));
        Block sectionBlock = new Block(BlockType.SECTION,
                new TextObject(TextType.MARKDOWN, "A message *with some bold text* and _some italicized text_."));

        Attachment attachment = new Attachment("#FF0000", "Fallback text", "This is an attachment text");

        SlackMessage message = new SlackMessage.SlackMessageBuilder()
                .setBlocks(List.of(headerBlock, sectionBlock))
                .setAttachments(List.of(attachment))
                .build();

        boolean result = slackClient.sendMessage(message);
        System.out.println("Message sent. Successfully: " + result);
    }


}
