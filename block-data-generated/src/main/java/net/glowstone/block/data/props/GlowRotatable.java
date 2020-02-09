package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BlockFaceIdStateReport;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProp;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Rotatable;

@AssociatedWithProp(
    propName = GlowRotatable.Constants.PROP_NAME,
    reportType = BlockFaceIdStateReport.class,
    interfaceName = "Rotatable"
)
public interface GlowRotatable extends StatefulBlockData, Rotatable {
    class Constants {
        public static final String PROP_NAME = "rotation";
        public static final Class<BlockFace> STATE_TYPE = BlockFace.class;
    }

    @Override
    default BlockFace getRotation() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setRotation(BlockFace rotation) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, rotation);
    }
}
