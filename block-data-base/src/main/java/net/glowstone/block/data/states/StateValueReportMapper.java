package net.glowstone.block.data.states;

import java.util.HashMap;
import java.util.Map;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.block.data.states.values.IntegerStateValue;

public class StateValueReportMapper {
    private static final Map<Class<? extends StateValue<?>>, Class<? extends StateReport<?>>> stateValuesToStateReports;

    static {
        stateValuesToStateReports = new HashMap<>();
        stateValuesToStateReports.put(IntegerStateValue.class, IntegerStateReport.class);
    }

    public static Class<? extends StateReport<?>> getStateReportTypeForValueType(Class<? extends StateValue<?>> valueType) {
        return stateValuesToStateReports.get(valueType);
    }
}
