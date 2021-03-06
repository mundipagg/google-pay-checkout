package com.mundipagg.googlepay.wallet.models;
import android.support.constraint.Guideline;
import android.util.Log;
import java.util.UUID;

import com.mundipagg.googlepay.wallet.Constants;
import com.mundipagg.googlepay.wallet.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentToken {

    public String Signature;

    public String ProtocolVersion;

    public PaymentTokenSignedMessage SignedMessage;

    public String BillingName;

    public PaymentToken(String signature, String protocol, PaymentTokenSignedMessage message, String billingName){
        this.Signature = signature;
        this.ProtocolVersion = protocol;
        this.SignedMessage = message;
        this.BillingName = billingName;
    }

    public JSONObject toJObject(){

            JSONObject request = new JSONObject();

            JSONObject customer = new JSONObject();
            JSONObject token = new JSONObject();
            JSONObject googlePay = new JSONObject();
            JSONObject header = new JSONObject();

            try {

                if(BillingName != null) {
                    customer.put("name", BillingName);
                    customer.put("email", StringUtils.deAccent(BillingName.split(" ")[0] + "@email.example.com").toLowerCase());
                }
                else{
                    String guid = UUID.randomUUID().toString().toLowerCase();
                    customer.put("email", guid + "@email.example.com");
                }

                googlePay.put("version", "EC_V1");
                googlePay.put("data", SignedMessage.EncryptedMessage);
                googlePay.put("signature", SignedMessage.Signature);
                googlePay.put("merchant_identifier", Constants.PAYMENT_GATEWAY_TOKENIZATION_ID);
                header.put("ephemeral_public_key", SignedMessage.EphemeralPublicKey);
                header.put("tag", SignedMessage.Tag);
                googlePay.put("header", header);

                token.put("type", "google_pay");
                token.put("google_pay", googlePay);
                token.put("customer",customer);

                request.put("token", token);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return request;
    }
}
