package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProps;
import net.glowstone.redstone_transformer.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Jukebox;
import org.bukkit.block.data.type.Piston;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowPiston.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceName = "Piston"
)
public interface GlowPiston extends StatefulBlockData, Piston {
    class Constants {
        public static final String PROP_NAME = "extended";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isExtended() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setExtended(boolean extended) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, extended);
    }
}
