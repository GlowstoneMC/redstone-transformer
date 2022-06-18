package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.Hangable;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.block.data.type.Lantern;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowHangable.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceClass = Hangable.class
)
public interface GlowHangable extends StatefulBlockData, Hangable {
    class Constants {
        public static final String PROP_NAME = "hanging";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isHanging() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setHanging(boolean hanging) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, hanging);
    }
}
