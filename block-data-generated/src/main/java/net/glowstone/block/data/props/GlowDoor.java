package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.HingeStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Door;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowDoor.Constants.PROP_NAME,
            reportType = HingeStateReport.class
        )
    },
    interfaceClass = Door.class
)
public interface GlowDoor extends StatefulBlockData, Door {
    class Constants {
        public static final String PROP_NAME = "hinge";
        public static final Class<Hinge> STATE_TYPE = Hinge.class;
    }

    @Override
    default Hinge getHinge() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setHinge(Hinge hinge) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, hinge);
    }
}
