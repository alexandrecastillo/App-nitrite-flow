package labs.alexandre.app.framework.alarm.manager.feature.appointment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import labs.alexandre.app.framework.alarm.manager.base.BaseAlarmManager
import labs.alexandre.app.model.Alarm

class AppointmentAlarmManager : BaseAlarmManager(), IAppointmentAlarmManager {

    override val topic: String
        get() = "AlarmTopic.APPOINTMENT"

    override fun schedule(context: Context, alarm: Alarm): Boolean {
        val requestCode = alarm.id
        val triggerAtMillis = alarm.timeInMillis

        val config = Config(
            alarm.id,
            context,
            requestCode,
            triggerAtMillis,
            AlarmManager.RTC_WAKEUP,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        schedule(config)

        return true
    }

    override fun cancel(context: Context, alarm: Alarm): Boolean {
        cancel(context, alarm.id)
        return true
    }

    override fun scheduleAll(context: Context, alarms: List<Alarm>): Boolean {
        alarms.forEach {
            val result = schedule(context, it)
            if (!result)
                return false
        }
        return true
    }

}