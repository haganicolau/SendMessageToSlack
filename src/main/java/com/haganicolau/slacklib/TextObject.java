package com.haganicolau.slacklib;

import java.util.Objects;

public class TextObject {

    private TextType type;
    private String text;

    public TextObject(TextType type, String text) {
        this.type = type;
        this.text = text;
    }

    public TextType getType() {
        return type;
    }

    public void setType(TextType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextObject that = (TextObject) o;
        return Objects.equals(type, that.type) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, text);
    }

    @Override
    public String toString() {
        return "TextObject{" +
                "type='" + type + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
