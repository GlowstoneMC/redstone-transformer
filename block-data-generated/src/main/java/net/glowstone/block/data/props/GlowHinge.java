package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.HingeStateReport;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProps;
import net.glowstone.redstone_transformer.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Door;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowHinge.Constants.PROP_NAME,
            reportType = HingeStateReport.class
        )
    },
    interfaceName = "Hinge"
)
public interface GlowHinge extends StatefulBlockData, Door {
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
