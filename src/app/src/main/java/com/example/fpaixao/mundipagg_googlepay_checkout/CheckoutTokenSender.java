package com.example.fpaixao.mundipagg_googlepay_checkout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fpaixao.mundipagg_googlepay_checkout.models.PaymentToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckoutTokenSender {

    static ProgressDialog loadingPayment;

    static String _baseUrl =  "https://postb.in/kaGQ5JKW";
    static String authorization = "";

    Context _context;
    RequestQueue queue;

    public CheckoutTokenSender(Context c){
        this._context = c;
        queue = Volley.newRequestQueue(c);
    }

    public void SendToken(PaymentToken token) {

        Log.w("Sending token to: ", _baseUrl);

        JsonObjectRequest MyStringRequest = new JsonObjectRequest(Request.Method.POST, _baseUrl, token.toJObject(), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                onSendTokenSuccess(response);
            }

        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                onSendTokenError(error);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", authorization);
                return headers;
            }


            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        MyStringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(MyStringRequest);
        loadingPayment = ProgressDialog.show(_context, "Sending token...", "Sending payment token to: " + _baseUrl);
    }

    private void onSendTokenError(VolleyError error) {
        alert("Send Error", error.getMessage());
        loadingPayment.dismiss();
        logError(error);
    }

    private void onSendTokenSuccess(JSONObject response) {
        alert("Send Success", "Message sent to: " + _baseUrl);
        loadingPayment.dismiss();
        Log.w("Payment_Response", response.toString());
    }

    private void alert(String title, String message){
        new AlertDialog.Builder(_context)
                .setTitle(title)
                .setMessage(
                        message)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }

    private void logError(VolleyError error) {

        Log.w("Error: ", error.toString());

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
