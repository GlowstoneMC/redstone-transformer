package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.SlabTypeStateReport;
import net.glowstone.block.data.states.reports.StairsShapeStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowStairs.Constants.PROP_NAME,
            reportType = StairsShapeStateReport.class
        )
    },
    interfaceName = "Stairs"
)
public interface GlowStairs extends StatefulBlockData, Stairs {
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
}
