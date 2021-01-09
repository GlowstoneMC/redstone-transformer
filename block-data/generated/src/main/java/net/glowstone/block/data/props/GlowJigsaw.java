package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.IntegerStateReport;
import net.glowstone.block.data.states.reports.JigsawOrientationStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.Jigsaw;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowJigsaw.Constants.PROP_NAME,
            reportType = JigsawOrientationStateReport.class
        )
    },
    interfaceClass = Jigsaw.class
)
public interface GlowJigsaw extends StatefulBlockData, Jigsaw {
    class Constants {
        public static final String PROP_NAME = "orientation";
        public static final Class<Jigsaw.Orientation> STATE_TYPE = Jigsaw.Orientation.class;
    }

    @Override
    default Jigsaw.Orientation getOrientation() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setOrientation(Jigsaw.Orientation orientation) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, orientation);
    }
}
