package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.block.data.type.SeaPickle;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowSeaPickle.Constants.PROP_NAME,
            reportType = IntegerStateReport.class
        )
    },
    interfaceName = "SeaPickle"
)
public interface GlowSeaPickle extends StatefulBlockData, SeaPickle {
    class Constants {
        public static final String PROP_NAME = "pickles";
        public static final Class<Integer> STATE_TYPE = Integer.class;
    }

    @Override
    default int getPickles() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setPickles(int pickles) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, pickles);
    }

    @Override
    default int getMinimumPickles() {
        return getMinValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default int getMaximumPickles() {
        return getMaxValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }
}
