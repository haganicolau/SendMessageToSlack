package com.haganicolau.slacklib;

public interface Logger {
    void log(String level, String message, Throwable t);
}
