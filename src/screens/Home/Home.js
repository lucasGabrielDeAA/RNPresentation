import React, { useState, useEffect } from 'react'
import { View, Text, StatusBar } from 'react-native'
import styled from 'styled-components'

import { torchlight } from 'src/utils'

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

const Container = styled.View`
  align-items: center;
  background-color: ${({ dark }) => (dark ? '#000' : '#fff')};
  flex: 1;
  justify-content: center;
`

const Label = styled.Text`
  color: ${({ dark }) => (dark ? '#fff' : '#000')};
  font-size: 20px;
`
const Button = styled.TouchableOpacity`
  align-items: center;
  background-color: ${({ dark, disabled }) => (disabled ? '#999' : dark ? '#fff' : '#000')};
  border-radius: 5px;
  elevation: 5;
  justify-content: center;
  margin: 32px;
  padding: 4px 8px;
  shadow-offset: { height: 2px, width: 0 };
  shadow-opacity: 0.2;
`

const ButtonLabel = styled.Text`
  color: ${({ dark }) => (dark ? '#000' : '#fff')};
  font-size: 16px;
`
