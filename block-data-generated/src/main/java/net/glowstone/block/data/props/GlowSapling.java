package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProps;
import net.glowstone.redstone_transformer.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Sapling;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowSapling.Constants.PROP_NAME,
            reportType = IntegerStateReport.class
        )
    },
    interfaceName = "Sapling"
)
public interface GlowSapling extends StatefulBlockData, Sapling {
    class Constants {
        public static final String PROP_NAME = "stage";
        public static final Class<Integer> STATE_TYPE = Integer.class;
    }

    @Override
    default int getStage() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setStage(int stage) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, stage);
    }

    @Override
    default int getMaximumStage() {
        return getMaxValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }
}
