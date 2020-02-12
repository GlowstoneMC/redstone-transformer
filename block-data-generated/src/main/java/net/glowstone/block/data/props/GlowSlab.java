package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.ComparatorModeStateReport;
import net.glowstone.block.data.states.reports.SlabTypeStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Comparator;
import org.bukkit.block.data.type.Slab;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowSlab.Constants.PROP_NAME,
            reportType = SlabTypeStateReport.class
        )
    },
    interfaceName = "Slab"
)
public interface GlowSlab extends StatefulBlockData, Slab {
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
