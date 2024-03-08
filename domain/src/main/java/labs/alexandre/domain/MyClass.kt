package labs.alexandre.domain

import jdk.nashorn.internal.runtime.PropertyMap.diff
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.ExperimentalTime


class MyClass {

    fun getTime() {
        val currentMoment: Instant = Clock.System.now()

        val localDatetimeInUtc: LocalDateTime = currentMoment.toLocalDateTime(TimeZone.UTC)
        println("LocalDatetimeInUtc: $localDatetimeInUtc")

        println("timeStampUTC: $localDatetimeInUtc")

        val localDateInUtc: LocalDate = localDatetimeInUtc.date
        println("LocalDateInUtc: $localDateInUtc")

        val localDatetimeInSystemZone: LocalDateTime =
            currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
        println("LocalDatetimeInSystemZone: $localDatetimeInSystemZone")

        //println("timeStampSystemZone: ${localDatetimeInSystemZone.timestamp}")

        val localDateInSystemZone: LocalDate = localDatetimeInSystemZone.date
        println("LocalDateInUtc: $localDateInSystemZone")


    }

}

var timeInMillisBoot = Clock.System.nowUTC.timeInMillis

fun test() {
    val timeInMillisReport = Clock.System.nowUTC.timeInMillis

    val diffInMillis = timeInMillisBoot - timeInMillisReport
    saveDiffReportTime(diffInMillis)

}

data class Item(
    val id: String,
    val name: String,
    val diffTimeInMillis: Long,
    val isSynchronized: Boolean,
    var timeInMillis: Long = -1
)

fun upload(items: List<Item>) {
    timeInMillisBoot = Clock.System.nowUTC.timeInMillis

    items.forEach {
        if(!it.isSynchronized) {
            it.timeInMillis = timeInMillisBoot.plus(it.diffTimeInMillis)
        }
    }

}

fun saveDiffReportTime(timeInMillis: Long) {

}


@ExperimentalTime
fun main() {
    //MyClass().getTime()

    onReceive()

}

val Clock.System.nowUTC
    get() = now().toLocalDateTime(TimeZone.UTC)

val LocalDateTime.timeInMillis
    get() = Calendar.getInstance().apply {
        clear()

        set(Calendar.YEAR, year)
        set(Calendar.MONTH, monthNumber - 1)
        set(Calendar.DAY_OF_MONTH, dayOfMonth)
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
    }.timeInMillis

fun onReceive() {
    val dateReferenceBootInMillis = "2021-05-04T10:20:25.000".toLocalDateTime().timeInMillis

    val calendarDateBoot = Calendar.getInstance().apply {
        timeInMillis = dateReferenceBootInMillis
    }
    println("CalendarDateBoot: ${calendarDateBoot.time}")

    val dateSavedReferenceInMillis = "2021-06-04T10:36:30.000".toLocalDateTime().timeInMillis
    val newTimeInMillis = "2021-06-04T10:39:10.000".toLocalDateTime().timeInMillis

    val diffBetweenTwoTimesInMillis: Int = (newTimeInMillis - dateSavedReferenceInMillis).toInt()

    val diffMinutes: Int = diffBetweenTwoTimesInMillis / (60 * 1000) % 60

    println("diffInMinutes: $diffMinutes")

    val calendarLastDateTimeSaved = Calendar.getInstance().apply {
        timeInMillis = dateSavedReferenceInMillis
    }
    println("calendarLastDateTimeSaved: ${calendarLastDateTimeSaved.time}")

    val calendarNewDateTime = Calendar.getInstance().apply {
        timeInMillis = newTimeInMillis
    }
    println("calendarNewDateTime: ${calendarNewDateTime.time}")

    val isNewDateMinorThatTimeSaved = calendarNewDateTime.before(calendarLastDateTimeSaved)

    println("isNewDateMinorThatTimeSaved: $isNewDateMinorThatTimeSaved")

    if (isNewDateMinorThatTimeSaved || diffMinutes > 1) {
        println("update date reference")

        val difference = if(isNewDateMinorThatTimeSaved) -diffBetweenTwoTimesInMillis else +diffBetweenTwoTimesInMillis

        calendarDateBoot.apply {
            add(Calendar.MILLISECOND, difference)
        }

        println("New CalendarDateBoot: ${calendarDateBoot.time}")

    } else {
        println("normal")
    }
}

fun upload() {

}