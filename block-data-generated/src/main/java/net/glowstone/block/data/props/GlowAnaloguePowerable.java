package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import org.bukkit.block.data.AnaloguePowerable;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowAnaloguePowerable.Constants.PROP_NAME,
            reportType = IntegerStateReport.class
        )
    },
    interfaceName = "AnaloguePowerable"
)
public interface GlowAnaloguePowerable extends StatefulBlockData, AnaloguePowerable {
    class Constants {
        public static final String PROP_NAME = "power";
        public static final Class<Integer> STATE_TYPE = Integer.class;
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
