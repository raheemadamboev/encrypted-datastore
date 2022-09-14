package xyz.teamgravity.encrypteddatastore.data.local.preferences

import kotlinx.serialization.Serializable

@Serializable
data class PreferencesModel(
    val username: String = "",
    val password: String = "",
)
