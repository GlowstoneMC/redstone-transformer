package net.glowstone.block.data.props;

import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.block.data.states.values.IntegerStateValue;
import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProp;
import org.bukkit.block.data.Ageable;

@AssociatedWithProp(
    propName = GlowAgeable.Constants.PROP_NAME,
    reportType = IntegerStateReport.class,
    interfaceName = "Ageable"
)
public interface GlowAgeable extends StatefulBlockData, Ageable {
    class Constants {
        public static final String PROP_NAME = "age";
        public static final Class<IntegerStateValue> STATE_TYPE = IntegerStateValue.class;
    }

    @Override
    default int getAge() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setAge(int age) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, age);
    }

    @Override
    default int getMaximumAge() {
        return getMaxValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }
}
