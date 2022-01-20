package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*
import kotlin.text.format as textFormat

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate : TextView? = null
    private var tvAgeInMins : TextView? = null
    private var tvAgeInHours : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDate)

        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMins = findViewById(R.id.tvAgeInMins)
        tvAgeInHours = findViewById(R.id.tvAgeInHours)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                Toast.makeText(this, "year was $selectedYear, month was ${selectedMonth+1}" +
                        ", day of month was $selectedDayOfMonth", Toast.LENGTH_LONG).show()

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)

                theDate?.let{
                    val selectedDateInMinutes = theDate.time/60000
                    val selectedDateInHours = theDate.time/3600000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let{
                        val currentDateInMinutes = currentDate.time/60000
                        val currentDateInHours = currentDate.time/3600000

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        val differenceInHours = currentDateInHours - selectedDateInHours

                        //"differenceInMinutes" is of type Long, so I have to convert it to a String Object
                        tvAgeInMins?.text = differenceInMinutes.toString().textFormat()
                        tvAgeInHours?.text = differenceInHours.toString()
                    }

                }


            },
            year,
            month,
            day
        )

        //Setting a maximum date, so that the user does not choose a future date or the present day
        dpd.datePicker.maxDate = System.currentTimeMillis() - (3600000 * 24)
        dpd.show()

    }
}






