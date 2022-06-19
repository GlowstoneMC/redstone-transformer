package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.SculkCatalyst;

@AssociatedWithProps(
        props = {
                @PropertyAssociation(
                        propName = GlowSculkCatalyst.Constants.PROP_NAME,
                        reportType = BooleanStateReport.class
                )
        },
        interfaceClass = SculkCatalyst.class
)
public interface GlowSculkCatalyst extends StatefulBlockData, SculkCatalyst {
    class Constants {
        public static final String PROP_NAME = "bloom";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isBloom() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setBloom(boolean berries) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, berries);
    }
}
