package com.example.android.camera2basic;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import android.content.Intent;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.camerakit.CameraKitView;
/**
 * This class echoes a string called from JavaScript.
 */
public class CameraPreview extends CordovaPlugin {
    private CallbackContext callback = null;
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Context context = cordova.getActivity().getApplicationContext();
        callback = callbackContext;
        if(action.equals("CameraActivity")) {
            String message = args.getString(0);
            this.openNewActivity(message,context);
            return true;
        }
        return false;
    }

    private void openNewActivity(String name,Context context) {
        this.cordova.setActivityResultCallback (this);
        Intent intent = new Intent(context,  CameraActivity.class);
        this.cordova.getActivity().startActivityForResult(intent,99);
    }

}
