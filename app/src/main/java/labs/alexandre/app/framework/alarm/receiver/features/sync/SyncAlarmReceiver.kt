package labs.alexandre.app.framework.alarm.receiver.features.sync

import android.content.Context
import android.content.Intent
import labs.alexandre.app.framework.alarm.receiver.base.AlarmReceiverAbs
import labs.alexandre.app.framework.alarm.util.AlarmTopic

class SyncAlarmReceiver: AlarmReceiverAbs() {

    override val topic: String
        get() = AlarmTopic.SYNC

    override fun onReceive(context: Context?, intent: Intent?) {

    }

}