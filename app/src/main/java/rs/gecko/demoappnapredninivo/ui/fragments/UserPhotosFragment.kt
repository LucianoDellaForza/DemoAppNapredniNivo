package rs.gecko.demoappnapredninivo.ui.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_photos.*
import kotlinx.coroutines.*
import rs.gecko.demoappnapredninivo.R
import rs.gecko.demoappnapredninivo.ui.activities.MapActivity
import rs.gecko.demoappnapredninivo.ui.adapters.UserPhotoRecyclerAdapter
import rs.gecko.demoappnapredninivo.ui.models.UserPhoto
import rs.gecko.demoappnapredninivo.ui.viewmodels.UserPhotoViewModel
import rs.gecko.demoappnapredninivo.util.GeoCoderUtil
import rs.gecko.demoappnapredninivo.util.LoadDataCallback
import java.io.IOException

@AndroidEntryPoint
class UserPhotosFragment : Fragment(R.layout.fragment_user_photos) {

    private val TAG = "UserPhotosFragment"

    companion object {
        const val CUSTOM_PHOTO_ID_KEY = "customPhotoIdKey"
        const val CITY_AND_AREA = 2
        lateinit var tmpPhotoForTest: UserPhoto
    }

    private val CAMERA_REQUEST_CODE = 0
    private val GALLERY_REQUEST_CODE = 1

    private val viewModel: UserPhotoViewModel by viewModels()
    lateinit var userPhotoAdapter: UserPhotoRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        subscribeToObservers()

        fab.setOnClickListener {
            showDialog()
        }
    }

    private fun subscribeToObservers() {
        viewModel.getAllUserPhotos().observe(viewLifecycleOwner, Observer {
            userPhotoAdapter.differ.submitList(it)
        })
    }

    private fun setupRecycler() {
        userPhotoAdapter = UserPhotoRecyclerAdapter()
        rvUserPhotos.apply {
            adapter = userPhotoAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        userPhotoAdapter.setOnItemClickListener {
            tmpPhotoForTest = it
            if (it.city == "" && it.area == "") {
                Toast.makeText(activity, "There is no location info", Toast.LENGTH_LONG).show()
            } else {
                startMapActivity(false)

            }

        }
    }

    private fun showDialog() {
        val items = arrayOf<CharSequence>("Camera", "Choose from Gallery", "Cancel")

        val alertDialogBuilder = AlertDialog.Builder(activity).apply {
            setTitle("Add photo")
            setItems(items, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when(items[which]) {
                        "Camera" -> {
                            cameraIntent()
                        }
                        "Choose from Gallery" -> {
                            galleryIntent()
                        }
                        else -> {
                            dialog?.dismiss()
                        }
                    }
                }
            })
            show()
        }
    }

    private fun cameraIntent() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            startActivityForResult(it, CAMERA_REQUEST_CODE);
        }
    }

    private fun galleryIntent() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also {
            startActivityForResult(it, GALLERY_REQUEST_CODE)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val selectedImageUri = data?.data
                    val selectedImageBitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedImageUri)
//                    tmpPhotoForTest = UserPhoto(0, selectedImageBitmap) //lat = 0.0, lng = 0.0

