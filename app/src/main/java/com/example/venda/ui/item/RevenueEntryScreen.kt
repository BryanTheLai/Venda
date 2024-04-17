package com.example.venda.ui.item

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.venda.InventoryTopAppBar
import com.example.venda.R
import com.example.venda.ui.AppViewModelProvider
import com.example.venda.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

object RevenueEntryDestination : NavigationDestination {
    override val route = "revenue_entry"
    override val titleRes = R.string.revenue_entry_title
    const val machineIdArg = "machineId"
    val routeWithArgs = "${route}/{$machineIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevenueEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: RevenueEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    //val machineId = viewModel.machineDetails
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(RevenueEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        RevenueEntryBody(
            //machineId = machineId,
            revenueUiState = viewModel.revenueUiState,
            onRevenueValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveRevenue()
                    Toast.makeText(context, "Successfully created", Toast.LENGTH_SHORT).show()
                    navigateBack()
                }
                          },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        )
    }
}

@Composable
fun RevenueEntryBody(
    //machineId: Int,
    revenueUiState: RevenueUiState,
    onRevenueValueChange: (RevenueDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
        ) {
        RevenueInputForm(
            //machineId = machineId,
            revenueDetails = revenueUiState.revenueDetails,
            onValueChange = onRevenueValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = revenueUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.save_action))
        }
    }
}

@Composable
fun RevenueInputForm(
    //machineId: Int,
    revenueDetails: RevenueDetails,
    modifier: Modifier = Modifier,
    onValueChange: (RevenueDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        onValueChange(revenueDetails.copy(machineId = revenueDetails.machineId))

        OutlinedTextField(
            value = revenueDetails.machineId,
            onValueChange = {
                new ->
                onValueChange(revenueDetails.copy(machineId = new))
            },
            label = { Text(stringResource(R.string.revenue_machine_id_req)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            singleLine = true,
        )

        OutlinedTextField(
            value = revenueDetails.revenue, // PRICE
            onValueChange = {newValue ->
                if ( newValue.matches( Regex("^\\d+(?:\\.\\d{0,2})?$") ) ) {
                    onValueChange(revenueDetails.copy(revenue = newValue))
                }},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(stringResource(R.string.revenue_entry)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = revenueDetails.year,
            onValueChange = {newValue ->
                if (newValue.matches(Regex("^\\d*$"))) {
                    onValueChange(revenueDetails.copy(year = newValue))
                }
                            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.revenue_year)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = revenueDetails.month, // Convert month to string
            onValueChange = {newValue ->
                if ( newValue.matches( Regex("^(?:[1-9]|1[0-2])$") ) ) {
                    onValueChange(revenueDetails.copy(month = newValue))
                }
                if (newValue == "" || newValue == "0") {
                    onValueChange(revenueDetails.copy(month = newValue))
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.revenue_month)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )


    }
}
