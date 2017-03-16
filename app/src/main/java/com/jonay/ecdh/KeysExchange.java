package com.jonay.ecdh;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

/**
 * Created by jonay on 14/03/17.
 */

public class KeysExchange {

    public static void publicKeyExchange(RequestQueue queue, String url, String publicKey, Context ctx){
        final Context ctx2 = ctx;

        HashMap<String, String> params = new HashMap<>();
        params.put("publickey", publicKey);

        // Request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String serverKey = response.get("serverkey").toString();
                    System.out.println(serverKey);
                    GlobalClass globalData = (GlobalClass) ctx2;

                    //Para probar
                    globalData.setAgreedKey(ECDH.generateAgreedKey(globalData.getPrivateKey(), ECDH.stringToPublicKey(serverKey)));
                    System.out.println("-----> " + globalData.getAgreedKey());

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });
        queue.add(jsonObjectRequest);
    }

}