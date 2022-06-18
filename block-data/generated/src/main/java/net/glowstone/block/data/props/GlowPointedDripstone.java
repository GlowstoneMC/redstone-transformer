package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BlockFaceStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.PointedDripstone;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

@AssociatedWithProps(
        props = {
                @PropertyAssociation(
                        propName = GlowPointedDripstone.Constants.VERTICAL_DIRECTION_PROP_NAME,
                        reportType = BlockFaceStateReport.class
                )
        },
        interfaceClass = PointedDripstone.class
)
public interface GlowPointedDripstone extends StatefulBlockData, PointedDripstone {
    class Constants {
        public static final String VERTICAL_DIRECTION_PROP_NAME = "vertical_direction";
        public static final Class<BlockFace> VERTICAL_DIRECTION_STATE_TYPE = BlockFace.class;
        public static final String THICKNESS_PROP_NAME = "";
    }

    @Override
    default BlockFace getVerticalDirection() {
        return getValue(Constants.VERTICAL_DIRECTION_PROP_NAME, Constants.VERTICAL_DIRECTION_STATE_TYPE);
    }

    @Override
    default void setVerticalDirection(@NotNull BlockFace direction) {
        setValue(Constants.VERTICAL_DIRECTION_PROP_NAME, Constants.VERTICAL_DIRECTION_STATE_TYPE, direction);
    }

    @Override
    default @NotNull Set<BlockFace> getVerticalDirections() {
        return getValidValues(Constants.VERTICAL_DIRECTION_PROP_NAME, Constants.VERTICAL_DIRECTION_STATE_TYPE);
    }
}
