package net.glowstone.block.data.states.reports;

import com.google.common.collect.ImmutableMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bukkit.block.data.Bisected;

public class BisectedHalfStateReport extends StateReport<Bisected.Half> {
    private static final Map<String, Bisected.Half> halfEnums = ImmutableMap.<String, Bisected.Half>builder()
        .put("top", Bisected.Half.TOP)
        .put("upper", Bisected.Half.TOP)
        .put("bottom", Bisected.Half.BOTTOM)
        .put("lower", Bisected.Half.BOTTOM)
        .build();

    private final Map<Bisected.Half, String> halfStrings;

    public BisectedHalfStateReport(String defaultValue, String... validValues) {
        super(
            Bisected.Half.class,
            halfEnums.get(defaultValue),
            Arrays.stream(validValues).map(halfEnums::get).collect(Collectors.toSet()),
            Optional.empty()
        );
        List<String> validValuesList = Arrays.asList(validValues);
        halfStrings = halfEnums.entrySet().stream()
            .filter((e) -> validValuesList.contains(e.getKey()))
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    @Override
    public String stringifyValue(Bisected.Half value) {
        return halfStrings.get(value);
    }

    @Override
    public Bisected.Half parseValue(String value) {
        return halfEnums.get(value);
    }
}
