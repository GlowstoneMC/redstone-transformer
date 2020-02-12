package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Piston;
import org.bukkit.block.data.type.TNT;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowTNT.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceName = "TNT"
)
public interface GlowTNT extends StatefulBlockData, TNT {
    class Constants {
        public static final String PROP_NAME = "unstable";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isUnstable() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setUnstable(boolean extended) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, extended);
    }
}
