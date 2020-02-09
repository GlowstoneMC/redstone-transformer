package net.glowstone.block.data.states.reports;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class BooleanStateReport extends StateReport<Boolean> {
    public BooleanStateReport(String defaultValue, String... validValues) {
        super(Boolean.class, Boolean.parseBoolean(defaultValue), Arrays.stream(validValues).map(Boolean::parseBoolean).collect(Collectors.toSet()), Optional.empty());
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
