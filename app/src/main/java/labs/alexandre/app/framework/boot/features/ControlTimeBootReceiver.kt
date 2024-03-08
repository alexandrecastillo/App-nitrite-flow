package labs.alexandre.app.framework.boot.features

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import labs.alexandre.app.framework.boot.IBootReceiver
import labs.alexandre.app.framework.boot.TickChangeReceiver

class ControlTimeBootReceiver: IBootReceiver {

    override fun onReceive(context: Context, intent: Intent?) {
        val intentFilter = IntentFilter(Intent.ACTION_TIME_TICK)
        context.applicationContext.registerReceiver(TickChangeReceiver(), intentFilter)
    }

}