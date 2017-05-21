package com.sliziix.ranpicky.ranpicky;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SecondActivity extends AppCompatActivity {
    private String USERNAME_KEY = "username";
    private String FIRSTNAME_KEY = "firstname";
    private String LASTNAME_KEY = "lastname";
    private String EMAIL_KEY = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String data = intent.getStringExtra(MainActivity.AUTH);
        JSONObject dataJson = null;
        try {
            dataJson = new JSONObject(data);
        } catch (JSONException e) { }

        if (dataJson == null) {
            return;
        }

        String username = null;
        String firstName = null;
        String lastName = null;
        String email = null;
        try {
            username = (String) dataJson.get(USERNAME_KEY);
            firstName = (String) dataJson.get(FIRSTNAME_KEY);
            lastName = (String) dataJson.get(LASTNAME_KEY);
            email = (String) dataJson.get(EMAIL_KEY);
        } catch (JSONException e) { }

        TextView tvUsername = (TextView) findViewById(R.id.username2);
        TextView tvFirstName = (TextView) findViewById(R.id.firstName2);
        TextView tvLastName = (TextView) findViewById(R.id.lastName2);
        TextView tvEmail = (TextView) findViewById(R.id.email2);

        tvUsername.setText("Username: " + username);
        tvFirstName.setText("FirstName: " + firstName);
        tvLastName.setText("LastName: " + lastName);
        tvEmail.setText("Email: " + email);
    }

    public void downloadPdf(View v) {
        new DownloadPdf().execute(this);
        File file = new File(getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS), "report.pdf");
        System.out.println(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        System.out.println(Uri.fromFile(file));
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "This device do not have PDF Viewer.", Toast.LENGTH_LONG).show();
        }

    }
}
