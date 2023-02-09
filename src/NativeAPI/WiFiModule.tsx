import {NativeModules, Platform} from 'react-native';
const {WiFiModule} = NativeModules;

const joinWifi = async (ssid: String, password: String) => {
  return new Promise(async (resolve, reject) => {
    if (Platform.OS === 'android') {
      try {
        const value = await WiFiModule.joinWifi(ssid, password);
        if (value === true) {
          resolve(value);
        } else {
          reject(value);
        }
      } catch (error) {
        reject(error);
      }
    } else {
      reject('Not allowed');
    }
  });
};

export {joinWifi};
