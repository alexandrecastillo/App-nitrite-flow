package labs.alexandre.app.framework.alarm.receiver.base

import android.content.Context
import android.content.Intent
import labs.alexandre.app.framework.alarm.util.AlarmParam
import labs.alexandre.app.framework.alarm.util.AlarmTopic

abstract class AlarmReceiverAbs {

    @AlarmTopic
    abstract val topic: String

    abstract fun onReceive(context: Context?, intent: Intent?)

    protected fun Intent?.getIdentifier(): Int? {
        val identifier = this?.getIntExtra(AlarmParam.EXTRA_IDENTIFIER, -1)
        return if (identifier == -1) null else identifier
    }

}