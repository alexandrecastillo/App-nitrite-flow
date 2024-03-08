package labs.alexandre.app.framework.shutdown

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.edit
import java.util.*

class ShutDownReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Toast.makeText(context, "shutdown!", Toast.LENGTH_SHORT).show()

        if(intent?.action == Intent.ACTION_SHUTDOWN) {
            val sharedPreferences = context.getSharedPreferences("time", Context.MODE_PRIVATE)
            sharedPreferences.edit {
                val calendar = Calendar.getInstance()
                putString("shutdown-time", "${calendar.time}").apply()

                val timeZone = TimeZone.getDefault()
                putString("shutdown-timezone", "${timeZone.id}::${timeZone.displayName}").apply()
            }
        }
    }

}