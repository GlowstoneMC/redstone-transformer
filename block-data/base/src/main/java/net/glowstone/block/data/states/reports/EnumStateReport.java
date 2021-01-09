package net.glowstone.block.data.states.reports;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import net.glowstone.block.data.states.values.StateValue;

public abstract class EnumStateReport<T extends Enum<T>> extends StateReport<T> {
    private static <T extends Enum<T>> T parseValue(Class<T> enumClass, String value) {
        return Enum.valueOf(enumClass, value.toUpperCase());
    }

    public EnumStateReport(Class<T> enumClass, String defaultValue, String[] validValues) {
        super(
            enumClass,
            parseValue(enumClass, defaultValue),
            Arrays.stream(validValues).map((v) -> parseValue(enumClass, v)).collect(Collectors.toSet())
        );
    }

    @Override
    public String stringifyValue(T value) {
        return value.toString().toLowerCase();
    }

    @Override
    public T parseValue(String value) {
        return parseValue(getValueType(), value);
    }
}
