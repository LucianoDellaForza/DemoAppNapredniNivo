package rs.gecko.demoappnapredninivo.ui.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_user_photos.*
import kotlinx.coroutines.*
import rs.gecko.demoappnapredninivo.R
import rs.gecko.demoappnapredninivo.ui.activities.MapActivity
import rs.gecko.demoappnapredninivo.ui.adapters.UserPhotoRecyclerAdapter
import rs.gecko.demoappnapredninivo.ui.models.UserPhoto
import rs.gecko.demoappnapredninivo.ui.viewmodels.UserPhotoViewModel

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val selectedImageUri = data?.data
                    val selectedImageBitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedImageUri)
                    tmpPhotoForTest = UserPhoto(0, selectedImageBitmap) //lat = 0.0, lng = 0.0

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

}