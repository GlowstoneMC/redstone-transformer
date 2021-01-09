package net.glowstone.block.data.props;

import net.glowstone.block.data.states.StatefulBlockData;
import net.glowstone.block.data.states.reports.BellAttachmentStateReport;
import net.glowstone.processor.block.data.annotations.AssociatedWithProps;
import net.glowstone.processor.block.data.annotations.PropertyAssociation;
import org.bukkit.block.data.type.Bell;

@AssociatedWithProps(
    props = {
        @PropertyAssociation(
            propName = GlowBell.Constants.PROP_NAME,
            reportType = BellAttachmentStateReport.class
        )
    },
    interfaceClass = Bell.class
)
public interface GlowBell extends StatefulBlockData, Bell {
    class Constants {
        public static final String PROP_NAME = "attachment";
        public static final Class<Attachment> STATE_TYPE = Attachment.class;
    }

    @Override
    default Attachment getAttachment() {
        return getValue(Constants.PROP_NAME, Constants.STATE_TYPE);
    }

    @Override
    default void setAttachment(Attachment attachment) {
        setValue(Constants.PROP_NAME, Constants.STATE_TYPE, attachment);
    }
}
