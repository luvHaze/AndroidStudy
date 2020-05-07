package luv.zoey.edwith_pr

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService

class NetworkStatus {

    companion object {

        fun getNetworkStatus(context: Context): Boolean {

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkStatus =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            val isConnect:Boolean

            isConnect = (networkStatus != null)

            return isConnect
        }
    }
}