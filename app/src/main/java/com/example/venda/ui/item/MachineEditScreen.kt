package com.example.venda.ui.item

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.venda.InventoryTopAppBar
import com.example.venda.R
import com.example.venda.ui.AppViewModelProvider
import com.example.venda.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object MachineEditDestination : NavigationDestination {
    override val route = "machine_edit"
    override val titleRes = R.string.edit_machine_title
    const val machineIdArg = "machineId"
    val routeWithArgs = "$route/{$machineIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MachineEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MachineEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(MachineEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        MachineEntryBody(
            machineUiState = viewModel.machineUiState,
            onMachineValueChange = viewModel::updateUiState,
            onSaveClick = {
              coroutineScope.launch {
                  viewModel.updateMachine()
                  Toast.makeText(context, "Changes saved", Toast.LENGTH_SHORT).show()
                  navigateBack()
              }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
