package com.jackbayliss.camerapreview2;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
import android.content.Intent;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.ViewParent;
import android.view.ViewGroup;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.FrameLayout;

public class CameraPreview extends CordovaPlugin implements CameraActivity.CameraPreviewListener {
    
    private ViewParent webViewParent;
    private CallbackContext callback = null;
    private String appResourcesPackage;
    private CameraActivity fragment;
    private CallbackContext takePictureCallbackContext;
    private int containerViewId = 20;
    
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
            callbackContext.success("Camera started");
            return true;
        }else if(action.equals("takePhoto")){
            return takePicture(callbackContext);
        }else if(action.equals("stopCamera")){
            return stopCamera(callbackContext);
        }
        return false;
    }
    private Intent intent;
    private Intent notificationIntent;
    private void openNewActivity(String name,Context context) {
        fragment = new CameraActivity();
        fragment.setEventListener(this);
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                


        FrameLayout containerView = (FrameLayout)cordova.getActivity().findViewById(containerViewId);
        containerView = new FrameLayout(cordova.getActivity().getApplicationContext());
        containerView.setId(containerViewId);
        FrameLayout.LayoutParams containerLayoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        cordova.getActivity().addContentView(containerView, containerLayoutParams);
        ((ViewGroup)webView.getView().getParent().getParent()).setBackgroundColor(0x00000000);	
        webViewParent = webView.getView().getParent().getParent();
        ((ViewGroup)webView.getView().getParent().getParent()).bringToFront();
        FragmentManager fragmentManager = cordova.getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerView.getId(), fragment);
        fragmentTransaction.commit();
    }
});


}

private boolean takePicture(CallbackContext callbackContext) {

    takePictureCallbackContext = callbackContext;
    fragment.takePicture();

    return true;
  }


  public void onPictureTaken(String originalPicture) {

    JSONArray data = new JSONArray();
    data.put(originalPicture);

    PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, data);
    pluginResult.setKeepCallback(true);
    takePictureCallbackContext.sendPluginResult(pluginResult);
  }


  private boolean stopCamera(CallbackContext callbackContext) {
    fragment.onDestroy();
    if(webViewParent != null) {
      cordova.getActivity().runOnUiThread(new Runnable() {
        @Override
        public void run() {
          ((ViewGroup)webView.getView()).bringToFront();
          webViewParent = null;
        }
      });
    }
    
    ((ViewGroup)webView.getView().getParent().getParent()).setBackgroundColor(0xffffffff);	

    if(fragment!=null){
      FragmentManager fragmentManager = cordova.getActivity().getFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      fragmentTransaction.remove(fragment);
      fragmentTransaction.commit();
      fragment = null;

    }

    callbackContext.success();
    return true;
  }


}
