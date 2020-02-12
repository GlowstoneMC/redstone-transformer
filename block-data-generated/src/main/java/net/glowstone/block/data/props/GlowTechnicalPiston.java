package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.TechnicalPistonTypeStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.TechnicalPiston;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowTechnicalPiston.Constants.PROP_NAME,
            reportType = TechnicalPistonTypeStateReport.class
        )
    },
    interfaceName = "TechnicalPiston"
)
public interface GlowTechnicalPiston extends StatefulBlockData, TechnicalPiston {
    class Constants {
        public static final String PROP_NAME = "type";
        public static final Class<Type> STATE_TYPE = Type.class;
    }

    @Override
    default Type getType() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setType(Type type) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, type);
    }
}
