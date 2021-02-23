import React from 'react'
import { View, Text } from 'react-native'

import { Container, Label, Button, ButtonLabel } from './styles'

export default function Home() {

  return (
    <Container>
      <Label>Hello everyone!</Label>

      <Button onPress={() => {}}>
        <ButtonLabel>Click me</ButtonLabel>
      </Button>
    </Container>
  )

}
