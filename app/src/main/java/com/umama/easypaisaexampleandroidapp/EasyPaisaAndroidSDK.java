package com.umama.easypaisaexampleandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import telenor.com.ep_v1_sdk.config.Easypay;

public class EasyPaisaAndroidSDK extends AppCompatActivity {

    String TAG = "AndroidSDK";
    String emailAddress = "test@test.com";
    String phoneNumber = "phoneNumber";
    Easypay easypay;
    Context context;
    String amount = "5.0";
    String HashKey = "";
    String storeID = "";
    String storeName = "storeName";
    String encryptedValue = "";
    Cipher cipher = null;
    String paymentMethod = "InitialRequest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_paisa_android_sdk);

        context = EasyPaisaAndroidSDK.this;

        purchase(amount);

    }

    private void purchase(String amount){

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime());

        String sampleString = "amount=" + amount +
                "&emailAddress=" + emailAddress +
                "&mobileNum=" + phoneNumber +
                "&orderRefNum=1" +
                "&paymentMethod=" + paymentMethod +
                "&storeId=" + storeID +
                "&timeStamp="+ timeStamp;

        try {

            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(HashKey.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                encryptedValue = Base64.getEncoder().encodeToString(cipher.doFinal(sampleString.getBytes()));
            }

            easypay = new Easypay();

            easypay.configure(context, storeID, storeName,
                    "", "", encryptedValue,
                    "https://easypay.easypaisa.com.pk/easypay-service/rest",
                    false, null);

            easypay.checkout(context, emailAddress,
                    phoneNumber, "1", amount);

        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, e.getMessage());
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, e.getMessage());
        } catch (InvalidKeyException e) {
            Log.e(TAG, e.getMessage());
        } catch (BadPaddingException e) {
            Log.e(TAG, e.getMessage());
        } catch (IllegalBlockSizeException e) {
            Log.e(TAG, e.getMessage());
        }

    }

}