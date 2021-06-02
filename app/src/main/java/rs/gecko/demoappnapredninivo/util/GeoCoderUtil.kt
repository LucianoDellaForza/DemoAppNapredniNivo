package rs.gecko.demoappnapredninivo.util

import android.content.Context
import android.location.Address
import android.location.Geocoder

object GeoCoderUtil {

    fun execute(
        context: Context,
        latitude: Double,
        longitude: Double,
        callback: LoadDataCallback<Pair<String, String>>    //Pair: city,area
    ) {
        var city = ""
        var area = ""
        try {
            val addresses: MutableList<Address>
            val geocoder = Geocoder(context)    //Locale.ENGLISH
            addresses =
                geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses.isNotEmpty()) {
                val fetchedAddress = addresses[0]
                if (fetchedAddress.maxAddressLineIndex > -1) {
                    fetchedAddress.locality?.let {
                        city = it
                    }
                    fetchedAddress.subLocality?.let {
                        area = it
                    }
                }
                val result = Pair(city, area)
                callback.onDataLoaded(result)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callback.onDataNotAvailable(1, "Something went wrong!")
        }
    }
}

interface LoadDataCallback<T> {
    fun onDataLoaded(response: T) {
    }

    fun onDataNotAvailable(errorCode: Int, reasonMsg: String) {

    }
}