package net.glowstone.block.data.props;

import java.util.Set;
import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.RailShapeStateReport;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProp;
import org.bukkit.block.data.Rail;

@AssociatedWithProp(
    propName = GlowRail.Constants.PROP_NAME,
    reportType = RailShapeStateReport.class,
    interfaceName = "Rail"
)
public interface GlowRail extends StatefulBlockData, Rail {
    class Constants {
        public static final String PROP_NAME = "shape";
        public static final Class<Shape> STATE_TYPE = Shape.class;
    }

    @Override
    default Shape getShape() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setShape(Shape shape) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, shape);
    }

    @Override
    default Set<Shape> getShapes() {
        return getValidValues(Constants.PROP_NAME, Constants.STATE_TYPE);
    }
}
