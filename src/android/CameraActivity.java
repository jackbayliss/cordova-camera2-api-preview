package com.example.android.camera2basic;

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
import com.example.android.camera2basic.R;
import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    private static final String TAG = CameraActivity.class.getSimpleName();
    private CameraKitView cameraKitView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_camera);
    cameraKitView = findViewById(R.id.camera);
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
}