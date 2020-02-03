package net.glowstone.block.data.states.values;

import java.util.Optional;
import net.glowstone.block.data.states.StateReport;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.block.data.states.StateValue;

public class IntegerStateValue extends StateValue<Integer> {
    public IntegerStateValue(Optional<String> explicitValue, IntegerStateReport stateReport) {
        super(
            explicitValue.map(Integer::parseInt),
            stateReport
        );
    }

    private IntegerStateValue(Optional<Integer> value, StateReport<Integer> report) {
        super(value, report);
    }

    @Override
    public String getValueAsString() {
        return getValue().toString();
    }

    @Override
    public void setValueFromString(String stringValue) {
        setValue(Integer.parseInt(stringValue));
    }

    @Override
    public IntegerStateValue clone() {
        return new IntegerStateValue(value, report);
    }
}
