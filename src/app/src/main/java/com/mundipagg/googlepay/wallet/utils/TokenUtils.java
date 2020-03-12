package com.mundipagg.googlepay.wallet.utils;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.mundipagg.googlepay.wallet.models.PaymentToken;
import com.mundipagg.googlepay.wallet.models.PaymentTokenSignedMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenUtils {

    public static PaymentToken BuildTokenObject(Context ctx, JSONObject data) throws JSONException {

        try {
            Log.d("build_token", "Start build token");

            JSONObject info = data.getJSONObject("info");
            Log.d("data_test", data.toString());

            String billingName = null;
            if(info.has("billingAddress"))
                billingName = info.getJSONObject("billingAddress").getString("name");

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

            Log.d("build_token", "Finish build token");

            return  token;
        }
        catch (Exception ex){
            DialogUtils.alert(ctx, "Error Building Token", ex.getMessage());
            throw ex;
        }
    }
}
