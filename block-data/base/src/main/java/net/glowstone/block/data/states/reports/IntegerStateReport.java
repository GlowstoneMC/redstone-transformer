package net.glowstone.block.data.states.reports;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class IntegerStateReport extends ComparableStateReport<Integer> {
    public IntegerStateReport(String defaultValue, String... validValues) {
        super(
            Integer.class,
            Integer.parseInt(defaultValue),
            Collections.unmodifiableSet(Arrays.stream(validValues).map(Integer::parseInt).collect(Collectors.toSet()))
        );
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
