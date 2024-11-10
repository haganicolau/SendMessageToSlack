package com.haganicolau.slacklib;

import java.util.List;
import java.util.Objects;

public class SlackMessage {
    private List<Block> blocks;
    private List<Attachment> attachments;

    public SlackMessage(SlackMessageBuilder builder) {
        this.blocks = builder.blocks;
        this.attachments = builder.attachments;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public static class SlackMessageBuilder {
        private String text;
        private List<Block> blocks;
        private List<Attachment> attachments;

        public SlackMessageBuilder setBlocks(List<Block> blocks) {
            this.blocks = blocks;
            return this;
        }

        public SlackMessageBuilder setAttachments(List<Attachment> attachments) {
            this.attachments = attachments;
            return this;
        }

        public SlackMessage build() {
            return new SlackMessage(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SlackMessage that = (SlackMessage) o;
        return Objects.equals(blocks, that.blocks) && Objects.equals(attachments, that.attachments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blocks, attachments);
    }

    @Override
    public String toString() {
        return "SlackMessage{" +
                ", blocks=" + blocks +
                ", attachments=" + attachments +
                '}';
    }
}
