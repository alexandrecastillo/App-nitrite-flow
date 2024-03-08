package labs.alexandre.app.framework.alarm.manager.feature.appointment

import android.content.Context
import labs.alexandre.app.model.Alarm

interface IAppointmentAlarmManager {

    fun schedule(context: Context, alarm: Alarm): Boolean

    fun scheduleAll(context: Context, alarms: List<Alarm>): Boolean

    fun cancel(context: Context, alarm: Alarm): Boolean

}