package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.SculkSensorPhaseStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.SculkSensor;
import org.jetbrains.annotations.NotNull;

@AssociatedWithProps(
        props = {
                @PropertyAssociation(
                        propName = GlowSculkSensor.Constants.PROP_NAME,
                        reportType = SculkSensorPhaseStateReport.class
                )
        },
        interfaceClass = SculkSensor.class
)
public interface GlowSculkSensor extends StatefulBlockData, SculkSensor {
    class Constants {
        public static final String PROP_NAME= "sculk_sensor_phase";
        public static final Class<Phase> STATE_TYPE = Phase.class;
    }

    @Override
    default @NotNull Phase getPhase() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setPhase(@NotNull Phase phase) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, phase);
    }
}
