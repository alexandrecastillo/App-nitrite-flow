package labs.alexandre.app.framework.boot.features

import android.content.Context
import android.content.Intent
import labs.alexandre.app.framework.alarm.worker.AlarmWorker
import labs.alexandre.app.framework.boot.IBootReceiver

class ScheduleAlarmsBootReceiver: IBootReceiver {

    override fun onReceive(context: Context, intent: Intent?) {
        //AlarmWorker.launch(context)
    }

}