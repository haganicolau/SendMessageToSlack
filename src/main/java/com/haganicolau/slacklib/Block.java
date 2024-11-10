package com.haganicolau.slacklib;

import java.util.Objects;

class Block {
    private BlockType type;
    private TextObject text;

    public Block(BlockType type, TextObject text) {
        this.type = type;
        this.text = text;
    }

    public BlockType getType() {
        return type;
    }

    public void setType(BlockType type) {
        this.type = type;
    }

    public TextObject getText() {
        return text;
    }

    public void setText(TextObject text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Block{" +
                "type='" + type + '\'' +
                ", text=" + text +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Block block = (Block) o;
        return Objects.equals(type, block.type) && Objects.equals(text, block.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, text);
    }
}
