package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BedPartStateReport;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Bed;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowBed.Constants.PART_PROP_NAME,
            reportType = BedPartStateReport.class
        ),
        @PropertyAssociation(
            propName = GlowBed.Constants.OCCUPIED_PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceClass = Bed.class
)
public interface GlowBed extends StatefulBlockData, Bed {
    class Constants {
        public static final String PART_PROP_NAME = "part";
        public static final Class<Part> PART_STATE_TYPE = Part.class;
        public static final String OCCUPIED_PROP_NAME = "occupied";
        public static final Class<Boolean> OCCUPIED_STATE_TYPE = Boolean.class;
    }

    @Override
    default Part getPart() {
        return getValue(Constants.PART_PROP_NAME, Constants.PART_STATE_TYPE);
    }

    @Override
    default void setPart(Part part) {
        setValue(Constants.PART_PROP_NAME, Constants.PART_STATE_TYPE, part);
    }

    @Override
    default boolean isOccupied() {
        return getValue(Constants.OCCUPIED_PROP_NAME, Constants.OCCUPIED_STATE_TYPE);
    }
}
