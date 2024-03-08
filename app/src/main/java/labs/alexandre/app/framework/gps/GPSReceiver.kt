package labs.alexandre.app.framework.gps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.widget.Toast


class GPSReceiver : BroadcastReceiver() {

    companion object {
        var counter = 0
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
            counter++

            val locationManager = getLocationManager(context) ?: return
            val isGPSEnabled = isGPSEnabled(locationManager)

            Toast.makeText(context, "GPS($counter): $isGPSEnabled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getLocationManager(context: Context): LocationManager? {
        return context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
    }

    private fun isGPSEnabled(locationManager: LocationManager): Boolean {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

}