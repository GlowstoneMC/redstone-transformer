package net.glowstone.block.data.states.reports;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.glowstone.block.data.states.StateReport;
import net.glowstone.block.data.states.StateValue;
import net.glowstone.block.data.states.values.IntegerStateValue;

public class IntegerStateReport extends StateReport<Integer> {
    public IntegerStateReport(String defaultValue, String... validValues) {
        this(
            Integer.parseInt(defaultValue),
            Collections.unmodifiableSet(Arrays.stream(validValues).map(Integer::parseInt).collect(Collectors.toSet()))
        );
    }

    private IntegerStateReport(int defaultValue, Set<Integer> validValues) {
        super(defaultValue, validValues, validValues.stream().max(Integer::compareTo));
    }

    @Override
    public StateValue<Integer> createStateValue(Optional<String> value) {
        return new IntegerStateValue(value, this);
    }
}
