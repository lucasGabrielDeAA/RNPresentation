//
//  RCTTorchModule.m
//
//
//  Created by Lucas Gabriel de Araújo Assis on 02/03/21.
//

#import <AVFoundation/AVFoundation.h>
#import "RCTTorchModule.h"

@implementation RCTTorchModule

RCT_EXPORT_MODULE(TorchLight)

RCT_EXPORT_METHOD(switchTorch: (BOOL *) onOffTorch) {
    if ([AVCaptureDevice class]) {
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

RCT_EXPORT_METHOD(hasFlashLight: resolver:(RCTPromiseResolveBlock)resolve
                                  rejecter:(RCTPromiseRejectBlock)reject) {
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
        resolve(@[@(isFlashAvailable)]);
    } @catch (NSException *exception) {
        reject(@"HAS_FLASH_LIGHT_ERROR", @"", nil);
    }
}

@end
