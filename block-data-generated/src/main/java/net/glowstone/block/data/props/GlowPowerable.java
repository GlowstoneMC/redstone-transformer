package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.block.data.states.values.IntegerStateValue;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProp;
import org.bukkit.block.data.AnaloguePowerable;

@AssociatedWithProp(
    propName = GlowPowerable.Constants.PROP_NAME,
    reportType = IntegerStateReport.class,
    interfaceName = "Powerable"
)
public interface GlowPowerable extends StatefulBlockData, AnaloguePowerable {
    class Constants {
        public static final String PROP_NAME = "power";
        public static final Class<IntegerStateValue> STATE_TYPE = IntegerStateValue.class;
    }

    @Override
    default int getPower() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setPower(int power) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, power);
    }

    @Override
    default int getMaximumPower() {
        return getMaxValue(GlowAgeable.Constants.PROP_NAME, GlowAgeable.Constants.STATE_TYPE);
    }
}
