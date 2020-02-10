package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.StructureBlockModeStateReport;
import net.glowstone.block.data.states.reports.SwitchFaceStateReport;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProps;
import net.glowstone.redstone_transformer.annotations.PropertyAssociation;
import org.bukkit.block.data.type.StructureBlock;
import org.bukkit.block.data.type.Switch;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowSwitch.Constants.PROP_NAME,
            reportType = SwitchFaceStateReport.class
        )
    },
    interfaceName = "Switch"
)
public interface GlowSwitch extends StatefulBlockData, Switch {
    class Constants {
        public static final String PROP_NAME = "face";
        public static final Class<Face> STATE_TYPE = Face.class;
    }

    @Override
    default Face getFace() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setFace(Face face) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, face);
    }
}
