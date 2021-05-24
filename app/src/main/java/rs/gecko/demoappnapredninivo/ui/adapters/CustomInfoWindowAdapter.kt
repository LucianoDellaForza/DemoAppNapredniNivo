package rs.gecko.demoappnapredninivo.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import rs.gecko.demoappnapredninivo.R
import rs.gecko.demoappnapredninivo.ui.models.UserPhoto

class CustomInfoWindowAdapter (
    val context: Context,
    val mWindow: View = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null),
    val userPhoto: UserPhoto?) : GoogleMap.InfoWindowAdapter {

    private fun setWindowImage(marker: Marker, view: View) {
        //set image
        val userImageIv = view.findViewById(R.id.userPhotoCustomWindowIv) as ImageView
        if (userPhoto != null) {
            userImageIv.setImageBitmap(userPhoto.photo)
        }
    }

    override fun getInfoWindow(marker: Marker): View? {
        setWindowImage(marker, mWindow)
        return mWindow
    }

    override fun getInfoContents(marker: Marker): View? {
        setWindowImage(marker, mWindow)
        return mWindow
    }
}