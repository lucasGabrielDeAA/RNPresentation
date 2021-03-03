import { NativeModules, PermissionsAndroid } from 'react-native'

const { TorchLight } = NativeModules

export default {
  switchTorch(onoffTorch) {
    TorchLight.switchTorch(onoffTorch)
  },

  async hasFlashLight(error, response) {
    await TorchLight.hasFlashLight(error, response)
  }
}
