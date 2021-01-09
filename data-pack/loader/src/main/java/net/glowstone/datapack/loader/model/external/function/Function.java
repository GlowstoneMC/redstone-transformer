package net.glowstone.datapack.loader.model.external.function;

import java.util.List;

public class Function {
    private final List<String> commands;

    public Function(List<String> commands) {
        this.commands = commands;
    }

    public List<String> getCommands() {
        return commands;
    }
}
