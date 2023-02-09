/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React from 'react';
import {
  Button,
  Platform,
  SafeAreaView,
  StatusBar,
  StyleSheet,
  View,
} from 'react-native';
import {joinWifi} from './src/NativeAPI/WiFiModule';

function App(): JSX.Element {
  const connectWifi = () => {
    joinWifi('YourSSID', 'PASSWORD')
      .then(() => {
        console.log('New WiFi config saved');
      })
      .catch(() => {
        console.error('Unknown error');
      });
  };

  return (
    <SafeAreaView style={{backgroundColor: 'white', flex: 1}}>
      <StatusBar barStyle={'dark-content'} />
      <View
        style={{
          flex: 1,
          alignItems: 'center',
          justifyContent: 'center',
        }}>
        <Button title="Press me" onPress={connectWifi} />
      </View>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  buttonStyle: {},
});

export default App;
