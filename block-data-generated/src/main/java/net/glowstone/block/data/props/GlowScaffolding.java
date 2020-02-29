package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BedPartStateReport;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Scaffolding;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowScaffolding.Constants.DISTANCE_PROP_NAME,
            reportType = IntegerStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowScaffolding.Constants.BOTTOM_PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceName = "Scaffolding"
)
public interface GlowScaffolding extends StatefulBlockData, Scaffolding {
    class Constants {
        public static final String DISTANCE_PROP_NAME = "distance";
        public static final Class<Integer> DISTANCE_STATE_TYPE = Integer.class;
        public static final String BOTTOM_PROP_NAME = "bottom";
        public static final Class<Boolean> BOTTOM_STATE_TYPE = Boolean.class;
    }

    @Override
    default int getDistance() {
        return getValue(Constants.DISTANCE_PROP_NAME, Constants.DISTANCE_STATE_TYPE);
    }

    @Override
    default void setDistance(int distance) {
        setValue(Constants.DISTANCE_PROP_NAME, Constants.DISTANCE_STATE_TYPE, distance);
    }

    @Override
    default int getMaximumDistance() {
        return getMaxValue(Constants.DISTANCE_PROP_NAME, Constants.DISTANCE_STATE_TYPE);
    }

    @Override
    default boolean isBottom() {
        return getValue(Constants.BOTTOM_PROP_NAME, Constants.BOTTOM_STATE_TYPE);
    }

    @Override
    default void setBottom(boolean bottom) {
        setValue(Constants.BOTTOM_PROP_NAME, Constants.BOTTOM_STATE_TYPE, bottom);
    }
}
