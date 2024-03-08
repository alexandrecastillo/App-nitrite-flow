package labs.alexandre.app.monitoring

interface UserMonitoring {

    fun login()

    fun logEvent(event: String, data: Any? = null)

}