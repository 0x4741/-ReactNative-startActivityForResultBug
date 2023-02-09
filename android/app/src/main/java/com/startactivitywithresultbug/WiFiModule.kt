package com.startactivitywithresultbug

import android.app.Activity
import android.content.Intent
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import com.facebook.react.bridge.ActivityEventListener
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class WiFiModule (reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), ActivityEventListener  {
    override fun getName() = "WiFiModule"
    private var gPromise: Promise? = null

    override fun onActivityResult(currentActivity: Activity?, requestCode: Int, resultcode: Int, intent: Intent?) {
        if (requestCode == 0) {
            Log.d(name, resultcode.toString())
            gPromise?.let { promise ->
                when(resultcode) {
                    Activity.RESULT_OK ->
                        promise.resolve(true)
                    Activity.RESULT_CANCELED ->
                        promise.reject(name, "-1")
                }
            }
        }
        gPromise = null
    }

    override fun onNewIntent(p0: Intent?) {
        TODO("Not yet implemented")
    }


    @RequiresApi(Build.VERSION_CODES.R)
    @ReactMethod
    fun joinWifi(ssid: String, password: String, promise: Promise){
        val currentActivity = currentActivity
        if (Build.VERSION.SDK_INT >= VERSION_CODES.R && currentActivity != null){
            try {
                Log.d(name, "Running")
                gPromise = promise
                val config = WifiNetworkSuggestion.Builder()
                    .setSsid(ssid)
                    .setWpa2Passphrase(password)
                    .build()

                val list = ArrayList<WifiNetworkSuggestion>()
                list.add(config)
                val bundle = Bundle()
                bundle.putParcelableArrayList(Settings.EXTRA_WIFI_NETWORK_LIST, list)
                val intent = Intent(Settings.ACTION_WIFI_ADD_NETWORKS)
                intent.putExtras(bundle)
                currentActivity.startActivityForResult(intent, 0)
            } catch (e: Exception) {
                Log.d(name, e.stackTraceToString())
                promise.reject(name, "false")
                gPromise = null
            }
        }else {
            promise.reject(name, "Not supported")
        }
    }



}