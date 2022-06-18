package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.CaveVinesPlant;

@AssociatedWithProps(
        props = {
                @PropertyAssociation(
                        propName = GlowCaveVinesPlant.Constants.PROP_NAME,
                        reportType = BooleanStateReport.class
                )
        },
        interfaceClass = CaveVinesPlant.class
)
public interface GlowCaveVinesPlant extends StatefulBlockData, CaveVinesPlant {
    class Constants {
        public static final String PROP_NAME = "berries";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isBerries() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setBerries(boolean berries) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, berries);
    }
}
