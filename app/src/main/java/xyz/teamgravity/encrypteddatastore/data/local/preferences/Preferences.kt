package xyz.teamgravity.encrypteddatastore.data.local.preferences

import android.content.Context
import androidx.datastore.dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext
import java.io.IOException

class Preferences(
    context: Context,
    serializer: PreferencesSerializer,
) {

    companion object {
        /**
         * Preferences name
         */
        const val PREFS = "prefs"
    }

    private val Context.store by dataStore(fileName = PREFS, serializer = serializer)
    private val store = context.store

    ///////////////////////////////////////////////////////////////////////////
    // UPDATE
    ///////////////////////////////////////////////////////////////////////////

    suspend fun update(value: PreferencesModel) {
        withContext(Dispatchers.IO) {
            store.updateData { value }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GET
    ///////////////////////////////////////////////////////////////////////////

    val data: Flow<PreferencesModel> = store.data
        .catch { emit(handleIOException(it)) }

    ///////////////////////////////////////////////////////////////////////////
    // MISC
    ///////////////////////////////////////////////////////////////////////////

    private fun handleIOException(t: Throwable): PreferencesModel {
        return if (t is IOException) PreferencesModel() else throw t
    }
}