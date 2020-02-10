package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProps;
import net.glowstone.redstone_transformer.annotations.PropertyAssociation;
import org.bukkit.block.data.Snowable;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowSnowable.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceName = "Snowable"
)
public interface GlowSnowable extends StatefulBlockData, Snowable {
    class Constants {
        public static final String PROP_NAME = "snowy";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isSnowy() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setSnowy(boolean attached) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, attached);
    }
}
