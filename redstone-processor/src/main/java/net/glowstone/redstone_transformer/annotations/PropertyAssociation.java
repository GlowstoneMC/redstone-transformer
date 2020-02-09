package net.glowstone.redstone_transformer.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.glowstone.block.data.states.reports.StateReport;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface PropertyAssociation {
    /**
     * The properties that this type is associated with.
     */
    String propName();
    Class<? extends StateReport<?>> reportType();
    boolean required() default true;
}
