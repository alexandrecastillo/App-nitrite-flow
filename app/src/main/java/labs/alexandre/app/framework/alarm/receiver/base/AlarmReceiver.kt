package labs.alexandre.app.framework.alarm.receiver.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import labs.alexandre.app.framework.alarm.util.AlarmParam

class AlarmReceiver(
    private val alarmsReceiver: List<AlarmReceiverAbs>
): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val topic = getTopic(intent) ?: return

        val alarmReceiver = alarmsReceiver.find { it.topic == topic } ?: return
        alarmReceiver.onReceive(context, intent)
    }

    private fun getTopic(intent: Intent?): String? {
        return intent?.getStringExtra(AlarmParam.EXTRA_TOPIC)
    }

}