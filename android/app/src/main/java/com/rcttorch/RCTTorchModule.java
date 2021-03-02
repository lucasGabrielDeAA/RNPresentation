package com.rcttorch;

import android.content.pm.PackageManager;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraCharacteristics;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import java.util.Map;
import java.util.HashMap;

public class RCTTorchModule extends ReactContextBaseJavaModule {
  private final ReactApplicationContext reactContext;
  private Boolean isTorchOn = false;
  private Camera camera;
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

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @ReactMethod
  public void hasFlashLight(Promise promise) {
    try {
      // Checking if device has flash on it's camera
      Map<String, Boolean> flashInfo = new HashMap<String, Boolean>();
      CameraManager cameraManager = (CameraManager) this.reactContext.getSystemService(Context.CAMERA_SERVICE);

      for (String id : cameraManager.getCameraIdList()) {
        Integer facing = cameraManager.getCameraCharacteristics(id).get(CameraCharacteristics.LENS_FACING);
        String camera = facing == CameraCharacteristics.LENS_FACING_FRONT ? "Front" : "Back";
        flashInfo.put(camera, cameraManager.getCameraCharacteristics(id).get(CameraCharacteristics.FLASH_INFO_AVAILABLE));
      }

      promise.resolve(flashInfo);
    } catch (Exception e) {
      promise.reject("TorchLightError", e);
    }
  }
}
