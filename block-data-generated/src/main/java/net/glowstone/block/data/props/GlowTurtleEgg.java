package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.block.data.type.TurtleEgg;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowTurtleEgg.Constants.EGGS_PROP_NAME,
            reportType = IntegerStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowTurtleEgg.Constants.HATCH_PROP_NAME,
            reportType = IntegerStateReport.class
        )
    },
    interfaceName = "TurtleEgg"
)
public interface GlowTurtleEgg extends StatefulBlockData, TurtleEgg {
    class Constants {
        public static final String EGGS_PROP_NAME = "eggs";
        public static final String HATCH_PROP_NAME = "hatch";
        public static final Class<Integer> STATE_TYPE = Integer.class;
    }

    @Override
    default int getEggs() {
        return getValue(Constants.EGGS_PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setEggs(int eggs) {
        setValue(Constants.EGGS_PROP_NAME, Constants.STATE_TYPE, eggs);
    }

    @Override
    default int getMinimumEggs() {
        return getMinValue(Constants.EGGS_PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default int getMaximumEggs() {
        return getMaxValue(Constants.EGGS_PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default int getHatch() {
        return getValue(Constants.HATCH_PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setHatch(int hatch) {
        setValue(Constants.HATCH_PROP_NAME, Constants.STATE_TYPE, hatch);
    }

    @Override
    default int getMaximumHatch() {
        return getMaxValue(Constants.EGGS_PROP_NAME, Constants.STATE_TYPE);
    }
}
