package com.example.memolistapplication.ui

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*


class DatePickFragment(val listener: DatePickerDialog.OnDateSetListener) : DialogFragment() {
    @Override
     override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            context!!,
            listener,
            year,
            month,
            day
        )
    }
}
class TimePickFragment(val listener: TimePickerDialog.OnTimeSetListener) : DialogFragment() {
    @Override
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar=Calendar.getInstance()
        val hour=calendar.get(Calendar.HOUR_OF_DAY)
        val minute=calendar.get(Calendar.MINUTE)
        return TimePickerDialog(
            context!!,
            listener,
            hour,
            minute,
            true
        )
    }
}