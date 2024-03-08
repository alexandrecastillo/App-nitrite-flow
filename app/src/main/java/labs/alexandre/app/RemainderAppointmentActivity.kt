package labs.alexandre.app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RemainderAppointmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remainder_appointment)
    }

    companion object {
        private const val EXTRA_ID_APPOINTMENT = "EXTRA_ID_APPOINTMENT"

        fun launch(context: Context, idAppointment: Int) {
            context.startActivity(Intent(context, RemainderAppointmentActivity::class.java).apply {
                putExtra(EXTRA_ID_APPOINTMENT, idAppointment)
            })
        }
    }

}