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

object RevenueEditDestination : NavigationDestination {
    override val route = "revenue_edit"
    override val titleRes = R.string.edit_revenue_title
    const val revenueIdArg = "revenueId"
    val routeWithArgs = "$route/{$revenueIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevenueEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RevenueEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(RevenueEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        RevenueEntryBody(
            machineId = viewModel.revenueUiState.revenueDetails.machineId.toInt(),
            revenueUiState = viewModel.revenueUiState,
            onRevenueValueChange = viewModel::updateUiState,
            onSaveClick = {
              coroutineScope.launch {
                  viewModel.updateRevenue()
                  Toast.makeText(context, "Changes saved", Toast.LENGTH_SHORT).show()
                  navigateBack()
              }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
