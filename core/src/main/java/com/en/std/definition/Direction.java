package com.en.std.definition;

/**
 * Created by h.mohammadi on 6/18/2016.
 */
public enum Direction implements IEnumFieldValue<String>{
    Incoming("I"),
    Outgoing("O");

    private final String direction;

    Direction(String direction) {
        this.direction = direction;
    }


    @Override
    public String getValue() {
        return null;
    }
}
