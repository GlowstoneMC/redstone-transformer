package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.Levelled;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowLevelled.Constants.PROP_NAME,
            reportType = IntegerStateReport.class
        )
    },
    interfaceName = "Levelled"
)
public interface GlowLevelled extends StatefulBlockData, Levelled {
    class Constants {
        public static final String PROP_NAME = "level";
        public static final Class<Integer> STATE_TYPE = Integer.class;
    }

    @Override
    default int getLevel() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setLevel(int level) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, level);
    }

    @Override
    default int getMaximumLevel() {
        return getMaxValue(GlowAgeable.Constants.PROP_NAME, GlowAgeable.Constants.STATE_TYPE);
    }
}
