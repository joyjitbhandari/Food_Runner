package com.joyjit.foodrunner.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo


class ConnectionManager {

    fun CheckConnectivity(context: Context) : Boolean {
        val ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = ConnectivityManager.activeNetworkInfo
        if (activeNetwork?.isConnected != null) {
            return activeNetwork.isConnected
        }
        else{
            return false
        }
    }
}