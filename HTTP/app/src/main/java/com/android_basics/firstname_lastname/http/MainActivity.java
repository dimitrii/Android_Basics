package com.android_basics.firstname_lastname.http;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    class RetrieveUrlTask extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient();

        protected String doInBackground(String... urls) {
            Request request = new Request.Builder()
                    .url(urls[0])
                    .build();
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            } catch (IOException ex) {
                return ex.getMessage();
            }
        }
        protected void onPostExecute(String response) {
            // Write Text
            TextView mainText = (TextView) findViewById(R.id.mainTextView);
            if (mainText != null && response != null) {
                mainText.setText(response);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RetrieveUrlTask().execute("https://raw.github.com/square/okhttp/master/README.md");
    }
}
