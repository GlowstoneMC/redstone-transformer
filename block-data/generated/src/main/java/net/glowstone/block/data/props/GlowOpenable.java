package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.Openable;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowOpenable.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceClass = Openable.class
)
public interface GlowOpenable extends StatefulBlockData, Openable {
    class Constants {
        public static final String PROP_NAME = "open";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isOpen() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setOpen(boolean attached) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, attached);
    }
}
