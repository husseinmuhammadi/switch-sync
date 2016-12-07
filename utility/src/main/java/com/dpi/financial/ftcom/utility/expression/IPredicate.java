package com.dpi.financial.ftcom.utility.expression;

/**
 * Created by h.mohammadi on 12/4/2016.
 */
public interface IPredicate<T> {
    boolean apply(T type);
}
