package net.glowstone.block.data.states.reports;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class BooleanStateReport extends StateReport<Boolean> {
    private static boolean parseBooleanStrict(String value) {
        if (value.equals("true")) {
            return true;
        } else if (value.equals("false")) {
            return false;
        } else {
            throw new IllegalArgumentException("Not a boolean: " + value);
        }
    }

    public BooleanStateReport(String defaultValue, String... validValues) {
        super(
            Boolean.class,
            parseBooleanStrict(defaultValue),
            Arrays.stream(validValues).map(BooleanStateReport::parseBooleanStrict).collect(Collectors.toSet())
        );
    }

    @Override
    public String stringifyValue(Boolean value) {
        return value.toString();
    }

    @Override
    public Boolean parseValue(String value) {
        return Boolean.parseBoolean(value);
    }
}
