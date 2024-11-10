package com.haganicolau.slacklib;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BlockType {
    SECTION("section"),
    HEADER("header"),
    DIVIDER("divider");

    private final String type;

    BlockType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @JsonValue
    public String getValue() {
        return type;
    }
}
