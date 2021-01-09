package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.Farmland;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowFarmland.Constants.PROP_NAME,
            reportType = IntegerStateReport.class
        )
    },
    interfaceClass = Farmland.class
)
public interface GlowFarmland extends StatefulBlockData, Farmland {
    class Constants {
        public static final String PROP_NAME = "moisture";
        public static final Class<Integer> STATE_TYPE = Integer.class;
    }

    @Override
    default int getMoisture() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setMoisture(int age) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, age);
    }

    @Override
    default int getMaximumMoisture() {
        return getMaxValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }
}
