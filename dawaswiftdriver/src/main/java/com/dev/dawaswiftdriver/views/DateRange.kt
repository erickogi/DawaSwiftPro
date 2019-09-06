package com.dev.dawaswiftdriver.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.dev.common.utils.CommonUtils
import com.dev.dawaswiftdriver.R
import com.savvi.rangedatepicker.CalendarPickerView
import java.util.*

class DateRange : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_range)


        var pair: Pair<Date, Date> = CommonUtils().getDateRange()

        val calendar_view = findViewById<CalendarPickerView>(R.id.calendar_view)
        // body.text = title
        val yesBtn = findViewById<Button>(R.id.yesBtn)
        // val noBtn = dialog .findViewById(R.id.noBtn) as TextView

        var nextYear: Calendar = Calendar.getInstance()
        nextYear.add(Calendar.YEAR, 10)

        var lastYear: Calendar = Calendar.getInstance()
        lastYear.add(Calendar.YEAR, -10)

        calendar_view.init(lastYear.time, nextYear.time) //
            .inMode(CalendarPickerView.SelectionMode.RANGE)
        // .withSelectedDate(Date())
        // deactivates given dates, non selectable
        /// .withDeactivateDates(list)
        // highlight dates in red color, mean they are aleady used.
        // .withHighlightedDates(arrayList)

        calendar_view.scrollToDate(Date())

        yesBtn.setOnClickListener {


            if (calendar_view.selectedDates != null && calendar_view.selectedDates.size > 1) {
                pair = Pair(
                    calendar_view.selectedDates[0],
                    calendar_view.selectedDates[calendar_view.selectedDates.size - 1]
                )


                val data = Intent()
                data.putExtra("data", pair)
                setResult(RESULT_OK, data)
                finish()

            }
        }
    }
}
