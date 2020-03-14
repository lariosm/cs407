package com.example.listit

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_listing_create.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class ListingCreate : AppCompatActivity() {
    private val GALLERY = 1
    private val CAMERA = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listing_create)

        // set onClickListeners for buttons
        gallery_upload.setOnClickListener { choosePhotoFromGallery() }
        camera_upload.setOnClickListener { takePhotoFromCamera() }
        submit.setOnClickListener { Toast.makeText(this, "Pressed the submit button.", Toast.LENGTH_LONG).show() }
    }

    // Checks is permissions have been granted
    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false
        }
        return true
    }

    // Request for permissions
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            200)
        //request code will let us know what was requested; will be useful in onRequestPermissionsResult
    }

    // if we receive permission to use camera, then run takePhotoFromCamera again
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200) {
            takePhotoFromCamera()
        }
    }

    // is ran when we press the gallery button to choose photo from gallery
    private fun choosePhotoFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        // GALLERY is a result code that will tell us we came from the gallery activity
        startActivityForResult(galleryIntent, GALLERY)
    }

    // is ran when we press the camera butotn to choose photo from camera
    private fun takePhotoFromCamera() {
        if (!checkPermission()) {
            requestPermission()
        } else {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            // CAMERA is a result code that will tell us we came from the camera activity
            startActivityForResult(cameraIntent, CAMERA)
        }

    }

    // this gets run after you return from the gallery or camera
    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY) // if we return from the gallery, then set the ImageView
        {
            if (data != null)
            {
                val contentURI = data.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    your_image!!.setImageBitmap(bitmap)
                    your_image.tag = getRealPathFromURI(contentURI!!) //saving file path to tag so we can get it later for firebase
                    Log.d("gallery url", your_image.tag.toString())
                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA) // if we return from the camera, then save the image
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            your_image!!.setImageBitmap(thumbnail)
            your_image.tag = saveImage(thumbnail)
            Log.d("camera url", your_image.tag.toString())
        }
    }

    // Convert the image URI to the direct file system path of the image file
    private fun getRealPathFromURI(contentUri: Uri): String {

        // can post image
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = getContentResolver().query(contentUri,
            proj, // WHERE clause selection arguments (none)
            null, null, null)// Which columns to return
        // WHERE clause; which rows to return (all rows)
        // Order-by clause (ascending by name)
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor!!.moveToFirst()

        return cursor.getString(column_index)
    }

    // saves image to gallery (when given a bitmap)
    private fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString())
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .timeInMillis).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.path),
                arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.absolutePath)

            return f.absolutePath
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }
}