package com.dpi.financial.ftcom.web.annotation;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * http://stackoverflow.com/questions/38412301/what-is-the-equivalent-of-managedbeaneager-true-in-cdi
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Eager {
    //
}
