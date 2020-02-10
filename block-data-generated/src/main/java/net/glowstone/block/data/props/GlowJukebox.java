package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BooleanStateReport;
import net.glowstone.redstone_transformer.annotations.AssociatedWithProps;
import net.glowstone.redstone_transformer.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Jukebox;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowJukebox.Constants.PROP_NAME,
            reportType = BooleanStateReport.class
        )
    },
    interfaceName = "Jukebox"
)
public interface GlowJukebox extends StatefulBlockData, Jukebox {
    class Constants {
        public static final String PROP_NAME = "has_record";
        public static final Class<Boolean> STATE_TYPE = Boolean.class;
    }

    @Override
    default boolean hasRecord() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }
}
