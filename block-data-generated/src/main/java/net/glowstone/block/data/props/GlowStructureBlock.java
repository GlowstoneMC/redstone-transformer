package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.StairsShapeStateReport;
import net.glowstone.block.data.states.reports.StructureBlockModeStateReport;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProps;
import net.glowstone.redstone_transformer.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.StructureBlock;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowStructureBlock.Constants.PROP_NAME,
            reportType = StructureBlockModeStateReport.class
        )
    },
    interfaceName = "StructureBlock"
)
public interface GlowStructureBlock extends StatefulBlockData, StructureBlock {
    class Constants {
        public static final String PROP_NAME = "mode";
        public static final Class<Mode> STATE_TYPE = Mode.class;
    }

    @Override
    default Mode getMode() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setMode(Mode mode) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, mode);
    }
}
