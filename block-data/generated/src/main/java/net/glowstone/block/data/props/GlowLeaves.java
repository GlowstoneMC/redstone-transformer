package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Leaves;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowLeaves.Constants.PERSISTENT_PROP_NAME,
            reportType = BooleanStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowLeaves.Constants.DISTANCE_PROP_NAME,
            reportType = IntegerStateReport.class
        )
    },
    interfaceClass = Leaves.class
)
public interface GlowLeaves extends StatefulBlockData, Leaves {
    class Constants {
        public static final String PERSISTENT_PROP_NAME = "persistent";
        public static final Class<Boolean> PERSISTENT_STATE_TYPE = Boolean.class;
        public static final String DISTANCE_PROP_NAME = "distance";
        public static final Class<Integer> DISTANCE_STATE_TYPE = Integer.class;
    }

    @Override
    default boolean isPersistent() {
        return getValue(Constants.PERSISTENT_PROP_NAME, Constants.PERSISTENT_STATE_TYPE);
    }

    @Override
    default void setPersistent(boolean persistent) {
        setValue(Constants.PERSISTENT_PROP_NAME, Constants.PERSISTENT_STATE_TYPE, persistent);
    }

    @Override
    default int getDistance() {
        return getValue(Constants.DISTANCE_PROP_NAME, Constants.DISTANCE_STATE_TYPE);
    }

    @Override
    default void setDistance(int distance) {
        setValue(Constants.DISTANCE_PROP_NAME, Constants.DISTANCE_STATE_TYPE, distance);
    }
}
