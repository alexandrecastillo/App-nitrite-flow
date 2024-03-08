package labs.alexandre.app.framework.alarm.manager.feature.sync

import android.content.Context
import labs.alexandre.app.model.Alarm

interface ISyncAlarmManager {

    fun schedule(context: Context, alarm: Alarm): Boolean

}