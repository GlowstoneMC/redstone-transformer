package net.glowstone.block.data.states.reports;

import com.google.common.collect.ImmutableMap;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.BigDripleaf;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BigDripleafStateReport extends StateReport<BigDripleaf.Tilt> {
    private static final Map<String, BigDripleaf.Tilt> tiltEnums = ImmutableMap.<String, BigDripleaf.Tilt>builder()
                                                                            .put("full", BigDripleaf.Tilt.FULL)
                                                                            .put("none", BigDripleaf.Tilt.NONE)
                                                                            .put("partial", BigDripleaf.Tilt.PARTIAL)
                                                                            .put("unstable", BigDripleaf.Tilt.UNSTABLE)
                                                                            .build();

    private final Map<BigDripleaf.Tilt, String> tiltStrings;

    public BigDripleafStateReport(String defaultValue, String... validValues) {
        super(
                BigDripleaf.Tilt.class,
                tiltEnums.get(defaultValue),
                Arrays.stream(validValues).map(tiltEnums::get).collect(Collectors.toSet())
        );
        List<String> validValuesList = Arrays.asList(validValues);
        tiltStrings = tiltEnums.entrySet().stream()
                               .filter((e) -> validValuesList.contains(e.getKey()))
                               .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    @Override
    public String stringifyValue(BigDripleaf.Tilt value) {
        return tiltStrings.get(value);
    }

    @Override
    public BigDripleaf.Tilt parseValue(String value) {
        return tiltEnums.get(value);
    }
}
