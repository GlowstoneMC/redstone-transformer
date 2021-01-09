package net.glowstone.datapack.utils.mapping;

public class MethodMapping {
    private final Class<?> type;
    private final String method;

    public MethodMapping(Class<?> type, String method) {
        this.type = type;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public String getMethod() {
        return method;
    }
}
