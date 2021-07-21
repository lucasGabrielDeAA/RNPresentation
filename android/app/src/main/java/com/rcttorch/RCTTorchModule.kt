package com.rcttorch

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.camera2.CameraManager
import android.os.Build
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class RCTTorchModule(reactContext: ReactApplicationContext): ReactContextBaseJavaModule(reactContext) {
  private var reactContext: ReactApplicationContext
  private var isTorchOn: Boolean = false
  private lateinit var camera : Camera

  private val HAS_FLASH_LIGHT_ERROR: String = "HAS_FLASH_LIGHT_ERROR"

  init {
    this.reactContext = reactContext
  }

  override fun getName(): String {
    return "TorchLight"
  }

  @ReactMethod
  fun switchTorch(onOffTorch: Boolean) {
    try {
        var isFlashAvailable : Boolean = this.reactContext.applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)

        if (isFlashAvailable) {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var cameraManager : CameraManager = this.reactContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager

            cameraManager.setTorchMode(cameraManager.cameraIdList[0], onOffTorch)
          } else {
            var cameraParams : Camera.Parameters

            if (!isTorchOn) {
              camera = Camera.open()
              cameraParams = camera.parameters
            }
          }
        }
    } catch (e: Exception) {

    }
  }
}
