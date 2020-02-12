package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.PistonHead;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowPistonHead.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceName = "PistonHead"
)
public interface GlowPistonHead extends StatefulBlockData, PistonHead {
    class Constants {
        public static final String PROP_NAME = "short";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isShort() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setShort(boolean _short) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, _short);
    }
}
