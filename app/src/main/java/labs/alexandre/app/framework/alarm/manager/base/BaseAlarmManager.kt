package labs.alexandre.app.framework.alarm.manager.base

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.AlarmManagerCompat
import labs.alexandre.app.framework.alarm.receiver.base.AlarmReceiver
import labs.alexandre.app.framework.alarm.util.AlarmTopic
import labs.alexandre.app.framework.alarm.util.AlarmParam

abstract class BaseAlarmManager {

    private var _intent: Intent? = null
    private var _manager: AlarmManager? = null

    @AlarmTopic
    abstract val topic: String

    private fun getIntent(context: Context): Intent? {
        if (_intent == null) {
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.putExtra(AlarmParam.EXTRA_TOPIC, topic)

            _intent = intent
        }
        return _intent
    }

    private fun getManager(context: Context): AlarmManager? {
        if (_manager == null) {
            val manager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
            if (manager == null) {
                Log.e(TAG, "Alarm Manager is null")
                return null
            }
            _manager = manager
        }
        return _manager
    }

    protected fun schedule(config: Config): Boolean {
        return try {
            val manager = getManager(config.context) ?: return false

            val intent = getIntent(config.context) ?: return false
            intent.putExtra(AlarmParam.EXTRA_IDENTIFIER, config.identifier)

            val pendingIntent =
                PendingIntent.getBroadcast(config.context, config.requestCode, intent, config.flag)

            AlarmManagerCompat.setAndAllowWhileIdle(
                manager,
                config.alarmType,
                config.triggerAtMillis,
                pendingIntent
            )
            true
        } catch (e: Exception) {
            false
        }
    }

    protected fun cancel(context: Context, requestCode: Int): Boolean {
        return try {
            val manager = getManager(context) ?: return false

            val intent = getIntent(context)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )

            manager.cancel(pendingIntent)
            true
        } catch (e: Exception) {
            false
        }

    }

    class Config(
        val identifier: Int,
        val context: Context,
        val requestCode: Int,
        val triggerAtMillis: Long,
        val alarmType: Int,
        val flag: Int
    )

    companion object {
        private const val TAG = "BaseAlarmManager"
    }

}