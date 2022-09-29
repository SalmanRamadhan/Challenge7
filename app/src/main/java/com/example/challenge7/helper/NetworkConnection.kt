package com.example.challenge7.helper

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData

class NetworkConnection(private val context: Context) : LiveData<Boolean>() {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private fun connectifyManagerCallback() {
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                postValue(true)
            }
            override fun onLost(network: android.net.Network) {
                postValue(false)
            }
        }
    }

    override fun onActive() {
        super.onActive()
        connectifyManagerCallback()
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        updateConnection()
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private fun updateConnection() {
        val activeNetwork = connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnected == true)
    }
}