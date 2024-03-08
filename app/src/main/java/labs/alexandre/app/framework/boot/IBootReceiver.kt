package labs.alexandre.app.framework.boot

import android.content.Context
import android.content.Intent

interface IBootReceiver {

    fun onReceive(context: Context, intent: Intent?)

}