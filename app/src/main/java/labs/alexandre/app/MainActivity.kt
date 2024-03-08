package labs.alexandre.app

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.NotificationCompat
import androidx.work.*
import kotlinx.datetime.*
import labs.alexandre.app.framework.alarm.worker.SyncWorker
import java.util.*
import java.util.TimeZone
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val UNIQUE_NAME_SYNC = "UNIQUE_NAME_SYNC"

    companion object {
        private const val TAG_SYNC = "TAG_SYNC"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getTime()

        val progressBar = findViewById<ProgressBar>(R.id.progress)


        WorkManager.getInstance(applicationContext)
            .getWorkInfosForUniqueWorkLiveData(UNIQUE_NAME_SYNC).observe(this) { worksInfo ->
                val anyWorkIsRunning =
                    worksInfo.any { workInfo -> workInfo.state == WorkInfo.State.RUNNING }
                progressBar.visibility = if (anyWorkIsRunning) View.VISIBLE else View.GONE
            }

        val sharedPreferences = getSharedPreferences("time", Context.MODE_PRIVATE)
        with(sharedPreferences) {
            val bootTime = getString("boot-time", "-")
            val bootTimeZone = getString("boot-timezone", "-")

            val shutdownTime = getString("shutdown-time", "-")
            val shutdownTimeZone = getString("shutdown-timezone", "-")

            findViewById<AppCompatTextView>(R.id.tvwTimes).text =
                "Boot:\n$bootTime\n$bootTimeZone\n\n" +
                        "Shutdown:\n$shutdownTime\n$shutdownTimeZone"
        }

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val calendar = Calendar.getInstance()
            val timeZone = TimeZone.getDefault()

            val timeZoneUTC = TimeZone.getTimeZone("UTC")
            val calendarUTC = Calendar.getInstance(timeZoneUTC)

            findViewById<AppCompatTextView>(R.id.tvwTime).text =
                "Default:${calendar.time}\n${timeZone.id}::${timeZone.displayName}\n\n" +
                        "UTC:${calendarUTC.time}\n${timeZoneUTC.id}::${timeZoneUTC.displayName}"
        }

        findViewById<Button>(R.id.btnWork).setOnClickListener {
            val constraint = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val syncWorkRequest = OneTimeWorkRequest.Builder(SyncWorker::class.java)
                .setConstraints(constraint)
                .setBackoffCriteria(BackoffPolicy.LINEAR, 1L, TimeUnit.MINUTES)
                .build()


            val operation = WorkManager.getInstance(baseContext).enqueue(syncWorkRequest)
            
            val uuidSyncWorkRequest = syncWorkRequest.id.toString()

            WorkManager.getInstance(applicationContext).beginUniqueWork(
                UNIQUE_NAME_SYNC,
                ExistingWorkPolicy.APPEND_OR_REPLACE,
                syncWorkRequest
            )
                .enqueue()

            WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(UUID.fromString("")).observe(this) {

            }
        }
    }

    private fun cancelSync() {
        val uniqueId = "1"

        val workManager = WorkManager.getInstance(applicationContext)
        workManager.getWorkInfoByIdLiveData(UUID.fromString(uniqueId)).observeForever {
            when (it.state) {
                WorkInfo.State.ENQUEUED -> TODO()
                WorkInfo.State.RUNNING -> TODO()
                WorkInfo.State.SUCCEEDED -> TODO()
                WorkInfo.State.FAILED -> TODO()
                WorkInfo.State.BLOCKED -> TODO()
                WorkInfo.State.CANCELLED -> TODO()
            }
        }

        val workQuery = WorkQuery.Builder.fromUniqueWorkNames(listOf()).build()

        val worksInfo = workManager.getWorkInfos(workQuery)
        

        val operation = workManager.cancelWorkById(UUID.fromString(uniqueId))
        val value = operation.state.observeForever {
            when (it) {
                is Operation.State.SUCCESS -> {

                }
                is Operation.State.IN_PROGRESS -> {

                }
                is Operation.State.FAILURE -> {

                }
            }
        }
    }

    fun getTime() {
        val currentMoment: Instant = Clock.System.now()

        val localDatetimeInUtc: LocalDateTime =
            currentMoment.toLocalDateTime(kotlinx.datetime.TimeZone.UTC)
        println("LocalDatetimeInUtc: $localDatetimeInUtc")

        val nanosecondsUTC = localDatetimeInUtc.nanosecond
        println("NanosecondsUTC: $nanosecondsUTC")

        val localDateInUtc: LocalDate = localDatetimeInUtc.date
        println("LocalDateInUtc: $localDateInUtc")

        val localDatetimeInSystemZone: LocalDateTime =
            currentMoment.toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
        println("LocalDatetimeInSystemZone: $localDatetimeInSystemZone")

        val localDateInSystemZone: LocalDate = localDatetimeInSystemZone.date
        println("LocalDateInUtc: $localDateInSystemZone")

        Toast.makeText(applicationContext, "AAAAAA", Toast.LENGTH_SHORT).show()

    }

    fun showNotification() {
        // Get the layouts to use in the custom notification
        val notificationLayout = RemoteViews(packageName, R.layout.notification_walking)

        // Apply the layouts to the notification
        val customNotification = NotificationCompat.Builder(applicationContext, "CHANNEL_ID")
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .build()
    }
}