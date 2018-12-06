package com.cordova.camerapreview2;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.camerakit.CameraKitView;
import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import java.io.File;
import java.io.FileOutputStream;
import android.os.Environment;

public class CameraActivity extends AppCompatActivity {

    private static final String TAG = CameraActivity.class.getSimpleName();
    private CameraKitView cameraKitView;
    private String appResourcesPackage;
    private Environment Environment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    appResourcesPackage = getApplicationContext().getPackageName();
    super.onCreate(savedInstanceState);
    setContentView(getResources().getIdentifier("activity_main", "layout", appResourcesPackage));
    cameraKitView = findViewById(getResources().getIdentifier("camera", "id", appResourcesPackage));
    }
    @Override
    protected void onStart() {
    super.onStart();
    cameraKitView.onStart();
    }
    @Override
    protected void onResume() {
    super.onResume();
    cameraKitView.onResume();
    }
    @Override
    protected void onPause() {
    cameraKitView.onPause();
    super.onPause();
    }
    @Override
    protected void onStop() {
    cameraKitView.onStop();
    super.onStop();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void TakeImage(){
        cameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, final byte[] capturedImage) {
                File savedPhoto = new File(Environment.getExternalStorageDirectory(), "photo.jpg");
                try {
                    FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                    outputStream.write(capturedImage);
                    outputStream.close();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    }
