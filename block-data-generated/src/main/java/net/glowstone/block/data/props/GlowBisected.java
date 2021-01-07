package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BisectedHalfStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.Bisected;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowBisected.Constants.PROP_NAME,
            reportType = BisectedHalfStateReport.class
        )
    },
    interfaceClass = Bisected.class
)
public interface GlowBisected extends StatefulBlockData, Bisected {
    class Constants {
        public static final String PROP_NAME = "half";
        public static final Class<Bisected.Half> STATE_TYPE = Bisected.Half.class;
    }

    @Override
    default Bisected.Half getHalf() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setHalf(Bisected.Half half) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, half);
    }
}
