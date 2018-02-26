package com.roundarch.codetest.part3.data.model;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by TAE on 26-Feb-18.
 */

public class IConnectionInterface {

    //code added from square
    private String sendGetRequest(String url) {
        String s = "";

        //creating new connection request
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request;
        Response response;


        //just incase request fails, accept json and return string
        try {
            request = new Request.Builder()
                    .url(url)
                    .header("Accept", "application/json")
                    .get()
                    .build();
            response = okHttpClient.newCall(request).execute();
            s = response.body().string();
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return s;
    }
}
