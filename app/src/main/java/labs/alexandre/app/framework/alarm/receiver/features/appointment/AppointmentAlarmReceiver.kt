package labs.alexandre.app.framework.alarm.receiver.features.appointment

import android.content.Context
import android.content.Intent
import labs.alexandre.app.RemainderAppointmentActivity
import labs.alexandre.app.framework.alarm.receiver.base.AlarmReceiverAbs
import labs.alexandre.app.framework.alarm.util.AlarmTopic

class AppointmentAlarmReceiver : AlarmReceiverAbs() {

    override val topic: String
        get() = AlarmTopic.APPOINTMENT

    override fun onReceive(context: Context?, intent: Intent?) {
        val contextNotNull = context ?: return
        val identifier = intent.getIdentifier() ?: return

        RemainderAppointmentActivity.launch(contextNotNull, identifier)
    }

}