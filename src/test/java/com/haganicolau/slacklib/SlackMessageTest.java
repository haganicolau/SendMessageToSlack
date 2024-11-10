package com.haganicolau.slacklib;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SlackMessageTest {

    private List<Block> blocks;
    private List<Attachment> attachments;

    @BeforeEach
    void setUp() {

        Block headerBlock = new Block(BlockType.HEADER, new TextObject(TextType.PLAIN_TEXT, "This is a header block"));
        Block sectionBlock = new Block(BlockType.SECTION, new TextObject(TextType.PLAIN_TEXT, "This is a section block"));
        blocks = Arrays.asList(headerBlock, sectionBlock);

        Attachment attachment = new Attachment("#FF0000", "Fallback text", "This is an attachment text");
        attachments = List.of(attachment);
    }

    @Test
    void testSlackMessageWithBlocksAndAttachments() {

        SlackMessage message = new SlackMessage.SlackMessageBuilder()
                .setBlocks(blocks)
                .setAttachments(attachments)
                .build();

        assertEquals(blocks, message.getBlocks(), "Blocks should match the initialized list");
        assertEquals(attachments, message.getAttachments(), "Attachments should match the initialized list");
    }

    @Test
    void testSlackMessageEquality() {

        SlackMessage message1 = new SlackMessage.SlackMessageBuilder()
                .setBlocks(blocks)
                .setAttachments(attachments)
                .build();

        SlackMessage message2 = new SlackMessage.SlackMessageBuilder()
                .setBlocks(blocks)
                .setAttachments(attachments)
                .build();

        assertEquals(message1, message2, "Two SlackMessage instances with the same blocks and attachments should be equal");
    }

    @Test
    void testSlackMessageInequality() {

        Block newBlock = new Block(BlockType.SECTION, new TextObject(TextType.PLAIN_TEXT,"Another section block"));
        List<Block> differentBlocks = List.of(newBlock);

        SlackMessage message1 = new SlackMessage.SlackMessageBuilder()
                .setBlocks(blocks)
                .setAttachments(attachments)
                .build();

        SlackMessage message2 = new SlackMessage.SlackMessageBuilder()
                .setBlocks(differentBlocks)
                .setAttachments(attachments)
                .build();

        assertNotEquals(message1, message2, "Two SlackMessage instances with different blocks should not be equal");
    }

    @Test
    void testSlackMessageToString() {
        SlackMessage message = new SlackMessage.SlackMessageBuilder()
                .setBlocks(blocks)
                .setAttachments(attachments)
                .build();

        String expectedString = "SlackMessage{" +
                ", blocks=" + blocks +
                ", attachments=" + attachments +
                '}';

        assertEquals(expectedString, message.toString(), "toString should return the expected string representation");
    }
}

