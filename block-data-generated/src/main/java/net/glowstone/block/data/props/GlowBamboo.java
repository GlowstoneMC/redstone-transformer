package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BambooLeavesStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Bamboo;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowBamboo.Constants.PROP_NAME,
            reportType = BambooLeavesStateReport.class
        )
    },
    interfaceName = "Bamboo"
)
public interface GlowBamboo extends StatefulBlockData, Bamboo {
    class Constants {
        public static final String PROP_NAME = "leaves";
        public static final Class<Leaves> STATE_TYPE = Leaves.class;
    }

    @Override
    default Leaves getLeaves() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setLeaves(Leaves leaves) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, leaves);
    }
}
