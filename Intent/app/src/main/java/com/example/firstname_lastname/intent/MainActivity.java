package com.example.firstname_lastname.intent;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // ----------------- Open Activity --------------------------

    public void openActivity(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // ---------- Open Map -------------------------------------

    public void openMap(View view) {

        Uri.Builder geoLocationBuilder = new Uri.Builder();
        geoLocationBuilder.scheme("geo");

        Uri geoLocation = geoLocationBuilder.build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    // ---------- Open Camera ------------------------------------

    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void openCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap thumbnail = data.getParcelableExtra("data");

            // Use image here
        }
    }
}

