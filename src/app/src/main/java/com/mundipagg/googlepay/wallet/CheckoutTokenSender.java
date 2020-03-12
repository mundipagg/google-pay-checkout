package com.mundipagg.googlepay.wallet;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mundipagg.googlepay.wallet.models.PaymentToken;
import com.mundipagg.googlepay.wallet.utils.DialogUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckoutTokenSender {

    static ProgressDialog loadingPayment;
    private final OkHttpClient client = new OkHttpClient();
    static String _baseUrl =  "http://requestbin.fullcontact.com/ennalzkycs5q";

    static Context _context;
    RequestQueue queue;

    public CheckoutTokenSender(Context c){
        this._context = c;
        String txt = ((EditText)((CheckoutActivity)c).findViewById(R.id.txt_sendTokenUrl)).getText().toString();

        if(txt != null || txt != ""){
            _baseUrl = txt;
        }

        queue = Volley.newRequestQueue(c);
    }

    public final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void SendToken(PaymentToken data) {

        RequestBody body = (RequestBody) RequestBody.create(JSON, data.toJObject().toString());
        okhttp3.Request request = new Request.Builder()
                .url(_baseUrl)
                .post(body)
                .build();

        client.newCall(request).enqueue(loginCallback);
        loadingPayment = ProgressDialog.show(_context, "Sending token...", "Sending payment token to: " + _baseUrl);
    }

    static Callback loginCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            loadingPayment.dismiss();
            try {
                Log.i("", "login failed: " + call.execute().code());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            loadingPayment.dismiss();
            DialogUtils.alert(_context, "Token Sent Successfully!", String.format("Token sent to '%s'", _baseUrl));


        }
    };

    private void onSendTokenError(VolleyError error) {
        if(error.networkResponse != null){
            Log.d("onSendTokenError", "StatusCode Response: " + Integer.toString(error.networkResponse.statusCode));
            alert("onSendTokenError", "StatusCode Response: " + Integer.toString(error.networkResponse.statusCode));
        }
        else{
            alert("onSendTokenError", error.getMessage());
        }

        loadingPayment.dismiss();
        logError(error);
    }

    private void onSendTokenSuccess(JSONObject response) {
        alert("Send Success", "Message sent to: " + _baseUrl);
        loadingPayment.dismiss();
        Log.w("Payment_Response", response.toString());
    }

    private void alert(String title, String message) {
        new AlertDialog.Builder(_context)
                .setTitle(title)
                .setMessage(
                        message)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    private void logError(VolleyError error) {

        try {

            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);

            new AlertDialog.Builder(_context)
                    .setTitle("Error")
                    .setMessage(
                            "Payment failed: " + data.getString("message"))
                    .setPositiveButton("OK", null)
                    .create()
                    .show();

        } catch (JSONException e) {
        } catch (Exception errorr) {

            new AlertDialog.Builder(_context)
                    .setTitle("Error")
                    .setMessage(
                            "Payment failed")
                    .setPositiveButton("OK", null)
                    .create()
                    .show();

        }
    }
}