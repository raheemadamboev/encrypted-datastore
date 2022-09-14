package xyz.teamgravity.encrypteddatastore.data.local.preferences

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import xyz.teamgravity.encrypteddatastore.core.util.CryptoManager
import java.io.InputStream
import java.io.OutputStream

class PreferencesSerializer(
    private val crypto: CryptoManager,
) : Serializer<PreferencesModel> {

    override val defaultValue: PreferencesModel
        get() = PreferencesModel()

    override suspend fun readFrom(input: InputStream): PreferencesModel {
        return try {
            Json.decodeFromString(
                deserializer = PreferencesModel.serializer(),
                string = crypto.decrypt(input).decodeToString()
            )
        } catch (e: SerializationException) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: PreferencesModel, output: OutputStream) {
        try {
            crypto.encrypt(
                data = Json.encodeToString(
                    serializer = PreferencesModel.serializer(),
                    value = t
                ).encodeToByteArray(),
                output = output
            )
        } catch (e: SerializationException) {
        }
    }
}