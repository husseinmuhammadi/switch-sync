package com.dpi.financial.ftcom.model.type.ascii;

import com.dpi.financial.ftcom.model.type.IEnumFieldValue;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;

/**
 *
 */
public enum ControlCharacter implements IEnumFieldValue<Byte> {
    NUL((byte) 0x00, "NUL", "&#000;", null, "Null char"),
    SOH((byte) 0x01, "SOH", "&#001;", null, "Start of Heading"),
    STX((byte) 0x02, "STX", "&#002;", null, "Start of Text"),
    ETX((byte) 0x03, "ETX", "&#003;", null, "End of Text"),
    EOT((byte) 0x04, "EOT", "&#004;", null, "End of Transmission"),
    ENQ((byte) 0x05, "ENQ", "&#005;", null, "Enquiry"),
    ACK((byte) 0x06, "ACK", "&#006;", null, "Acknowledgment"),
    BEL((byte) 0x07, "BEL", "&#007;", null, "Bell"),
    BS((byte) 0x08, "BS", "&#008;", null, "Back Space"),
    HT((byte) 0x09, "HT", "&#009;", null, "Horizontal Tab"),
    LF((byte) 0x0A, "LF", "&#010;", null, "Line Feed"),
    VT((byte) 0x0B, "VT", "&#011;", null, "Vertical Tab"),
    FF((byte) 0x0C, "FF", "&#012;", null, "Form Feed"),
    CR((byte) 0x0D, "CR", "&#013;", null, "Carriage Return"),
    SO((byte) 0x0E, "SO", "&#014;", null, "Shift Out / X-On"),
    SI((byte) 0x0F, "SI", "&#015;", null, "Shift In / X-Off"),
    DLE((byte) 0x10, "DLE", "&#016;", null, "Data Line Escape"),
    DC1((byte) 0x11, "DC1", "&#017;", null, "Device Control 1 (oft. XON)"),
    DC2((byte) 0x12, "DC2", "&#018;", null, "Device Control 2"),
    DC3((byte) 0x13, "DC3", "&#019;", null, "Device Control 3 (oft. XOFF)"),
    DC4((byte) 0x14, "DC4", "&#020;", null, "Device Control 4"),
    NAK((byte) 0x15, "NAK", "&#021;", null, "Negative Acknowledgement"),
    SYN((byte) 0x16, "SYN", "&#022;", null, "Synchronous Idle"),
    ETB((byte) 0x17, "ETB", "&#023;", null, "End of Transmit Block"),
    CAN((byte) 0x18, "CAN", "&#024;", null, "Cancel"),
    EM((byte) 0x19, "EM", "&#025;", null, "End of Medium"),
    SUB((byte) 0x1A, "SUB", "&#026;", null, "Substitute"),
    ESC((byte) 0x1B, "ESC", "&#027;", null, "Escape"),
    FS((byte) 0x1C, "FS", "&#028;", null, "File Separator"),
    GS((byte) 0x1D, "GS", "&#029;", null, "Group Separator"),
    RS((byte) 0x1E, "RS", "&#030;", null, "Record Separator"),
    US((byte) 0x1F, "US", "&#031;", null, "Unit Separator"),;

    private final byte value;
    private final String symbol;
    private final String htmlNumber;
    private final String htmlName;
    private final String description;

    ControlCharacter(byte value, String symbol, String htmlNumber, String htmlName, String description) {
        this.value = value;
        this.symbol = symbol;
        this.htmlNumber = htmlNumber;
        this.htmlName = htmlName;
        this.description = description;
    }

    public static ControlCharacter getInstance(byte value) {
        for (ControlCharacter controlCharacter : values()) {
            if (controlCharacter.getValue().equals(value))
                return controlCharacter;
        }

        throw new TypeNotFoundException(ControlCharacter.class.getName()
                + " Error creating instance for ascii ControlCharacter: " + value);
    }

    @Override
    public Byte getValue() {
        return this.value;
    }

    public Character getCharacter() {
        return new Character((char) this.value);
    }

    public String getSymbol() {
        return symbol;
    }

    public String getHtmlNumber() {
        return htmlNumber;
    }

    public String getHtmlName() {
        return htmlName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String getFullName() {
        return this.getClass().getName() + "." + this.name();
    }
}
