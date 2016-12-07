package com.dpi.financial.ftcom.utility.expression;

import java.util.ArrayList;
import java.util.Collection;

/**
 * http://stackoverflow.com/questions/122105/what-is-the-best-way-to-filter-a-java-collection
 */
public class Predicate {
    public static Object predicateParams;

    public static <T> Collection<T> filter(Collection<T> target, IPredicate<T> predicate) {
        Collection<T> result = new ArrayList<T>();
        for (T element : target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }

    public static <T> T select(Collection<T> target, IPredicate<T> predicate) {
        T result = null;
        for (T element : target) {
            if (!predicate.apply(element))
                continue;
            result = element;
            break;
        }
        return result;
    }

    public static <T> T select(Collection<T> target, IPredicate<T> predicate, T defaultValue) {
        T result = defaultValue;
        for (T element : target) {
            if (!predicate.apply(element))
                continue;
            result = element;
            break;
        }
        return result;
    }
}
