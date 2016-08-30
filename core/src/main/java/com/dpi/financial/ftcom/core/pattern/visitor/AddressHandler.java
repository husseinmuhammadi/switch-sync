package com.dpi.financial.ftcom.core.pattern.visitor;

/**
 * Visitor Pattern
 * https://en.wikipedia.org/wiki/Visitor_pattern
 */
public class AddressHandler implements Visitor {
    void handle(Event e){
        e.accept(this);
    }

    @Override
    public void visitAddressChanged(AddressChanged e) {

    }

    @Override
    public void visitAddressDiscarded(AddressDiscarded e) {

    }
}
