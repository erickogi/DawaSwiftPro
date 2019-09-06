package com.dev.dawaswiftdriver.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.dev.common.models.custom.Status
import com.dev.common.models.driver.balance.BalanceQuery
import com.dev.common.models.driver.balance.DateRange
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswiftdriver.data.TripsViewModel
import kotlinx.android.synthetic.main.activity_balance.*
import kotlinx.android.synthetic.main.toolback_bar.*
import java.text.SimpleDateFormat
import java.util.*


class BalanceActivity : AppCompatActivity() {
    private lateinit var viewModel: TripsViewModel

    var pair: Pair<Date, Date>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.dev.dawaswiftdriver.R.layout.activity_balance)
        ViewUtils().makeFullScreen(this)
        viewModel = ViewModelProviders.of(this).get(TripsViewModel::class.java)

        linear_back.setOnClickListener {
            onBackPressed()
        }


        pair = CommonUtils().getDateRange()
        edt_dob.setText("From  " + getDisplayDate(pair!!.first) + "\nTo     " + getDisplayDate(pair!!.second) + "")
        viewModel.observeBalance().observe(this, androidx.lifecycle.Observer {
            ViewUtils.setStatus(
                this,
                view,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )

            edt_earnings.setText("")
            edt_distance.setText("")
            edt_trips.setText("")
            if (it.status == Status.SUCCESS) {

                if (it.data != null && it.data!!.data != null) {


                    edt_earnings.setText(CommonUtils().roundOff(it.data?.data?.earnings.toString()).toString())
                    edt_distance.setText(CommonUtils().roundOff(it.data?.data?.distance.toString()).toString())
                    edt_trips.setText(it.data?.data?.trips.toString())
                }


            }
        })
        viewModel.balance(BalanceQuery(DateRange(start = getDate(pair!!.first), end = getDate(pair!!.second))))


        edt_dob.setOnClickListener {


            showDialog()
        }


    }

    private fun getDisplayDate(first: Date): String? {

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        return CommonUtils().getDisplayDate(formatter.format(first))
    }

    private fun getDate(first: Date): String? {

        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        return formatter.format(first)
    }

    private fun showDialog() {
        startActivityForResult(Intent(this@BalanceActivity, com.dev.dawaswiftdriver.views.DateRange::class.java), 100)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        try {
            pair = data?.extras?.get("data") as Pair<Date, Date>
            edt_dob.setText("From  " + getDisplayDate(pair!!.first) + "\nTo     " + getDisplayDate(pair!!.second) + "")

            viewModel.balance(BalanceQuery(DateRange(start = getDate(pair!!.first), end = getDate(pair!!.second))))

        } catch (x: Exception) {
            x.toString()
        }

    }
}
