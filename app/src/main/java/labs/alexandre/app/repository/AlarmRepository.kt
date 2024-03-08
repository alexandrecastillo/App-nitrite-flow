package labs.alexandre.app.repository

import labs.alexandre.app.model.Alarm

interface AlarmRepository {

    suspend fun getAll(): List<Alarm>

    suspend fun delete(alarm: Alarm): Boolean

    suspend fun update(alarm: Alarm): Boolean

    suspend fun add(alarm: Alarm): Alarm

}