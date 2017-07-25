package com.android_basics.firstname_lastname.http;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

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
//            if (mainText != null && response != null) {
//                mainText.setText(response);
//            }

            String jsonData = response;
            @com.sun.istack.internal.NotNull JSONArray list;
            try {
                list = new JSONArray(jsonData);
            } catch (Exception e) {
                // Do nothing
            } finally {
                for(int i = 0; i < list.length(); i++) {
                    JSONObject obj;
                    try {
                        obj = list.getJSONObject(i);
                    } catch (Exception e) {} finally {
                        mainText.append(obj.getString("public_url"));
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new RetrieveUrlTask().execute("https://storybox-145021.appspot.com/api/audio/featured");
    }
}
