package com.aquatic.lucre.utilities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.app.ActivityCompat.startActivityForResult
import com.aquatic.lucre.R

/**
 * Helper function for showing the Android image picker
 */
fun showImagePicker(parent: Activity, id: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_OPEN_DOCUMENT
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    val chooser = Intent.createChooser(intent, R.string.select_entry_image.toString())
    parent.startActivityForResult(chooser, id)
}

/**
 * Helper function for converting a filepath to a Bitmap
 * so the image can be displayed in an image view
 */
fun readImage(context: Context, path: String?): Bitmap? {
    var bitmap: Bitmap? = null
    val uri = Uri.parse(path)
    if (uri != null) {
        try {
            val parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r")
            val fileDescriptor = parcelFileDescriptor?.getFileDescriptor()
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor?.close()
        } catch (e: Exception) {
            error(e)
        }
    }
    return bitmap
}
