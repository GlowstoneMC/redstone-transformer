package net.glowstone.block.data.states.reports;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class IntegerStateReport extends StateReport<Integer> {
    public IntegerStateReport(String defaultValue, String... validValues) {
        this(
            Integer.parseInt(defaultValue),
            Collections.unmodifiableSet(Arrays.stream(validValues).map(Integer::parseInt).collect(Collectors.toSet()))
        );
    }

    private IntegerStateReport(int defaultValue, Set<Integer> validValues) {
        super(Integer.class, defaultValue, validValues, validValues.stream().max(Integer::compareTo));
    }

    @Override
    public String stringifyValue(Integer value) {
        return value.toString();
    }

    @Override
    public Integer parseValue(String value) {
        return Integer.parseInt(value);
    }
}
