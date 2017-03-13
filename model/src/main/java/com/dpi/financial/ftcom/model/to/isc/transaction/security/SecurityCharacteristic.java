package com.dpi.financial.ftcom.model.to.isc.transaction.security;

import com.dpi.financial.ftcom.model.to.isc.validator.Validator;
import com.dpi.financial.ftcom.model.type.isc.DeviceCode;
import com.dpi.financial.ftcom.utility.exception.model.TypeNotFoundException;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.PosDataCode;

/**
 * Represent Security Characteristic for SHETAB based on org.jpos.iso.PosDataCode.SecurityCharacteristic
 */
public class SecurityCharacteristic implements Validator {

    // private byte[] b = new byte[4];

    private final NetworkType networkType;
    private final MacingType macingType;
    private final EncryptionType encryptionType;
    private final EncryptionMethod encryptionMethod;

    public enum NetworkType {
        PRIVATE_NETWORK(1, "Private network", PosDataCode.SecurityCharacteristic.PRIVATE_NETWORK),
        OPEN_NETWORK(2, "Open network (Internet)", PosDataCode.SecurityCharacteristic.OPEN_NETWORK);

        private final int value;
        private final String description;
        private final PosDataCode.SecurityCharacteristic isoSecurityCharacteristic;

        NetworkType(int value, String description, PosDataCode.SecurityCharacteristic isoSecurityCharacteristic) {
            this.value = value;
            this.description = description;
            this.isoSecurityCharacteristic = isoSecurityCharacteristic;
        }

        public static NetworkType valueOf(int value) {
            for (NetworkType networkType : values()) {
                if (networkType.getValue() == value)
                    return networkType;
            }

            throw new TypeNotFoundException(NetworkType.class.getName()
                    + " Error creating instance for NetworkType: " + value);
        }

        public int getValue() {
            return value;
        }

        public byte getByte() {
            return (byte) value;
        }

        public String getDescription() {
            return description;
        }

        public PosDataCode.SecurityCharacteristic getIsoSecurityCharacteristic() {
            return isoSecurityCharacteristic;
        }
    }

    public enum MacingType {
        NO_MACING(0, "No macing"),
        PRIVATE_ALG_MACING(1, "Private algorithm MACing", PosDataCode.SecurityCharacteristic.PRIVATE_ALG_MACING),
        STD_ALG_MACING(2, "Standard algorithm MACing", PosDataCode.SecurityCharacteristic.STD_ALG_MACING),;

        private final int value;
        private final String description;
        private final PosDataCode.SecurityCharacteristic isoSecurityCharacteristic;

        MacingType(int value, String description, PosDataCode.SecurityCharacteristic isoSecurityCharacteristic) {
            this.value = value;
            this.description = description;
            this.isoSecurityCharacteristic = isoSecurityCharacteristic;
        }

        MacingType(int value, String description) {
            this(value, description, null);
        }

        public static MacingType valueOf(int value) {
            for (MacingType macingType : values()) {
                if (macingType.getValue() == value)
                    return macingType;
            }

            throw new TypeNotFoundException(MacingType.class.getName()
                    + " Error creating instance for MacingType: " + value);
        }

        public int getValue() {
            return value;
        }

        public byte getByte() {
            return (byte) value;
        }

        public String getDescription() {
            return description;
        }

        public PosDataCode.SecurityCharacteristic getIsoSecurityCharacteristic() {
            return isoSecurityCharacteristic;
        }
    }

    public enum EncryptionType {
        NO_ENCRYPTION(0, "No encryption"),
        PKI_ENCRYPTION(1, "PKI encryption", PosDataCode.SecurityCharacteristic.PKI_ENCRYPTION),
        PRIVATE_ALG_ENCRYPTION(2, "Private algorithm encryption", PosDataCode.SecurityCharacteristic.PRIVATE_ALG_ENCRYPTION),;

        private final int value;
        private final String description;
        private final PosDataCode.SecurityCharacteristic isoSecurityCharacteristic;

        EncryptionType(int value, String description, PosDataCode.SecurityCharacteristic isoSecurityCharacteristic) {
            this.value = value;
            this.description = description;
            this.isoSecurityCharacteristic = isoSecurityCharacteristic;
        }

