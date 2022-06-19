package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.SculkShrieker;

@AssociatedWithProps(
        props = {
                @PropertyAssociation(
                        propName = GlowSculkShrieker.Constants.SUMMON_PROP_NAME,
                        reportType = BooleanStateReport.class
                ),
                @PropertyAssociation(
                        propName = GlowSculkShrieker.Constants.SHRIEKING_PROP_NAME,
                        reportType = BooleanStateReport.class
                )
        },
        interfaceClass = SculkShrieker.class
)
public interface GlowSculkShrieker extends StatefulBlockData, SculkShrieker {
    class Constants {
        public static final String SUMMON_PROP_NAME = "can_summon";
        public static final Class<Boolean> SUMMON_STATE_TYPE = Boolean.class;
        public static final String SHRIEKING_PROP_NAME = "shrieking";
        public static final Class<Boolean> SHRIEKING_STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isCanSummon() {
        return getValue(Constants.SUMMON_PROP_NAME, Constants.SUMMON_STATE_TYPE);
    }

    @Override
    default void setCanSummon(boolean berries) {
        setValue(Constants.SUMMON_PROP_NAME, Constants.SUMMON_STATE_TYPE, berries);
    }


    @Override
    default boolean isShrieking() {
        return getValue(Constants.SHRIEKING_PROP_NAME, Constants.SHRIEKING_STATE_TYPE);
    }

    @Override
    default void setShrieking(boolean berries) {
        setValue(Constants.SHRIEKING_PROP_NAME, Constants.SHRIEKING_STATE_TYPE, berries);
    }
}
