package com.cordova.camerapreview2;

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


public class CameraPreview extends CordovaPlugin {
    
    private CallbackContext callback = null;
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }
    
  private String appResourcesPackage;
    private CameraActivity cameraKitView;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Context context = cordova.getActivity().getApplicationContext();
        callback = callbackContext;
        if(action.equals("CameraActivity")) {
            String message = args.getString(0);
            this.openNewActivity(message,context);
            callbackContext.success("Camera started");
            return true;
        }else if(action.equals("takePhoto")){
            // Take picture
            callbackContext.success("Take photo");
        }
        return false;
    }
    private Intent intent;

    private void openNewActivity(String name,Context context) {
        
        this.cordova.setActivityResultCallback (this);
        Intent intent = new Intent(context,  CameraActivity.class);
        this.cordova.getActivity().startActivityForResult(intent,99);
    }

}
