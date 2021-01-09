package net.glowstone.processor.block.data.annotations;

import org.bukkit.block.data.BlockData;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface PropPolyfill {
    Class<? extends BlockData>[] replaces();
    Class<? extends BlockData> interfaceClass();
}
