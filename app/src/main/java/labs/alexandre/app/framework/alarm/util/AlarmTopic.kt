package labs.alexandre.app.framework.alarm.util

import androidx.annotation.StringDef

@Retention(AnnotationRetention.SOURCE)
@StringDef(
    AlarmTopic.APPOINTMENT,
    AlarmTopic.SYNC
)
annotation class AlarmTopic {

    companion object {
        const val APPOINTMENT = "APPOINTMENT"
        const val SYNC = "SYNC"
    }

}
