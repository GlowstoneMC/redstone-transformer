package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProp;
import org.bukkit.block.data.Lightable;

@AssociatedWithProp(
    propName = GlowLightable.Constants.PROP_NAME,
    reportType = BooleanStateReport.class,
    interfaceName = "Lightable"
)
public interface GlowLightable extends StatefulBlockData, Lightable {
    class Constants {
        public static final String PROP_NAME = "lit";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isLit() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setLit(boolean attached) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, attached);
    }
}