//
//  RCTTorchModule.m
//
//
//  Created by Lucas Gabriel de Araújo Assis on 02/03/21.
//
// This file provides the implementation of our .h file, and is the declaration of our module, and it methods to be provided to JS code level
#import <AVFoundation/AVFoundation.h>
#import "RCTTorchModule.h"

@implementation RCTTorchModule


// NativeModules bridge name, the same approach used on Java, with the getName method.
// The main difference between this both methods is the non-literal string usage here in obj-c code, like "TorchLight"
RCT_EXPORT_MODULE(TorchLight)

// Methods to be used on JS code level, need this signature on obj-C code level, the same approach when we use @ReactMethod on Java code
RCT_EXPORT_METHOD(switchTorch: (BOOL *) onOffTorch) {
  // Checking if the device has a camera device
    if ([AVCaptureDevice class]) {
      // Checking the flash
        AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];

        if ([device hasTorch]) {
            [device lockForConfiguration:nil];

            if (onOffTorch) {
                [device setTorchMode:AVCaptureTorchModeOn];
            } else {
                [device setTorchMode:AVCaptureTorchModeOff];
            }

            [device unlockForConfiguration];
        }
    }
}

RCT_EXPORT_METHOD(hasFlashLight:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    @try {
        BOOL isFlashAvailable = false;

        if ([AVCaptureDevice class]) {
            AVCaptureDevice *device = [AVCaptureDevice defaultDeviceWithMediaType:AVMediaTypeVideo];

            if ([device hasTorch]) {
                isFlashAvailable = true;
            } else {
                isFlashAvailable = false;
            }
        }

      resolve([NSNumber numberWithBool:isFlashAvailable]);
    } @catch (NSException *exception) {
        reject(@"HAS_FLASH_LIGHT_ERROR", @"", nil);
    }
}

@end
