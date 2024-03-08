package labs.alexandre.app

import androidx.work.WorkManager
import androidx.work.impl.WorkManagerImpl

interface SyncManager {

    fun sync(category: Category)

}

enum class Category {
    MONITOR, DATA
}


class SyncManagerWorkManager(
    private val workManager: WorkManagerImpl
): SyncManager {

    override fun sync(category: Category) {

    }

}