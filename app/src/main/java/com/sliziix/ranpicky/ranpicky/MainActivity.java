package com.sliziix.ranpicky.ranpicky;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static String AUTH = "authData";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View v) {
        String data = null;
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        try {
            data = (String) new Restful().execute(username, password).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (data == null) {
            Toast.makeText(MainActivity.this, "Username or Password invalid", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra(AUTH, data);
        startActivity(intent);

        // Get data with thread
//        RestfulRunnable asd = new RestfulRunnable(username, password, new A() {
//            @Override
//            public void finish(String data) {
//                if (data == null) {
//                    Toast.makeText(MainActivity.this, "Username or Password invalid", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                intent.putExtra(AUTH, data);
//                startActivity(intent);
//            }
//        });
//
//        new Thread(asd).start();
    } // <<<<<< be careful this line

//    private interface A {
//        public void finish(String data);
//    }
//
//    private class RestfulRunnable implements Runnable {
//        private String username = null;
//        private String password = null;
//        private A a;
//        private String data = null;
//
//        public RestfulRunnable(String username, String password, A a) {
//            this.username = username;
//            this.password = password;
//            this.a = a;
//        }
//
//        public void run() {
//            try {
//                String tokenJson = postAuth("http://164.115.26.40:8080/oauth/auth", "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}");
//                JSONObject json = new JSONObject(tokenJson);
//                String token = (String) json.get("token");
//                data = getAuth("http://164.115.26.40:8080/oauth/user", token);
//                a.finish(data);
//            }
//            catch (Exception e) { }
//        }
//
//        OkHttpClient client = new OkHttpClient();
//        public final MediaType JSON
//                = MediaType.parse("application/json; charset=utf-8");
//
//        private String getAuth(String url, String token) throws IOException {
//            Request request = new Request.Builder()
//                    .url(url)
//                    .addHeader("Authorization", token)
//                    .build();
//
//            Response response = client.newCall(request).execute();
//            return response.body().string();
//        }
//
//        private String postAuth(String url, String json) throws IOException {
//            RequestBody body = RequestBody.create(JSON, json);
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(body)
//                    .build();
//            Response response = client.newCall(request).execute();
//            return response.body().string();
//        }
//    }

}
