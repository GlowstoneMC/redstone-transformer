package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.ChestTypeStateReport;
import net.glowstone.block.data.states.reports.ComparatorModeStateReport;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProps;
import net.glowstone.redstone_transformer.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Comparator;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowComparator.Constants.PROP_NAME,
            reportType = ComparatorModeStateReport.class
        )
    },
    interfaceName = "Comparator"
)
public interface GlowComparator extends StatefulBlockData, Comparator {
    class Constants {
        public static final String PROP_NAME = "mode";
        public static final Class<Mode> STATE_TYPE = Mode.class;
    }

    @Override
    default Mode getMode() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setMode(Mode mode) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, mode);
    }
}
