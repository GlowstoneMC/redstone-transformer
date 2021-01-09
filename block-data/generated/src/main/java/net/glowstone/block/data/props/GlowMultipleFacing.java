package net.glowstone.block.data.props;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowMultipleFacing.Constants.NORTH_PROP_NAME,
            reportType = BooleanStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowMultipleFacing.Constants.SOUTH_PROP_NAME,
            reportType = BooleanStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowMultipleFacing.Constants.EAST_PROP_NAME,
            reportType = BooleanStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowMultipleFacing.Constants.WEST_PROP_NAME,
            reportType = BooleanStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowMultipleFacing.Constants.UP_PROP_NAME,
            reportType = BooleanStateReport.class,
            required = false
        ),
        @PropertyAssociation(
            propName = GlowMultipleFacing.Constants.DOWN_PROP_NAME,
            reportType = BooleanStateReport.class,
            required = false
        )
    },
    interfaceClass = MultipleFacing.class
)
public interface GlowMultipleFacing extends StatefulBlockData, MultipleFacing {
    class Constants {
        public static final String NORTH_PROP_NAME = "north";
        public static final String SOUTH_PROP_NAME = "south";
        public static final String EAST_PROP_NAME = "east";
        public static final String WEST_PROP_NAME = "west";
        public static final String UP_PROP_NAME = "up";
        public static final String DOWN_PROP_NAME = "down";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean hasFace(BlockFace face) {
        switch (face){
            case NORTH:
                return getValue(Constants.NORTH_PROP_NAME, Constants.STATE_TYPE);

            case SOUTH:
                return getValue(Constants.SOUTH_PROP_NAME, Constants.STATE_TYPE);

            case EAST:
                return getValue(Constants.EAST_PROP_NAME, Constants.STATE_TYPE);

            case WEST:
                return getValue(Constants.WEST_PROP_NAME, Constants.STATE_TYPE);

            case UP:
                if (hasValue(Constants.UP_PROP_NAME)) {
                    return getValue(Constants.UP_PROP_NAME, Constants.STATE_TYPE);
                }
                return false;
            case DOWN:
                if (hasValue(Constants.DOWN_PROP_NAME)) {
                    return getValue(Constants.DOWN_PROP_NAME, Constants.STATE_TYPE);
                }
                return false;
            default:
                return false;
        }
    }

    @Override
    default void setFace(BlockFace face, boolean has) {
        switch (face){
            case NORTH:
                setValue(Constants.NORTH_PROP_NAME, Constants.STATE_TYPE, has);
                break;

            case SOUTH:
                setValue(Constants.SOUTH_PROP_NAME, Constants.STATE_TYPE, has);
                break;

            case EAST:
                setValue(Constants.EAST_PROP_NAME, Constants.STATE_TYPE, has);
                break;

            case WEST:
                setValue(Constants.WEST_PROP_NAME, Constants.STATE_TYPE, has);
                break;

            case UP:
                if (hasValue(Constants.UP_PROP_NAME)) {
                    setValue(Constants.UP_PROP_NAME, Constants.STATE_TYPE, has);
                }
                break;

            case DOWN:
                if (hasValue(Constants.DOWN_PROP_NAME)) {
                    setValue(Constants.DOWN_PROP_NAME, Constants.STATE_TYPE, has);
                }
                break;
        }
    }

    @Override
    default Set<BlockFace> getFaces() {
        return Arrays.stream(BlockFace.values())
            .filter(this::hasFace)
            .collect(Collectors.toSet());
    }

    @Override
    default Set<BlockFace> getAllowedFaces() {
        Set<BlockFace> faces = new HashSet<>(Arrays.asList(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST));
        if (hasValue(Constants.UP_PROP_NAME)) {
            faces.add(BlockFace.UP);
        }
        if (hasValue(Constants.DOWN_PROP_NAME)) {
            faces.add(BlockFace.DOWN);
        }
        return faces;
    }
}