        EncryptionType(int value, String description) {
            this(value, description, null);
        }

        public static EncryptionType valueOf(int value) {
            for (EncryptionType encryptionType : values()) {
                if (encryptionType.getValue() == value)
                    return encryptionType;
            }

            throw new TypeNotFoundException(EncryptionType.class.getName()
                    + " Error creating instance for EncryptionType: " + value);
        }

        public int getValue() {
            return value;
        }

        public byte getByte() {
            return (byte) value;
        }

        public String getDescription() {
            return description;
        }

        public PosDataCode.SecurityCharacteristic getIsoSecurityCharacteristic() {
            return isoSecurityCharacteristic;
        }
    }

    public enum EncryptionMethod {
        NO_MACING(0, "No macing"),
        CHANNEL_ENCRYPTION(1, "Channel encryption", PosDataCode.SecurityCharacteristic.CHANNEL_ENCRYPTION),
        END_TO_END_ENCRYPTION(2, "End-to-end encryption", PosDataCode.SecurityCharacteristic.END_TO_END_ENCRYPTION),
        CHANNEL_ENCRYPTION_AND_END_TO_END_ENCRYPTION(3, "Channel encryption and End-to-end encryption"),;

        private final int value;
        private final String description;
        private final PosDataCode.SecurityCharacteristic isoSecurityCharacteristic;

        EncryptionMethod(int value, String description, PosDataCode.SecurityCharacteristic isoSecurityCharacteristic) {
            this.value = value;
            this.description = description;
            this.isoSecurityCharacteristic = isoSecurityCharacteristic;
        }

        EncryptionMethod(int value, String description) {
            this(value, description, null);
        }

        public static EncryptionMethod valueOf(int value) {
            for (EncryptionMethod encryptionMethod : values()) {
                if (encryptionMethod.getValue() == value)
                    return encryptionMethod;
            }

            throw new TypeNotFoundException(EncryptionMethod.class.getName()
                    + " Error creating instance for EncryptionMethod: " + value);
        }

        public int getValue() {
            return value;
        }

        public byte getByte() {
            return (byte) value;
        }

        public String getDescription() {
            return description;
        }

        public PosDataCode.SecurityCharacteristic getIsoSecurityCharacteristic() {
            return isoSecurityCharacteristic;
        }
    }

    private SecurityCharacteristic(int networkType, int macingType, int encryptionType, int encryptionMethod) {
        // b[0] = (byte) networkType;
        // b[1] = (byte) macingType;
        // b[2] = (byte) encryptionType;
        // b[3] = (byte) encryptionMethod;

        this.networkType = NetworkType.valueOf(networkType);
        this.macingType = MacingType.valueOf(macingType);
        this.encryptionType = EncryptionType.valueOf(encryptionType);
        this.encryptionMethod = EncryptionMethod.valueOf(encryptionMethod);
    }

    public SecurityCharacteristic(NetworkType networkType, MacingType macingType, EncryptionType encryptionType, EncryptionMethod encryptionMethod) {
        this(networkType.getValue(), macingType.getValue(), encryptionType.getValue(), encryptionMethod.getValue());
    }

    private SecurityCharacteristic(byte[] b) {
        this(b[0], b[1], b[2], b[3]);
        // this.b = b;
    }

    public byte[] getBytes() {
        return new byte[]{networkType.getByte(), macingType.getByte(), encryptionType.getByte(), encryptionMethod.getByte()};
    }

    public String toString() {
        return super.toString() + "[" + ISOUtil.hexString(getBytes()) + "]";
    }

    public static SecurityCharacteristic valueOf(byte[] b) {
        return new SecurityCharacteristic(b);
    }

    @Override
    public void validate() {

    }

    // todo complete this file
    @Override
    public void validate(DeviceCode deviceCode) {
        boolean b = false;
        if (!b) b = networkType == NetworkType.PRIVATE_NETWORK && deviceCode == DeviceCode.ATM;
        if (!b) b = networkType == NetworkType.OPEN_NETWORK && encryptionType == EncryptionType.PKI_ENCRYPTION && encryptionMethod == EncryptionMethod.CHANNEL_ENCRYPTION && deviceCode == DeviceCode.ATM;

        // if (!b) throw new Exception();
    }
}
