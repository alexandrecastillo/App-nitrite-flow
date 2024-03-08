package labs.alexandre.app.repository

import labs.alexandre.app.model.Appointment

interface CitaRepository {

    suspend fun getCitaById(id: Int): Appointment

}