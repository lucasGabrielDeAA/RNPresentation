import React from 'react'
import { createStackNavigator } from '@react-navigation/stack'

import { Home } from 'src/screens'

const { Navigator: StackNavigator, Screen: StackScreen } = createStackNavigator()

export default function Routes() {

  return (
    <StackNavigator initialRouteName='Home'>
      <StackScreen name='Home' component={Home} options={{ headerShown: false }} />
    </StackNavigator>
  )
}
