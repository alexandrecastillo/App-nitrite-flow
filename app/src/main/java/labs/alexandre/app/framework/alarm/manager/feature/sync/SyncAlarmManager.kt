package labs.alexandre.app.framework.alarm.manager.feature.sync

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import labs.alexandre.app.framework.alarm.manager.base.BaseAlarmManager
import labs.alexandre.app.framework.alarm.util.AlarmTopic
import labs.alexandre.app.model.Alarm

class SyncAlarmManager : BaseAlarmManager(), ISyncAlarmManager {

    override val topic: String
        get() = AlarmTopic.SYNC

    override fun schedule(context: Context, alarm: Alarm): Boolean {
        val requestCode = alarm.id
        val triggerAtMillis = alarm.timeInMillis

        val config = Config(
            alarm.id,
            context,
            requestCode,
            triggerAtMillis,
            AlarmManager.RTC,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        schedule(config)

        return true
    }

}