package net.venarge.brigadier.exeptions;

import net.venarge.brigadier.ImmutableStringReader;
import net.venarge.brigadier.Message;

public class SimpleCommandExceptionType implements CommandExceptionType {
    private final Message message;

    public SimpleCommandExceptionType(final Message message) {
        this.message = message;
    }

    public CommandSyntaxException create() {
        return new CommandSyntaxException(this, message);
    }

    public CommandSyntaxException createWithContext(final ImmutableStringReader reader) {
        return new CommandSyntaxException(this, message, reader.getString(), reader.getCursor());
    }

    @Override
    public String toString() {
        return message.getString();
    }
}

