package com.roundarch.codetest.part3;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.roundarch.codetest.part3.model.Location;
import com.roundarch.codetest.part3.model.Model;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Part3Service extends Service {

    private final String TAG = this.getClass().getSimpleName();
    private static String URL = "http://gomashup.com/json.php?fds=geo/usa/zipcode/state/IL";



    // TODO - we can use this as the broadcast intent to filter for in our Part3Fragment
    public static final String ACTION_SERVICE_DATA_UPDATED = "com.roundarch.codetest.ACTION_SERVICE_DATA_UPDATED";

    private List<Map<String,String>> data = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO - this interface needs to be implemented to allow consumers
        // TODO - access to the data we plan to download
        return new Part3ServiceBinder();
    }

    public void updateData() {
        // TODO - start the update process for our data
        new AsyncTask<String, Void, ArrayList<Location>>() {

            public void onPostExecute(ArrayList<Location> result)
            {
                broadcastDataUpdated(result);
            }

            @Override
            protected ArrayList<Location> doInBackground(String... params) {
                 String response;
                 Model model;
                 ArrayList<Location> locations = null;
                try
                {
                    response = sendGetRequest(URL);
                    response = response.replace("(", "").replace(")", "");
                    model = parseJSON(response, Model.class);
                    if(model != null) {
                        locations = model.getMyLocations();
                    }
                } catch (Exception e)
                {

                }

                return locations;
            }



        }.execute();
    }

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

    //code added from parseJson online
    public <T> T parseJSON(String json, Class<T> type) {
        T t = null;
        try {
            t = new Gson().fromJson(json, type);
        }
        catch (Exception e)
        {
           // Toast.makeText(getClass().getName(), "", e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
        return t;
    }

    private void broadcastDataUpdated(ArrayList<Location> result) {
        // TODO - send the broadcast
        Intent intent = new Intent();

        //action from part3 service, passing result by serializable and passing to broadcast
        intent.setAction(ACTION_SERVICE_DATA_UPDATED);
        intent.putParcelableArrayListExtra("res", result);

        //sending intent to broadcast receiver
        sendBroadcast(intent);
    }

    public final class Part3ServiceBinder extends Binder {
        // TODO - we need to expose our public IBinder API to clients

        public Part3Service getConnection()
        {
            return Part3Service.this;
        }
    }

    // TODO - eventually we plan to request JSON from the network, so we need
    // TODO - to implement a way to perform that off the main thread.  Then, once we
    // TODO - have the data we can parse it as JSON (using standard Android APIs is fine)
    // TODO - before finally returning to the main thread to store our data on the service.
    // TODO - Keep in mind that the service will keep a local copy and will need an interface
    // TODO - to allow clients to access it.

    // TODO - if you need a simple JSON endpoint, you can obtain the ZIP codes for the state
    // TODO - of Illinois by using this URL:
    //
    // TODO - http://gomashup.com/json.php?fds=geo/usa/zipcode/state/IL

}
