package com.rcttorch;

import android.content.pm.PackageManager;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

// This file extends the ReactContextBaseJavaModule, which means that aour code will be recongnized as React code on JS code level
public class RCTTorchModule extends ReactContextBaseJavaModule {
  private final ReactApplicationContext reactContext;
  private Boolean isTorchOn = false;
  private Camera camera;

  private static final String HAS_FLASH_LIGHT_ERROR = "HAS_FLASH_LIGHT_ERROR";

  public RCTTorchModule(ReactApplicationContext reactContext) {
    super(reactContext);

    this.reactContext = reactContext;
  }

  // Method required to NativeModules's API get our bridge. This is the same name that is used to get our module on JS code level
  @Override
  public String getName() {
    return "TorchLight";
  }

  // Ever method provided from our bridge to be accepted on JS code level need this anotation ahead, to tell that is a React Method properly
  @ReactMethod
  public void switchTorch(Boolean onOffTorch) {
    try {
      Boolean isFlashAvailable = this.reactContext.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

      if (isFlashAvailable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          CameraManager cameraManager = (CameraManager) this.reactContext.getSystemService(Context.CAMERA_SERVICE);

          cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], onOffTorch);
        } else {
          Camera.Parameters cameraParams;

          if (!isTorchOn) {
            camera = Camera.open();
            cameraParams = camera.getParameters();
            cameraParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

            camera.setParameters(cameraParams);
          } else {
            cameraParams = camera.getParameters();
            cameraParams.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

            camera.setParameters(cameraParams);
            camera.stopPreview();
            camera.release();
          }

          isTorchOn = onOffTorch;
        }
      }
    } catch (Exception e ) {
      e.printStackTrace();
    }
  }

  @ReactMethod
  public void hasFlashLight(Promise promise) {
    try {
      Boolean isFlashAvailable = this.reactContext.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

      promise.resolve(isFlashAvailable);
    } catch (Exception e) {
      promise.reject(HAS_FLASH_LIGHT_ERROR, e);
    }
  }
}
