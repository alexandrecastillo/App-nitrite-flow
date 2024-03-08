package labs.alexandre.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.addRepeatingJob
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import org.dizitart.no2.Document
import org.dizitart.no2.Nitrite
import org.dizitart.no2.NitriteCollection
import org.dizitart.no2.event.ChangeInfo
import org.dizitart.no2.event.ChangeListener
import org.dizitart.no2.event.ChangeType
import org.dizitart.no2.filters.Filters

@ExperimentalCoroutinesApi
class NitriteFlowActivity : AppCompatActivity() {

    companion object {
        private const val CODE_ITEM = "COD_1000"
    }

    private lateinit var nitriteDb: Nitrite
    private lateinit var collection: NitriteCollection

    private var counterinsert = 0
    private var counterupdate = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nitrite_flow)

        nitriteDb = Nitrite.builder()
            .filePath("/test.db")
            .openOrCreate()

        collection = nitriteDb.getCollection("item")

        findViewById<Button>(R.id.btnInsert).setOnClickListener {
            if (counterinsert != 2) {
                if (counterinsert % 2 == 0) {
                    collection.insert(Document.createDocument("code", CODE_ITEM))
                } else {
                    collection.insert(Document.createDocument("code", "CODE_ITEM"))
                }
                counterinsert++
            }
        }

        findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            if (counterupdate % 2 == 0) {
                collection.update(
                    Filters.eq("code", "CODE_ITEM"),
                    Document.createDocument("code", "CODE_ITEM").put("counter", counterupdate)
                )
            } else {
                collection.update(
                    Filters.eq("code", "CODE_ITEM"),
                    Document.createDocument("code", "CODE_ITEM").put("counter", counterupdate)
                )
            }
        }
    }

    private fun observeDocumentByCode(code: String) = callbackFlow<Document?> {
        val documentsFound = collection.find().toList().first()
        if (documentsFound != null) {
            trySend(documentsFound)
        }

        fun ChangeInfo.getItemByCuv(): Document? {
            return getDocumentByCondition { it.get("cuv", String::class.java) == code }
        }

        val changeListener = ChangeListener { changeInfo ->
            when (changeInfo.changeType) {
                ChangeType.DROP -> {
                    trySend(null)
                }
                ChangeType.REMOVE -> {
                    val wasRemoved = changeInfo.getItemByCuv() != null
                    if (wasRemoved) {
                        trySend(null)
                    }
                }
                ChangeType.INSERT, ChangeType.UPDATE -> {
                    val document = changeInfo.getItemByCuv()
                    trySend(document)
                }
                else -> Unit
            }
        }

        collection.register(changeListener)
        awaitClose { collection.deregister(changeListener) }
    }

    private fun ChangeInfo.getDocumentByCondition(predicate: (Document) -> Boolean): Document? {
        return changedItems.find { predicate.invoke(it.document) }?.document
    }

    override fun onResume() {
        super.onResume()
        addRepeatingJob(Lifecycle.State.RESUMED) {
            observeDocumentByCode("CODE").collect {
                Log.d("PEDRO", "- - - - - - - - - - - - ")
                Log.d("PEDRO", "onCreate: $it")
                Log.d("PEDRO", "- - - - - - - - - - - - ")
            }
        }
    }

}