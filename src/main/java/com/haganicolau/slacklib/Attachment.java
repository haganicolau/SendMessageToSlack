package com.haganicolau.slacklib;

import java.util.Objects;

class Attachment {
    private String color;
    private String fallback;
    private String text;

    public Attachment(String color, String fallback, String text) {
        this.color = color;
        this.fallback = fallback;
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFallback() {
        return fallback;
    }

    public void setFallback(String fallback) {
        this.fallback = fallback;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "color='" + color + '\'' +
                ", fallback='" + fallback + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return Objects.equals(color, that.color) && Objects.equals(fallback, that.fallback)
                && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, fallback, text);
    }
}

