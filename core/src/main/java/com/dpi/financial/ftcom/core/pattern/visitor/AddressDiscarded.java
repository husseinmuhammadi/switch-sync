package com.dpi.financial.ftcom.core.pattern.visitor;

/**
 * Visitor Pattern
 * https://en.wikipedia.org/wiki/Visitor_pattern
 */
public class AddressDiscarded implements Event {
    @Override
    public void accept(Visitor visitor) {
        visitor.visitAddressDiscarded(this);
    }
}
