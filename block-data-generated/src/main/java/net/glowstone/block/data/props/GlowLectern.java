package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Lantern;
import org.bukkit.block.data.type.Lectern;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowLectern.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceName = "Lectern"
)
public interface GlowLectern extends StatefulBlockData, Lectern {
    class Constants {
        public static final String PROP_NAME = "has_book";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean hasBook() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }
}
