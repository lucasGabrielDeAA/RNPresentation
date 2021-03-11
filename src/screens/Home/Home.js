import React, { useState, useEffect } from 'react'
import { View, Text, StatusBar } from 'react-native'

import { torchlight } from 'src/utils'

import { Container, Label, Button, ButtonLabel } from './styles'

export default function Home() {
  const [onOffTorch, setOnOffTorch] = useState(false)
  const [hasFlashLight, setHasFlashLight] = useState(false)

  const handleTorchLight = () => {
    try {
      torchlight.switchTorch(!onOffTorch)
      setOnOffTorch(!onOffTorch)
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
    <Container dark={onOffTorch}>
      <StatusBar
        backgroundColor={onOffTorch ? '#000' : '#fff'}
        barStyle={onOffTorch ? 'light-content' : 'dark-content'}
      />

      <Label dark={onOffTorch}>
        {hasFlashLight ? 'Flash light available' : 'No flash light available'}
      </Label>

      <Button disabled={!hasFlashLight} dark={onOffTorch} onPress={() => handleTorchLight()}>
        <ButtonLabel dark={onOffTorch}>{onOffTorch ? 'Turn off' : 'Light up'}</ButtonLabel>
      </Button>
    </Container>
  )
}
