package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.CommandBlock;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowCommandBlock.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceClass = CommandBlock.class
)
public interface GlowCommandBlock extends StatefulBlockData, CommandBlock {
    class Constants {
        public static final String PROP_NAME = "conditional";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isConditional() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setConditional(boolean conditional) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, conditional);
    }
}
