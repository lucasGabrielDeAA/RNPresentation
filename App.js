import React, { useEffect } from 'react'
import { SafeAreaProvider } from 'react-native-safe-area-context'
import { NavigationContainer } from '@react-navigation/native'

import Routes from './Routes'

export default () => (
  <SafeAreaProvider>
    <NavigationContainer>
      <Routes />
    </NavigationContainer>
  </SafeAreaProvider>
)
