package labs.alexandre.app.framework.alarm.worker

import android.content.Context
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import labs.alexandre.app.repository.AlarmRepository

class AlarmWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val alarmRepository: AlarmRepository,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val alarms = withContext(Dispatchers.IO) {
                alarmRepository.getAll()
            }
            //alarmManager.scheduleAll(applicationContext, alarms)
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "doWork: ${e.message}")
            Result.failure()
        }
    }

    companion object {
        private val TAG = AlarmWorker::class.java.simpleName

        fun launch(context: Context) {
            val setAlarmsRequest: WorkRequest =
                OneTimeWorkRequestBuilder<AlarmWorker>()
                    .build()

            WorkManager.getInstance(context).enqueue(setAlarmsRequest)
        }
    }

}