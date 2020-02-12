package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.Note;
import org.bukkit.block.data.type.Repeater;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowRepeater.Constants.DELAY_PROP_NAME,
            reportType = IntegerStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowRepeater.Constants.LOCKED_PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceName = "Repeater"
)
public interface GlowRepeater extends StatefulBlockData, Repeater {
    class Constants {
        public static final String DELAY_PROP_NAME = "delay";
        public static final Class<Integer> DELAY_STATE_TYPE = Integer.class;
        public static final String LOCKED_PROP_NAME = "locked";
        public static final Class<Boolean> LOCKED_STATE_TYPE = Boolean.class;
    }

    @Override
    default int getDelay() {
        return getValue(Constants.DELAY_PROP_NAME, Constants.DELAY_STATE_TYPE);
    }

    @Override
    default void setDelay(int delay) {
        setValue(Constants.DELAY_PROP_NAME, Constants.DELAY_STATE_TYPE, delay);
    }

    @Override
    default int getMinimumDelay() {
        return getMinValue(Constants.DELAY_PROP_NAME, Constants.DELAY_STATE_TYPE);
    }

    @Override
    default int getMaximumDelay() {
        return getMaxValue(Constants.DELAY_PROP_NAME, Constants.DELAY_STATE_TYPE);
    }

    @Override
    default boolean isLocked() {
        return getValue(Constants.LOCKED_PROP_NAME, Constants.LOCKED_STATE_TYPE);
    }

    @Override
    default void setLocked(boolean locked) {
        setValue(Constants.LOCKED_PROP_NAME, Constants.LOCKED_STATE_TYPE, locked);
    }
}
