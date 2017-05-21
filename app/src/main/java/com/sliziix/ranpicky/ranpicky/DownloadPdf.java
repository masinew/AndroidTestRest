package com.sliziix.ranpicky.ranpicky;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ch-AMP on 18/9/2559.
 */
public class DownloadPdf extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] params) {
        AppCompatActivity secondActivity = (AppCompatActivity) params[0];
        InputStream in = null;
        FileOutputStream out = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://164.115.26.40:8080/oauth/test_pdf");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("Response: " + connection.getResponseMessage());
                return null;
            }

            int fileLength = connection.getContentLength();
            in = connection.getInputStream();
            String root = Environment.getExternalStorageDirectory().toString();
            File file = new File(secondActivity.getExternalFilesDir(
                    Environment.DIRECTORY_DOWNLOADS), "report.pdf");
            out = new FileOutputStream(file);
            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = in.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    in.close();
                    return null;
                }
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                out.write(data, 0, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
