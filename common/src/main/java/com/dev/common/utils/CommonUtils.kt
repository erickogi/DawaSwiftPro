package com.dev.common.utils

import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dev.common.models.custom.SearchModel
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import java.text.SimpleDateFormat
import java.util.*


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


        Glide.with(context).load(url).placeholder(com.dev.common.R.drawable.placeholder)
            .error(com.dev.common.R.drawable.logo).into(imageView)

    }


    fun displayFormattedDate(actualDate: String, txtDay: TextView, txtMonth: TextView, txtYear: TextView) {
        val sdf = SimpleDateFormat("yyyy-MM-dd")


        val month_date = SimpleDateFormat("MMM ", Locale.ENGLISH)
        val day_date = SimpleDateFormat("dd ", Locale.ENGLISH)
        val year_date = SimpleDateFormat("yyyy", Locale.ENGLISH)


        val date = sdf.parse(actualDate)

        //val month_name = month_date.format(date)


        txtDay.text = day_date.format(date)
        txtMonth.text = month_date.format(date)
        txtYear.text = year_date.format(date)
    }

    fun displayFormattedDate(
        actualDate: String,
        txtDay: TextView,
        txtMonth: TextView,
        txtYear: TextView,
        txtTime: TextView
    ) {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")


        val month_date = SimpleDateFormat("MMM ", Locale.ENGLISH)
        val day_date = SimpleDateFormat("dd ", Locale.ENGLISH)
        val year_date = SimpleDateFormat("yyyy", Locale.ENGLISH)
        val time = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)


        val date = sdf.parse(actualDate)

        //val month_name = month_date.format(date)


        txtDay.text = day_date.format(date)
        txtMonth.text = month_date.format(date)
        txtYear.text = year_date.format(date)
        txtTime.text = time.format(date)
    }

    fun roundOff(number: String?): Double {

        try {

            val number3digits: Double = String.format("%.3f", number?.toDouble()).toDouble()
            val number2digits: Double = String.format("%.2f", number3digits).toDouble()
            val solution: Double = String.format("%.1f", number2digits).toDouble()
            return solution

        } catch (x: Exception) {
            Log.d("roundsfsa", "Error \n" + x.toString())
            return 0.0
        }

    }

    fun roundOffD(number: Double?): String {

        try {

            val number3digits: Double = String.format("%.3f", number).toDouble()
            val number2digits: Double = String.format("%.2f", number3digits).toDouble()
            val solution: String = String.format("%.1f", number2digits)
            return solution

        } catch (x: Exception) {
            Log.d("roundsfsa", "Error \n" + x.toString())
            return ""
        }

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