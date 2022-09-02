package net.venarge.brigadier;

public class LiteralMessage implements Message{
    private final String string;

    public LiteralMessage(final String string) {
        this.string = string;
    }

    @Override
    public String getString() {
        return string;
    }

    @Override
    public String toString() {
        return string;
    }
}
