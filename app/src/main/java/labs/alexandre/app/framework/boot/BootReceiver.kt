package labs.alexandre.app.framework.boot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import labs.alexandre.app.framework.boot.features.ControlTimeBootReceiver
import labs.alexandre.app.framework.boot.features.ScheduleAlarmsBootReceiver

class BootReceiver : BroadcastReceiver() {

    private val bootReceivers: List<IBootReceiver> = listOf(
        ControlTimeBootReceiver(),
        ScheduleAlarmsBootReceiver()
    )

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            bootReceivers.forEach {
                it.onReceive(context, intent)
            }
        }
    }

}