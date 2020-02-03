package net.glowstone.block.data.states.reports;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.glowstone.block.data.states.StateReport;
import net.glowstone.block.data.states.StateValue;
import net.glowstone.block.data.states.values.IntegerStateValue;

public class IntegerStateReport extends StateReport<Integer> {
    public IntegerStateReport(String defaultValue, Set<String> validValues) {
        super(
            Integer.parseInt(defaultValue),
            validValues.stream().map(Integer::parseInt).collect(Collectors.toSet()),
            validValues.stream().map(Integer::parseInt).max(Integer::compareTo)
        );
    }

    @Override
    public StateValue<Integer> createStateValue(Optional<String> value) {
        return new IntegerStateValue(value, this);
    }
}
