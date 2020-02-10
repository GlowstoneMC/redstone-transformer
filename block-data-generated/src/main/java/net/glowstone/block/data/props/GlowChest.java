package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.ChestTypeStateReport;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProps;
import net.glowstone.redstone_transformer.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Chest;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowChest.Constants.PROP_NAME,
            reportType = ChestTypeStateReport.class
        )
    },
    interfaceName = "Bisected"
)
public interface GlowChest extends StatefulBlockData, Chest {
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
