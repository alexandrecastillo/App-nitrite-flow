package labs.alexandre.app.framework.alarm.worker

import android.app.Notification
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class SyncWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val foregroundInfo = ForegroundInfo(1, Notification())

            val a = setForeground(foregroundInfo)
            

            Log.d("ALEXANDRE", "doWork: SyncWorker started")
            delay(10000)
            Log.d("ALEXANDRE", "doWork: SyncWorker finished")
            Result.success()
        } catch (e: Exception) {
            Log.d("ALEXANDRE", "doWork: e: ${e.message}")
            if(runAttemptCount == 3) {
                Log.d("ALEXANDRE", "doWork: failure")
                Result.failure()
            } else {
                Log.d("ALEXANDRE", "doWork: retry")
                Result.retry()
            }
        }
    }


}