package xyz.teamgravity.encrypteddatastore.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.teamgravity.encrypteddatastore.R
import xyz.teamgravity.encrypteddatastore.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewmodel: LoginViewModel = hiltViewModel(),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
    ) {
        TextField(
            value = viewmodel.username,
            onValueChange = viewmodel::onUsernameChange,
            label = { Text(text = stringResource(id = R.string.username)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = viewmodel.password,
            onValueChange = viewmodel::onPasswordChange,
            label = { Text(text = stringResource(id = R.string.password)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = viewmodel::onSavePreferences) {
            Text(text = stringResource(id = R.string.save))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = viewmodel.data.toString(),
            textAlign = TextAlign.Center
        )
    }
}