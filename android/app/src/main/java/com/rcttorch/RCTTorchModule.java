package com.rcttorch;

import android.content.pm.PackageManager;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;

public class RCTTorchModule extends ReactContextBaseJavaModule {
  private final ReactApplicationContext reactContext;
  private Boolean isTorchOn = false;
  private Camera camera;

  private static final String TORCH_LIGHT_ERROR = "TORCH_LIGHT_ERROR";

  public RCTTorchModule(ReactApplicationContext reactContext) {
    super(reactContext);

    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "TorchLight";
  }

  @ReactMethod
  public void switchTorch(Boolean onoffTorch) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      CameraManager cameraManager = (CameraManager) this.reactContext.getSystemService(Context.CAMERA_SERVICE);

      try {
        cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], onoffTorch);
      } catch (Exception e ) {
        e.printStackTrace();
      }
    } else {
      Camera.Parameters cameraParams;

      if (onoffTorch && !isTorchOn) {
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

      isTorchOn = onoffTorch;
    }
  }

  @ReactMethod
  public void hasFlashLight(Callback errorCallback, Callback successCallback) {
    try {
      WritableMap map = Arguments.createMap();
      Boolean isFlashAvailable = this.reactContext.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

      map.putBoolean("isFlashAvailable", isFlashAvailable);

      successCallback.invoke(isFlashAvailable);
    } catch (Exception e) {
      errorCallback.invoke(e);
    }
  }
}
