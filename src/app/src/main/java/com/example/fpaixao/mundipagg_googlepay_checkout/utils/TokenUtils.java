package com.example.fpaixao.mundipagg_googlepay_checkout.utils;

import android.util.Log;

import com.example.fpaixao.mundipagg_googlepay_checkout.models.PaymentToken;
import com.example.fpaixao.mundipagg_googlepay_checkout.models.PaymentTokenSignedMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenUtils {

    public static PaymentToken BuildTokenObject(JSONObject data) throws JSONException {

        JSONObject info = data.getJSONObject("info");

        String billingName = info.getJSONObject("billingAddress").getString("name");

        JSONObject jsonToken = new JSONObject(data.getJSONObject("tokenizationData").getString("token"));
        JSONObject jsonSignedMessage = new JSONObject(jsonToken.getString("signedMessage"));

        PaymentTokenSignedMessage signedMessage = new
                PaymentTokenSignedMessage(
                jsonSignedMessage.getString("ephemeralPublicKey"),
                jsonSignedMessage.getString("encryptedMessage"),
                jsonSignedMessage.getString("tag"),
                jsonToken.getString("signature")
        );

        PaymentToken token = new PaymentToken(
                jsonToken.getString("signature"),
                jsonToken.getString("protocolVersion"),
                signedMessage,
                billingName);

        Log.w("token: ", jsonToken.toString());

        return  token;
    }
}
