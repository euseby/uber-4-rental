package org.eusebiu.dto;

public class PaymentMethod {
    private String type;      // ex: "Visa" sau "Mastercard"
    private String endingIn;  // ex: "4242"
    private String expiry;    // ex: "12/25"
    private boolean isDefault;// ex: true sau false

    public PaymentMethod(String type, String endingIn, String expiry, boolean isDefault) {
        this.type = type;
        this.endingIn = endingIn;
        this.expiry = expiry;
        this.isDefault = isDefault;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEndingIn() {
        return endingIn;
    }

    public void setEndingIn(String endingIn) {
        this.endingIn = endingIn;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}