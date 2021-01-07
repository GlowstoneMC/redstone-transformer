package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.EndPortalFrame;
import org.bukkit.block.data.type.Gate;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowGate.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceClass = Gate.class
)
public interface GlowGate extends StatefulBlockData, Gate {
    class Constants {
        public static final String PROP_NAME = "in_wall";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isInWall() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setInWall(boolean inWall) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, inWall);
    }
}
