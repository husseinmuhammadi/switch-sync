package com.en.std.definition;

/**
 * Created by h.mohammadi on 6/18/2016.
 */
public enum TransactionState implements IEnumFieldValue<String> {

    SEND_TO_SHETAB(Realm.SHETAB, Direction.Outgoing),
    SEND_TO_HIF(Realm.HOST_INTERFACE, Direction.Outgoing);

    private final Realm realm;
    private final Direction direction;

    TransactionState(Realm realm, Direction direction) {
        this.realm = realm;
        this.direction = direction;
    }

    @Override
    public String getValue() {
        return realm.getValue() + direction.getValue();
    }
}
