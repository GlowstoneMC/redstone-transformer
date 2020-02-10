package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProps;
import net.glowstone.redstone_transformer.annotations.PropertyAssociation;
import org.bukkit.block.data.type.SeaPickle;
import org.bukkit.block.data.type.Snow;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowSnow.Constants.PROP_NAME,
            reportType = IntegerStateReport.class
        )
    },
    interfaceName = "Snow"
)
public interface GlowSnow extends StatefulBlockData, Snow {
    class Constants {
        public static final String PROP_NAME = "layers";
        public static final Class<Integer> STATE_TYPE = Integer.class;
    }

    @Override
    default int getLayers() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setLayers(int pickles) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, pickles);
    }

    @Override
    default int getMinimumLayers() {
        return getMinValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default int getMaximumLayers() {
        return getMaxValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }
}
