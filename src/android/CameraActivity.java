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
import com.camerakit.CameraKit;
import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import android.os.Environment;
import android.app.Fragment;
import android.util.Base64;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap;

public class CameraActivity extends Fragment {

    private static final String TAG = CameraActivity.class.getSimpleName();
    private CameraKitView cameraKitView;
    private CameraPreviewListener eventListener;


    public interface CameraPreviewListener {
        void onPictureTaken(String originalPicture);
      }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(getResources().getIdentifier("activity_main", "layout", getActivity().getPackageName()), container, false);

        cameraKitView = view.findViewById(getResources().getIdentifier("camera", "id", getActivity().getPackageName()));
        cameraKitView.setFocus(CameraKit.FOCUS_OFF);
        cameraKitView.setImageMegaPixels(0.1f);
        cameraKitView.setSensorPreset(CameraKit.SENSOR_PRESET_NONE);
        return view;
    }
    public void setEventListener(CameraPreviewListener listener){
        eventListener = listener;
      }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.v(TAG, "ON CREATE");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
    super.onStart();
    cameraKitView.onStart();
    }
    @Override
    public void onResume() {
    super.onResume();
    cameraKitView.onResume();
    }
    @Override
    public void onPause() {
    cameraKitView.onPause();
    super.onPause();
    }
    @Override
    public void onStop() {
    cameraKitView.onStop();
    super.onStop();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void takePicture(){
        new Thread() {
            public void run() {
            cameraKitView.captureImage(new CameraKitView.ImageCallback() {
                @Override
                public void onImage(CameraKitView cameraKitView,byte[] data) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    data = outputStream.toByteArray();
                    String encodedImage = Base64.encodeToString(data, Base64.NO_WRAP);
    
              eventListener.onPictureTaken(encodedImage);
                }
            });
        }
        }.start();
    }
    

}