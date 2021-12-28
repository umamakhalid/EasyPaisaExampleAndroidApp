package com.umama.easypaisaexampleandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import telenor.com.ep_v1_sdk.config.Easypay;

public class EasyPaisaRestAPIs extends AppCompatActivity {

    String TAG = "RestAPIs";
    WebView mWebView;

    String orderID = "1221";
    String amount = "5.0";
    String storeID = "";
    String hasyKey = "";
    String paymentMethod = "InitialRequest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_paisa_rest_apis);

        mWebView = findViewById(R.id.webview);
        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.getSettings().setDomStorageEnabled(true);

        purchaseRest();

    }

    private void purchaseRest() {

        try {

            String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime());
            String sampleString = "amount="+amount+"&orderRefNum="+orderID+"&paymentMethod="+paymentMethod+"&postBackURL=https://www.google.com/&storeId="+storeID+"&timeStamp="+timeStamp;

            Log.e(TAG, "Umama: sampleString : " +sampleString);

            String encryptedHashRequest = "";

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // google recommended "AES/GCM/NoPadding"
            SecretKeySpec secretKey = new SecretKeySpec(hasyKey.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                encryptedHashRequest = Base64.getEncoder().encodeToString(cipher.doFinal(sampleString.getBytes()));
            }
            Log.e(TAG, "Umama: encryptedHashRequest : " +encryptedHashRequest);

            encryptedHashRequest = URLEncoder.encode(encryptedHashRequest, "UTF-8");


            Log.e(TAG, "Umama: encryptedHashRequest : " +encryptedHashRequest);

            String urll = "https://easypay.easypaisa.com.pk/tpg/?storeId="+storeID+"&orderId="+orderID+"&transactionAmount="+amount+"&mobileAccountNo=&emailAddress=" +
                    "&transactionType="+paymentMethod+"&tokenExpiry=&bankIdentificationNumber=" +
                    "&encryptedHashRequest=" + encryptedHashRequest +
                    "&merchantPaymentMethod=&postBackURL=https%3A%2F%2Fwww.google.com%2F&signature=";

            mWebView.loadUrl(urll);

        }catch (Exception e){Log.e(TAG, e.getMessage());}

    }

}
