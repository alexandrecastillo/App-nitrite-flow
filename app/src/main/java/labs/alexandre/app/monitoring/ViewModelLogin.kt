package labs.alexandre.app.monitoring

import labs.alexandre.app.analytics.Analytics

class ViewModelLogin(
    private val analytics: Analytics,
    private val userMonitoring: UserMonitoring,
) {

    fun onClickLogin() {

        userMonitoring.login()

        if(true) {
            userMonitoring.logEvent("LOGIN")
        }

    }

}