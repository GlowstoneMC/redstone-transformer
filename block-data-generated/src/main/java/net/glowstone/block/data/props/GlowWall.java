package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.block.data.states.reports.WallHeightStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Wall;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowWall.Constants.EAST_PROP_NAME,
            reportType = WallHeightStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowWall.Constants.NORTH_PROP_NAME,
            reportType = WallHeightStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowWall.Constants.SOUTH_PROP_NAME,
            reportType = WallHeightStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowWall.Constants.WEST_PROP_NAME,
            reportType = WallHeightStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowWall.Constants.UP_PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceClass = Wall.class
)
public interface GlowWall extends StatefulBlockData, Wall {
    class Constants {
        public static final String EAST_PROP_NAME = "east";
        public static final Class<Wall.Height> EAST_STATE_TYPE = Wall.Height.class;
        public static final String NORTH_PROP_NAME = "north";
        public static final Class<Wall.Height> NORTH_STATE_TYPE = Wall.Height.class;
        public static final String SOUTH_PROP_NAME = "south";
        public static final Class<Wall.Height> SOUTH_STATE_TYPE = Wall.Height.class;
        public static final String WEST_PROP_NAME = "west";
        public static final Class<Wall.Height> WEST_STATE_TYPE = Wall.Height.class;
        public static final String UP_PROP_NAME = "up";
        public static final Class<Boolean> UP_STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isUp() {
        return getValue(Constants.UP_PROP_NAME, Constants.UP_STATE_TYPE);
    }

    @Override
    default void setUp(boolean up) {
        setValue(Constants.UP_PROP_NAME, Constants.UP_STATE_TYPE, up);
    }

    @Override
    default Height getHeight(BlockFace face) {
        switch (face) {
            case EAST:
                return getValue(Constants.EAST_PROP_NAME, Constants.EAST_STATE_TYPE);
            case NORTH:
                return getValue(Constants.NORTH_PROP_NAME, Constants.NORTH_STATE_TYPE);
            case SOUTH:
                return getValue(Constants.SOUTH_PROP_NAME, Constants.SOUTH_STATE_TYPE);
            case WEST:
                return getValue(Constants.WEST_PROP_NAME, Constants.WEST_STATE_TYPE);
            default:
                throw new IllegalArgumentException("Illegal wall face: " + face);
        }
    }

    @Override
    default void setHeight(BlockFace face, Height height) {
        switch (face) {
            case EAST:
                setValue(Constants.EAST_PROP_NAME, Constants.EAST_STATE_TYPE, height);
                break;
            case NORTH:
                setValue(Constants.NORTH_PROP_NAME, Constants.NORTH_STATE_TYPE, height);
                break;
            case SOUTH:
                setValue(Constants.SOUTH_PROP_NAME, Constants.SOUTH_STATE_TYPE, height);
                break;
            case WEST:
                setValue(Constants.WEST_PROP_NAME, Constants.WEST_STATE_TYPE, height);
                break;
            default:
                throw new IllegalArgumentException("Illegal wall face: " + face);
        }
    }
}
