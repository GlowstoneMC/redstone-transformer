package net.glowstone.block.data.props;

import java.util.Set;
import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BlockFaceStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowDirectional.Constants.PROP_NAME,
            reportType = BlockFaceStateReport.class
        )
    },
    interfaceName = "Directional"
)
public interface GlowDirectional extends StatefulBlockData, Directional {
    class Constants {
        public static final String PROP_NAME = "facing";
        public static final Class<BlockFace> STATE_TYPE = BlockFace.class;
    }

    @Override
    default BlockFace getFacing() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setFacing(BlockFace facing) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, facing);
    }

    @Override
    default Set<BlockFace> getFaces() {
        return getValidValues(Constants.PROP_NAME, Constants.STATE_TYPE);
    }
}
