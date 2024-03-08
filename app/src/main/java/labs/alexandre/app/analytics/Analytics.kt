package labs.alexandre.app.analytics

interface Analytics {

    fun logEvent(event: String, data: Any? = null)

}