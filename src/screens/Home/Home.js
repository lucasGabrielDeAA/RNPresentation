import React, { useState, useEffect } from 'react'
import { View, Text, StatusBar } from 'react-native'

import { torchlight } from 'src/utils'

import { Container, Label, Button, ButtonLabel } from './styles'

export default function Home() {
  const [onoffTorch, setonoffTorch] = useState(false)
  const [hasFlashLight, setHasFlashLight] = useState(false)

  const handleTorchLight = async () => {
    try {
      if (hasFlashLight) {
        await torchlight.switchTorch(!onoffTorch)
        setonoffTorch(!onoffTorch)
      }
    } catch (error) {
      console.log(`Error ${error}`)
    }
  }

  useEffect(() => {
    const checkFlashLight = async () => {
      try {
        const isFlashLightAvailable = await torchlight.hasFlashLight()

        setHasFlashLight(isFlashLightAvailable)
      } catch (error) {
        console.log(`Error: ${error}`)
      }
    }

    checkFlashLight()
  }, [])

  return (
    <Container dark={onoffTorch}>
      <StatusBar
        backgroundColor={onoffTorch ? '#000' : '#fff'}
        barStyle={onoffTorch ? 'light-content' : 'dark-content'}
      />

      <Label dark={onoffTorch}>Torch</Label>

      <Button dark={onoffTorch} onPress={() => handleTorchLight()}>
        <ButtonLabel dark={onoffTorch}>{onoffTorch ? 'Turn off' : 'Light up'}</ButtonLabel>
      </Button>
    </Container>
  )
}
