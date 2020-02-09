package net.glowstone.block.data.states.reports;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bukkit.block.BlockFace;

public class BlockFaceIdStateReport extends StateReport<BlockFace> {
    public static final BiMap<String, BlockFace> BLOCK_FACES = ImmutableBiMap.<String, BlockFace>builder()
        .put("0", BlockFace.SOUTH)
        .put("1", BlockFace.SOUTH_SOUTH_WEST)
        .put("2", BlockFace.SOUTH_WEST)
        .put("3", BlockFace.WEST_SOUTH_WEST)
        .put("4", BlockFace.WEST)
        .put("5", BlockFace.WEST_NORTH_WEST)
        .put("6", BlockFace.NORTH_WEST)
        .put("7", BlockFace.NORTH_NORTH_WEST)
        .put("8", BlockFace.NORTH)
        .put("9", BlockFace.NORTH_NORTH_EAST)
        .put("10", BlockFace.NORTH_EAST)
        .put("11", BlockFace.EAST_NORTH_EAST)
        .put("12", BlockFace.EAST)
        .put("13", BlockFace.EAST_SOUTH_EAST)
        .put("14", BlockFace.SOUTH_EAST)
        .put("15", BlockFace.SOUTH_SOUTH_EAST)
        .build();

    public BlockFaceIdStateReport(String defaultValue, String... validValues) {
        super(
            BlockFace.class,
            BLOCK_FACES.get(defaultValue),
            Arrays.stream(validValues).map(BLOCK_FACES::get).collect(Collectors.toSet()),
            Optional.empty()
        );
    }

    @Override
    public String stringifyValue(BlockFace value) {
        return BLOCK_FACES.inverse().get(value);
    }

    @Override
    public BlockFace parseValue(String value) {
        return BLOCK_FACES.get(value);
    }
}
