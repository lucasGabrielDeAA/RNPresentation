import styled from 'styled-components'

export const Container = styled.View`
  align-items: center;
  background-color: #fff;
  flex: 1;
  justify-content: center;
`

export const Label = styled.Text`
  color: #000;
  font-size: 16px;
`
export const Button = styled.TouchableOpacity`
  align-items: center;
  background-color: #000;
  border-radius: 5px;
  elevation: 5;
  justify-content: center;
  margin: 16px;
  padding: 4px 8px;
  shadow-offset: { height: 2px, width: 0 };
  shadow-opacity: 0.2;
`

export const ButtonLabel = styled.Text`
  color: #fff;
  font-size: 16px;
`
