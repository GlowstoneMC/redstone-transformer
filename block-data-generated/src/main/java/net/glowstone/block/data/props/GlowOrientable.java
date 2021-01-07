package net.glowstone.block.data.props;

import java.util.Set;
import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.AxisStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.Axis;
import org.bukkit.block.data.Orientable;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowOrientable.Constants.PROP_NAME,
            reportType = AxisStateReport.class
        )
    },
    interfaceClass = Orientable.class
)
public interface GlowOrientable extends StatefulBlockData, Orientable {
    class Constants {
        public static final String PROP_NAME = "axis";
        public static final Class<Axis> STATE_TYPE = Axis.class;
    }

    @Override
    default Axis getAxis() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setAxis(Axis axis) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, axis);
    }

    @Override
    default Set<Axis> getAxes() {
        return getValidValues(Constants.PROP_NAME, Constants.STATE_TYPE);
    }
}
