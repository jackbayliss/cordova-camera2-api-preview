package com.jackbayliss.camerapreview2;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;

import com.otaliastudios.cameraview.PictureResult;

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
import android.graphics.Matrix;

import android.os.Handler;
import android.os.HandlerThread;

public class CameraActivity extends Fragment {

    private static final String TAG = CameraActivity.class.getSimpleName();
    private CameraView mCameraView;
    private CameraPreviewListener eventListener;
    private Handler mBackgroundHandler;
    private static int orientationchange;
    public interface CameraPreviewListener {
        void onPictureTaken(String originalPicture);
      }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(getResources().getIdentifier("activity_main", "layout", getActivity().getPackageName()), container, false);
        mCameraView  = view.findViewById(getResources().getIdentifier("camera", "id", getActivity().getPackageName()));

        mCameraView.addCameraListener(new CameraListener() {
            public void onPictureTaken(PictureResult result) { handlePicture(result); }

            public void onCameraOpened(CameraOptions options) {
                mCameraView.setPlaySounds(false);
                mCameraView.setZoom(0.7f);
            }

            public void onOrientationChanged(int orientation) {
                orientationchange = orientation;
            }

        });
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
    public void onResume() {
        super.onResume();
        mCameraView.open();
    }

    @Override
    public void onPause() {
        mCameraView.close();
        super.onPause();
    }
    @Override
    public void onDestroy() {
    super.onDestroy();
    mCameraView.close();
}
    

    public void handlePicture(PictureResult result){
        
        new Thread() {
            public void run() {
                    byte[] data = result.getData();
                    int rotation = result.getRotation();
                    int compensation = (360 - orientationchange + rotation) % 360;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                    Matrix matrix = new Matrix();
                    matrix.postRotate(compensation);
                    Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    byte[]   a = outputStream.toByteArray();
                    String encodedImage = Base64.encodeToString(a, Base64.NO_WRAP);

           
                    eventListener.onPictureTaken(encodedImage);
                }
         
                    }.start();
                }


                public void takePicture(){
                    mCameraView.takePictureSnapshot();
                    
                }

}


    
    


    

