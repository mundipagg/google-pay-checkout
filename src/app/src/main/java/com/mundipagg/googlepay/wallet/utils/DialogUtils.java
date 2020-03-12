package com.mundipagg.googlepay.wallet.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import com.mundipagg.googlepay.wallet.CheckoutActivity;

public class DialogUtils {

    public static void alert(final Context ctx, final String title, final String message){

        ((Activity)ctx).runOnUiThread(new Runnable(){
            @Override
            public void run() {
                new AlertDialog.Builder(ctx)
                        .setTitle(title)
                        .setMessage(
                                message)
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
            }
        });
    }
}
