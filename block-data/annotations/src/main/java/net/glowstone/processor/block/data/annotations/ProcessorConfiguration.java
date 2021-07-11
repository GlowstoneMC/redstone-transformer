package net.glowstone.processor.block.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.SOURCE)
public @interface ProcessorConfiguration {
    String blockDataManagerPackage();
    String blockDataImplPackage();
    String blockDataBlockManagerPackage();
}
