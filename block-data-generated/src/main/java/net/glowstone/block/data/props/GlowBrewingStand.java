package net.glowstone.block.data.props;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.BrewingStand;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowBrewingStand.Constants.BOTTLE_0_PROP_NAME,
            reportType = BooleanStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowBrewingStand.Constants.BOTTLE_1_PROP_NAME,
            reportType = BooleanStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowBrewingStand.Constants.BOTTLE_2_PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceName = "BrewingStand"
)
public interface GlowBrewingStand extends StatefulBlockData, BrewingStand {
    class Constants {
        public static final String BOTTLE_0_PROP_NAME = "has_bottle_0";
        public static final String BOTTLE_1_PROP_NAME = "has_bottle_1";
        public static final String BOTTLE_2_PROP_NAME = "has_bottle_2";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean hasBottle(int bottle) {
        switch(bottle) {
            case 0:
                return getValue(Constants.BOTTLE_0_PROP_NAME, Constants.STATE_TYPE);
            case 1:
                return getValue(Constants.BOTTLE_1_PROP_NAME, Constants.STATE_TYPE);
            case 2:
                return getValue(Constants.BOTTLE_2_PROP_NAME, Constants.STATE_TYPE);
            default:
                return false;
        }
    }

    @Override
    default void setBottle(int bottle, boolean has) {
        switch(bottle) {
            case 0:
                setValue(Constants.BOTTLE_0_PROP_NAME, Constants.STATE_TYPE, has);
            case 1:
                setValue(Constants.BOTTLE_1_PROP_NAME, Constants.STATE_TYPE, has);
            case 2:
                setValue(Constants.BOTTLE_2_PROP_NAME, Constants.STATE_TYPE, has);
        }
    }

    @Override
    default Set<Integer> getBottles() {
        return Stream.of(0, 1, 2)
            .filter(this::hasBottle)
            .collect(Collectors.toSet());
    }

    @Override
    default int getMaximumBottles() {
        return 3;
    }
}
