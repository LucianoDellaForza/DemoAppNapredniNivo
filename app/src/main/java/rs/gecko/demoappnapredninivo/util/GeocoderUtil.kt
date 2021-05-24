package rs.gecko.demoappnapredninivo.util

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.util.*

object GeocoderUtil {

    var address: String = ""
    var cityName: String = ""
    var areaName: String = ""

    fun findCity(context: Context, latLng: LatLng) {
        val geocoder = Geocoder(context, Locale.ENGLISH)

        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        if (addresses.isNotEmpty()) {
            val fetchedAddress = addresses[0]
            if (fetchedAddress.maxAddressLineIndex > -1) {
                address = fetchedAddress.getAddressLine(0)
                fetchedAddress.locality?.let {
                    cityName = it
                }
                fetchedAddress.subLocality?.let {
                    areaName = it
                }
            }
        }
    }
}