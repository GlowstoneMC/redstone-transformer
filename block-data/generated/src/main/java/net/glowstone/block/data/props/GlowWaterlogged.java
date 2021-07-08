package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.Waterlogged;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowWaterlogged.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceClass = Waterlogged.class
)
public interface GlowWaterlogged extends StatefulBlockData, Waterlogged {
    class Constants {
        public static final String PROP_NAME = "waterlogged";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isWaterlogged() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setWaterlogged(boolean attached) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, attached);
    }
}
