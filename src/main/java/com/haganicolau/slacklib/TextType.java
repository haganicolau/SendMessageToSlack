package com.haganicolau.slacklib;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TextType {
    PLAIN_TEXT("plain_text"),
    MARKDOWN("mrkdwn");

    private final String textType;

    TextType(String textType) {
        this.textType = textType;
    }

    public String getTextType() {
        return textType;
    }

    @JsonValue
    public String getValue() {
        return textType;
    }
}
