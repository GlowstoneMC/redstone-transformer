package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.BubbleColumn;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowBubbleColumn.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceClass = BubbleColumn.class
)
public interface GlowBubbleColumn extends StatefulBlockData, BubbleColumn {
    class Constants {
        public static final String PROP_NAME = "drag";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean isDrag() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setDrag(boolean drag) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, drag);
    }
}
