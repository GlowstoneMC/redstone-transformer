package net.glowstone.block.data.states.reports;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bukkit.Instrument;

public class InstrumentStateReport extends StateReport<Instrument> {
    private static final BiMap<String, Instrument> nameToValueMap = ImmutableBiMap.<String, Instrument>builder()
        .put("harp", Instrument.PIANO)
        .put("basedrum", Instrument.BASS_DRUM)
        .put("snare", Instrument.SNARE_DRUM)
        .put("hat", Instrument.STICKS)
        .put("bass", Instrument.BASS_GUITAR)
        .put("flute", Instrument.FLUTE)
        .put("bell", Instrument.BELL)
        .put("guitar", Instrument.GUITAR)
        .put("chime", Instrument.CHIME)
        .put("xylophone", Instrument.XYLOPHONE)
        .put("iron_xylophone", Instrument.IRON_XYLOPHONE)
        .put("cow_bell", Instrument.COW_BELL)
        .put("didgeridoo", Instrument.DIDGERIDOO)
        .put("bit", Instrument.BIT)
        .put("banjo", Instrument.BANJO)
        .put("pling", Instrument.PLING)
        .build();

    private static Instrument parseValueImpl(String value) {
        return Optional.ofNullable(nameToValueMap.get(value))
            .orElseThrow(() -> new IllegalArgumentException("Instrument not found: " + value));
    }

    public InstrumentStateReport(String defaultValue, String... validValues) {
        super(
            Instrument.class,
            parseValueImpl(defaultValue),
            Arrays.stream(validValues).map(InstrumentStateReport::parseValueImpl).collect(Collectors.toSet())
        );
    }

    @Override
    public String stringifyValue(Instrument value) {
        return nameToValueMap.inverse().get(value);
    }

    @Override
    public Instrument parseValue(String value) {
        return parseValueImpl(value);
    }
}