//                    val testPath = getRealPathFromURI(selectedImageUri!!)
//                    Log.d("UserPhotosFragment", "getRealPathFromURI: ${testPath.toString()}")

                    var city = ""
                    var area = ""
                    val latLng = getLocationFromGalleryImage(selectedImageUri)
                    latLng?.let {
                        //find city, area
                        GeoCoderUtil.execute(requireContext(), latLng.latitude, latLng.longitude, object : LoadDataCallback<Pair<String, String>> {
                            override fun onDataLoaded(response: Pair<String,String>) {
                                city = response.first
                                area = response.second
                                Log.d("UserPhotosFragment", "GeoCoderUtil.execute callback: City: $city, Area: $area")
                            }
                            override fun onDataNotAvailable(errorCode: Int, reasonMsg: String) {
                                Log.d("UserPhotosFragment", "GeoCoderUtil.execute callback: Something went wrong!")
                            }
                        })
                    }

                    tmpPhotoForTest = UserPhoto(0, selectedImageBitmap, city, area) //lat = 0.0, lng = 0.0

                    // ne treba lokacija kad je iz galerije
                    //startMapActivity(true)
                    saveUserPhotoToDb(tmpPhotoForTest)
                }
            }
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val photoBitmap = data?.extras?.get("data") as Bitmap
                    tmpPhotoForTest = UserPhoto(0, photoBitmap)

                    startMapActivity(true)
                }
            }
            CITY_AND_AREA -> {
                Toast.makeText(activity, resultCode.toString(), Toast.LENGTH_SHORT).show()
                if(resultCode == Activity.RESULT_OK) {
//                    val latitude = data?.getDoubleExtra("locationLat", 0.0)
//                    val longitude = data?.getDoubleExtra("locationLng", 0.0)
                    val city = data?.getStringExtra("city") ?: ""
                    val area = data?.getStringExtra("area") ?: ""
                    Log.d("UserPhotosFragment", "onActivityResult: City: $city, Area: $area")
//                    Toast.makeText(activity, "locationLat: $latitude, locationLng: $longitude", Toast.LENGTH_SHORT).show()
                    Toast.makeText(activity, "City: $city, Area: $area", Toast.LENGTH_SHORT).show()
                    val userPhoto = UserPhoto(0, photo = tmpPhotoForTest.photo, city = city, area = area)

                    saveUserPhotoToDb(userPhoto)
                }
            }
        }
    }

    private fun startMapActivity(expectingActivityResult: Boolean) {
        val intent = Intent(activity, MapActivity::class.java)
        if (expectingActivityResult) {
//            intent.putExtra(CUSTOM_PHOTO_KEY, customPhoto) // error: data parcel size too big...
            startActivityForResult(intent, CITY_AND_AREA)
        } else {
            startActivity(intent)
        }
    }

    private fun saveUserPhotoToDb(userPhoto: UserPhoto) {
        Log.d(TAG, "saveUserPhotoToDb: Trying to save user photo: ${userPhoto.id}, ${userPhoto.photo}, ${userPhoto.city}, ${userPhoto.area}" )
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.insertUserPhoto(userPhoto)
        }
        Toast.makeText(activity, "Photo inserted in db", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLocationFromGalleryImage(imagePath: Uri?): LatLng? {
        imagePath?.let {
            Log.d("UserPhotoFragment", "getLocationFromGalleryImage: image path: ${imagePath.path}")
//            val exifInterface = ExifInterface(imagePath.path.toString())
//            val lat = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
//            val lng = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
//            if (lat != null && lng != null) {
//                return LatLng(lat.toDouble(), lng.toDouble())
//            }
            try {
                requireContext().contentResolver.openInputStream(imagePath).use { inputStream ->
                    val exif = ExifInterface(inputStream!!)
                    val lat = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
                    val lng = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE)
                    val datetimeTest = exif.getAttribute(ExifInterface.TAG_DATETIME)
                    val orientationTest = exif.getAttribute(ExifInterface.TAG_ORIENTATION)
                    Log.d("UserPhotoFragment", "getLocationFromGalleryImage: lat: $lat, lng: $lng, datetime: $datetimeTest, orientation: $orientationTest")
                    if (lat != null && lng != null) {
                        return@getLocationFromGalleryImage LatLng(lat.toDouble(), lng.toDouble())
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return null
    }

    fun getRealPathFromURI(contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = activity?.contentResolver?.query(
            contentUri,
            proj,  // Which columns to return
            null,  // WHERE clause; which rows to return (all rows)
            null,  // WHERE clause selection arguments (none)
            null
        ) // Order-by clause (ascending by name)
        val column_index: Int = (cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA) ?: cursor?.moveToFirst()) as Int
        return cursor?.getString(column_index)
    }

}