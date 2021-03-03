import React, { useState, useEffect } from 'react'
import { View, Text, PermissionsAndroid } from 'react-native'

import { torchlight } from 'src/utils'

import { Container, Label, Button, ButtonLabel } from './styles'

export default function Home() {
  const [onoffTorch, setonoffTorch] = useState(false)
  const [hasFlashLight, setHasFlashLight] = useState(true)

  const handleTorchLight = async () => {
    await torchlight.switchTorch(!onoffTorch)
    setonoffTorch(!onoffTorch)
  }

  useEffect(() => {
    const checkFlashLight = async () => {
      torchlight.hasFlashLight(
        error => console.log(`Error found ${error}`),
        isFlashAvailable => {
          console.log(`Has flash light ${isFlashAvailable}`)
        }
      )
    }

    checkFlashLight()
  }, [])

  return (
    <Container dark={onoffTorch}>
      <Label dark={onoffTorch}>Torch</Label>

      <Button dark={onoffTorch} onPress={() => handleTorchLight()}>
        <ButtonLabel dark={onoffTorch}>{onoffTorch ? 'Turn off' : 'Light up'}</ButtonLabel>
      </Button>
    </Container>
  )
}
