package net.glowstone.block.data.props;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.block.data.states.reports.RedstoneWireConnectionStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.type.RedstoneWire;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowRedstoneWire.Constants.NORTH_PROP_NAME,
            reportType = RedstoneWireConnectionStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowRedstoneWire.Constants.SOUTH_PROP_NAME,
            reportType = RedstoneWireConnectionStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowRedstoneWire.Constants.EAST_PROP_NAME,
            reportType = RedstoneWireConnectionStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowRedstoneWire.Constants.WEST_PROP_NAME,
            reportType = RedstoneWireConnectionStateReport.class
        )
    },
    interfaceName = "RedstoneWire"
)
public interface GlowRedstoneWire extends StatefulBlockData, RedstoneWire {
    class Constants {
        public static final String NORTH_PROP_NAME = "north";
        public static final String SOUTH_PROP_NAME = "south";
        public static final String EAST_PROP_NAME = "east";
        public static final String WEST_PROP_NAME = "west";
        public static final Class<Connection> STATE_TYPE = Connection.class;
    }

    @Override
    default Connection getFace(BlockFace face) {
        switch (face){
            case NORTH:
                return getValue(Constants.NORTH_PROP_NAME, Constants.STATE_TYPE);

            case SOUTH:
                return getValue(Constants.SOUTH_PROP_NAME, Constants.STATE_TYPE);

            case EAST:
                return getValue(Constants.EAST_PROP_NAME, Constants.STATE_TYPE);

            case WEST:
                return getValue(Constants.WEST_PROP_NAME, Constants.STATE_TYPE);

            default:
                return Connection.NONE;
        }
    }

    @Override
    default void setFace(BlockFace face, Connection connection) {
        switch (face){
            case NORTH:
                setValue(Constants.NORTH_PROP_NAME, Constants.STATE_TYPE, connection);

            case SOUTH:
                setValue(Constants.SOUTH_PROP_NAME, Constants.STATE_TYPE, connection);

            case EAST:
                setValue(Constants.EAST_PROP_NAME, Constants.STATE_TYPE, connection);

            case WEST:
                setValue(Constants.WEST_PROP_NAME, Constants.STATE_TYPE, connection);
        }
    }

    @Override
    default Set<BlockFace> getAllowedFaces() {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST)));
    }
}
