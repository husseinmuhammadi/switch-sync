package com.dpi.financial.ftcom.core.pattern.visitor;

/**
 * Visitor Pattern
 * https://en.wikipedia.org/wiki/Visitor_pattern
 */
public interface Visitor {
    void visitAddressChanged(AddressChanged e);
    void visitAddressDiscarded(AddressDiscarded e);
}
