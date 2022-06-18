package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BigDripleafStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.BigDripleaf;
import org.jetbrains.annotations.NotNull;

@AssociatedWithProps(
        props = {
                @PropertyAssociation(
                        propName = GlowBigDripleaf.Constants.PROP_NAME,
                        reportType = BigDripleafStateReport.class
                )
        },
        interfaceClass = BigDripleaf.class
)
public interface GlowBigDripleaf extends StatefulBlockData, BigDripleaf {
    class Constants {
        public static final String PROP_NAME = "tilt";
        public static final Class<BigDripleaf.Tilt> STATE_TYPE = BigDripleaf.Tilt.class;
    }

    @Override
    default BigDripleaf.Tilt getTilt() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setTilt(@NotNull Tilt tilt) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, tilt);
    }
}
