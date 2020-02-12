package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Cake;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowCake.Constants.PROP_NAME,
            reportType = IntegerStateReport.class
        )
    },
    interfaceName = "Cake"
)
public interface GlowCake extends StatefulBlockData, Cake {
    class Constants {
        public static final String PROP_NAME = "bites";
        public static final Class<Integer> STATE_TYPE = Integer.class;
    }

    @Override
    default int getBites() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setBites(int age) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, age);
    }

    @Override
    default int getMaximumBites() {
        return getMaxValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }
}
