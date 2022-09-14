package xyz.teamgravity.encrypteddatastore.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.teamgravity.encrypteddatastore.data.local.preferences.Preferences
import xyz.teamgravity.encrypteddatastore.data.local.preferences.PreferencesModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val preferences: Preferences,
) : ViewModel() {

    companion object {
        private const val USERNAME = "username"
        private const val DEFAULT_USERNAME = ""

        private const val PASSWORD = "password"
        private const val DEFAULT_PASSWORD = ""
    }

    var username: String by mutableStateOf(handle.get<String>(USERNAME) ?: DEFAULT_USERNAME)
        private set

    var password: String by mutableStateOf(handle.get<String>(PASSWORD) ?: DEFAULT_PASSWORD)
        private set

    var data: PreferencesModel by mutableStateOf(PreferencesModel())
        private set

    init {
        observe()
    }

    private fun observe() {
        observePreferences()
    }

    private fun observePreferences() {
        viewModelScope.launch {
            preferences.data.collectLatest { data ->
                this@LoginViewModel.data = data
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun onUsernameChange(value: String) {
        username = value
        handle[USERNAME] = value
    }

    fun onPasswordChange(value: String) {
        password = value
        handle[PASSWORD] = value
    }

    fun onSavePreferences() {
        viewModelScope.launch(NonCancellable) {
            preferences.update(
                PreferencesModel(
                    username = username,
                    password = password
                )
            )
        }
    }
}