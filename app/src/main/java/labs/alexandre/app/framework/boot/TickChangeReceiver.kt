package labs.alexandre.app.framework.boot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*
import java.util.concurrent.TimeUnit

class TickChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val lastTimeSaved = 1L //24-10-1997 16:30:20
        val newTime = 1L //16:31:20

        val diffBetweenTwoTimesInMillis = lastTimeSaved - newTime

        val differenceBetweenToTimesInMinutes =
            TimeUnit.MILLISECONDS.toMinutes(diffBetweenTwoTimesInMillis).toInt()

        val calendarLastDateTimeSaved = Calendar.getInstance().apply {
            timeInMillis = lastTimeSaved
        }

        val calendarNewDateTime = Calendar.getInstance().apply {
            timeInMillis = newTime
        }

        val isCalendarLastDateTimeSavedNotBeforeThatNew =
            calendarLastDateTimeSaved.after(calendarNewDateTime)

        if (isCalendarLastDateTimeSavedNotBeforeThatNew && differenceBetweenToTimesInMinutes != 1) {
            updateReferenceBootDate()
        }
    }

    private fun updateReferenceBootDate() {

    }

}