package com.dev.common.utils

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dev.common.models.custom.SearchModel
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener

class CommonUtils {

    fun isGPSEnabled(activity: AppCompatActivity): Boolean {
        val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun loadImageUrl(context: Context, url: String?, imageView: ImageView) {

        Glide.with(context).load(url).into(imageView)

    }
    fun isInternetDisabled(activity: AppCompatActivity): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false

        val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.allNetworkInfo
        for (ni in netInfo) {
            if (ni.typeName.equals("WIFI", ignoreCase = true))
                if (ni.isConnected)
                    haveConnectedWifi = true
            if (ni.typeName.equals("MOBILE", ignoreCase = true))
                if (ni.isConnected)
                    haveConnectedMobile = true
        }
        return !haveConnectedWifi && !haveConnectedMobile
    }



    fun <T> loadImage(context: Context, url: T?, imageView: ImageView) {


        Glide.with(context).load("http://calista.co.ke/dawaswift_mock/images/"+url).into(imageView)

    }

    fun <T> simpleSearchDialog(
        context: Context,
        title: String,
        searchHint: String,
        searchModels: ArrayList<SearchModel<T>>, cancelable: Boolean,
        searchDialogListener: SearchDialogListener<T>
    ) {


        var s = SimpleSearchDialogCompat(context, title, searchHint, null, searchModels,
            SearchResultListener<SearchModel<T>> { dialog, item, position ->
                searchDialogListener.onResults(item, position)

                dialog?.dismiss()
            })

        s.setCancelable(cancelable)
        s.setCanceledOnTouchOutside(cancelable)
        s.show()


    }
    fun <T> searchDialog(
        context: Context,
        title: String,
        searchHint: String,
        searchModels: ArrayList<SearchModel<T>>, cancelable: Boolean,
        searchDialogListener: SearchDialogListener<T>
    ) {


        var s = TSearchDialogCompat(context, title, searchHint, null, searchModels,
            SearchResultListener<SearchModel<T>> { dialog, item, position ->
                searchDialogListener.onResults(item, position)

                dialog?.dismiss()
            })

        s.setCancelable(cancelable)
        s.setCanceledOnTouchOutside(cancelable)
        s.show()


    }

    fun uriFromString(value: String?): Uri? {

        return if (value == null) {
            null
        } else Uri.parse(value)
    }


    fun uriToString(uri: Uri?): String? {
        if (uri == null) {
            return ""
        }
        return uri.toString()
    }

    fun encode(imageUri: Uri, context: Context): String? {
//        val input = context.contentResolver.openInputStream(imageUri)
//        val image = BitmapFactory.decodeStream(input , null, null)
//        //encode image to base64 string
//        val baos = ByteArrayOutputStream()
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//        val imageBytes = baos.toByteArray()
//        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return uriToString(uri = imageUri)
    }

    fun decode(imageString: String): Uri? {

        return uriFromString(imageString)
//        try {
//            Log.d("BTI643,",imageString)
//            val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
//            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//
//        }catch (e: Exception){
//
//            return  null
//        }
        //decode base64 string to image

        //imageview.setImageBitmap(decodedImage)
    }

}