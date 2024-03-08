package labs.alexandre.app.framework.datetime

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.*

class DateTimeChangedReceiver : BroadcastReceiver() {

    private val bootDateSaved: Date
        get() = Date() //24-10-2021 09:20:20

    private val lastDateSaved: Date
        get() = Date() //24-10-2021 13:30:25

    private val differenceTimeBetweenBootAndLast: Int
        get() = 1000

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_TIME_CHANGED || intent?.action == Intent.ACTION_DATE_CHANGED) {
            val newDate = Date()
            val newBootDate = 0 // newDate - differenceTimeBetweenBootAndLast

            save(newBootDate)

            Log.d("ALEXANDRE", "DateChangedReceiver::onReceive:: currDate: $ - newDate: $newDate")
        }
    }

    fun save(newDate: Int) { }

    private fun saveReport() {
        val currentDate = Date()
        val differenceDate = bootDateSaved // - currentDate


    }

}