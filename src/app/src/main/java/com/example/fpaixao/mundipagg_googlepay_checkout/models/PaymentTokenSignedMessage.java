package com.example.fpaixao.mundipagg_googlepay_checkout.models;

public class PaymentTokenSignedMessage {

    public String EphemeralPublicKey;

    public String EncryptedMessage;

    public String Tag;

    public String Signature;

    public PaymentTokenSignedMessage(String publicKey, String encryptedMessage, String tag, String signature){
        this.EphemeralPublicKey = publicKey;
        this.EncryptedMessage = encryptedMessage;
        this.Tag = tag;
        this.Signature = signature;

    }
}
