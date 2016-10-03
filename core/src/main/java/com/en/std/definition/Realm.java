package com.en.std.definition;

/**
 * Created by h.mohammadi on 6/18/2016.
 */
public enum Realm implements IEnumFieldValue<String> {
    SHETAB("H"),
    HOST_INTERFACE("I"),
    DEVICE("D");

    private final String realm;

    Realm(String realm) {
        this.realm = realm;
    }

    @Override
    public String getValue() {
        return realm;
    }
}
