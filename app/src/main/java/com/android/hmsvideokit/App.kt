package com.android.hmsvideokit

import android.app.Application
import android.util.Log
import com.huawei.hms.videokit.player.InitFactoryCallback
import com.huawei.hms.videokit.player.WisePlayerFactory
import com.huawei.hms.videokit.player.WisePlayerFactoryOptions

class App: Application() {

    companion object{
        private const val TAG = "App"
        private var factory: WisePlayerFactory? = null
        fun getWisePlayerFactory(): WisePlayerFactory? {
            return factory
        }
    }

    override fun onCreate() {
        super.onCreate()
        val factoryOptions = WisePlayerFactoryOptions.Builder().setDeviceId("xxx").build()
        WisePlayerFactory.initFactory(this, factoryOptions, object : InitFactoryCallback {
            override fun onSuccess(wisePlayerFactory: WisePlayerFactory) {
                Log.d(TAG, "onSuccess wisePlayerFactory:$wisePlayerFactory")
                factory = wisePlayerFactory
            }

            override fun onFailure(errorCode: Int, msg: String) {
                Log.d(TAG, "onFailure errorcode:$errorCode reason:$msg")
            }
        })
    }


    
}