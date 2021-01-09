package net.glowstone.block.data.props;

import net.glowstone.processor.block.data.annotations.PropPolyfill;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.Powerable;
import org.bukkit.block.data.type.Switch;

@PropPolyfill(
    replaces = {
        Directional.class,
        FaceAttachable.class,
        Powerable.class
    },
    interfaceClass = Switch.class
)
public interface GlowSwitch extends GlowDirectional, GlowFaceAttachable, GlowPowerable, Switch {
    @Override
    default Face getFace() {
        return Face.valueOf(getAttachedFace().name());
    }

    @Override
    default void setFace(Face face) {
        setAttachedFace(AttachedFace.valueOf(face.name()));
    }
}
