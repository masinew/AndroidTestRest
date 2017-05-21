package com.sliziix.ranpicky.ranpicky;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Ch-AMP on 18/9/2559.
 */
public class Restful extends AsyncTask {
    @Override
    protected String doInBackground(Object[] params) {
        if (params.length <= 0) {
            return null;
        }

        String username = (String) params[0];
        String password = (String) params[1];

        System.out.println(username + " : " + password + "cxzdsaewq");

        try {
            String tokenJson = postAuth("http://164.115.26.40:8080/oauth/auth", "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}");
            JSONObject json = new JSONObject(tokenJson);
            String token = (String) json.get("token");
            String data = getAuth("http://164.115.26.40:8080/oauth/user", token);
            return data;
        }
        catch (Exception e) {
            return null;
        }
    }

    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private String getAuth(String url, String token) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", token)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private String postAuth(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
