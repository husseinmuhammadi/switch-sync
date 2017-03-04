package com.dpi.financial.ftcom.model.type;


public class  SecurityCharacter  {


    private String networkType;
    private String macType;
    private String steganographyType;
    private String steganographyMethod;

    public SecurityCharacter() {
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;

    }

    public String getMacType() {
        return macType;
    }

    public void setMacType(String macType) {
        this.macType = macType;
    }

    public String getSteganographyType() {
        return steganographyType;
    }

    public void setSteganographyType(String steganographyType) {
        this.steganographyType = steganographyType;
    }

    public String getSteganographyMethod() {
        return steganographyMethod;
    }

    public void setSteganographyMethod(String steganographyMethod) {
        this.steganographyMethod = steganographyMethod;
    }



}
