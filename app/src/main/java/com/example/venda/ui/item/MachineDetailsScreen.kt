package com.example.venda.ui.item

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.venda.BottomNavBar
import com.example.venda.InventoryTopAppBar
import com.example.venda.R
import com.example.venda.data.Machine
import com.example.venda.ui.AppViewModelProvider
import com.example.venda.ui.home.RevenueScreen
import com.example.venda.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH)
    return format.format(date)
}

object MachineDetailsDestination : NavigationDestination {
    override val route = "machine_details"
    override val titleRes = R.string.machine_detail_title
    const val machineIdArg = "machineId"
    val routeWithArgs = "$route/{$machineIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MachineDetailsScreen(
    navigateToRevenueUpdate: (Int) -> Unit,
    navigateToRevenueEntry: (Int) -> Unit,
    navigateToEditMachine: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MachineDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(MachineDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },/*
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToRevenueEntry(uiState.value.machineDetails.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_revenue),
                )
            }
        },*/
        bottomBar = {BottomNavBar(navController = navController)}
    ) { innerPadding ->
        MachineDetailsBody(
            machineDetailsUiState = uiState.value,
            onEditMachine = { navigateToEditMachine(uiState.value.machineDetails.id) },
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteMachine()
                    viewModel.deleteRevenue()
                    Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show()
                    navigateBack()
                }
            },
            modifier = modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
            ,navigateToRevenueUpdate = navigateToRevenueUpdate,
            navigateToRevenueEntry = {navigateToRevenueEntry(uiState.value.machineDetails.id)}
            )
    }
}

@Composable
private fun MachineDetailsBody(
    navigateToRevenueEntry: () -> Unit,
    navigateToRevenueUpdate: (Int) -> Unit,
    machineDetailsUiState: MachineDetailsUiState,
    onEditMachine: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        Row (
            horizontalArrangement = Arrangement
                .spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            OutlinedButton(
                onClick = onEditMachine,
                modifier = Modifier.weight(1f),
                shape = MaterialTheme.shapes.small,
            ) {
                //Text(stringResource(R.string.edit_machine_title))
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_machine_title),
                )
            }
            if (deleteConfirmationRequired) {
                DeleteConfirmationDialog(
                    onDeleteConfirm = {
                        deleteConfirmationRequired = false
                        onDelete()
                    },
                    onDeleteCancel = { deleteConfirmationRequired = false },
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                )
            }

            Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)))

            OutlinedButton(
                onClick = { deleteConfirmationRequired = true },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.weight(1f)
            ) {
                //Text(stringResource(R.string.delete_machine))
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_machine),
                )
            }
        }

        MachineDetails(
            machine = machineDetailsUiState.machineDetails.toMachine(),
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            /*
            Text(
                text = stringResource(R.string.revenue_list),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )*/
            Button(
                onClick = navigateToRevenueEntry,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
            )  {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_revenue),
                )
                Text(text = stringResource(R.string.revenue),)
            }
        }

        RevenueScreen(
            navigateToRevenueUpdate = navigateToRevenueUpdate,
            modifier = Modifier
        )
    }
}

@Composable
fun MachineDetails(
    machine: Machine, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.padding_medium)
            )
        ) {
            MachineDetailsRow(
                labelResID = R.string.name,
                machineDetail = machine.name,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )

            MachineDetailsRow(
                labelResID = R.string.machine_capacity,
                machineDetail = machine.capacity.toString(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            MachineDetailsRow(
                labelResID = R.string.price,
                machineDetail = machine.formatedPrice(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            MachineDetailsRow(
                labelResID = R.string.model_name,
                machineDetail = machine.model,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            val installYear = machine.year
            val installMonth = machine.month
            val installDay = machine.day

            MachineDetailsRow(
                labelResID = R.string.date_installed,
                machineDetail = "$installDay/$installMonth/$installYear",
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            MachineDetailsRow(
                labelResID = R.string.location,
                machineDetail = machine.location,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            MachineDetailsRow(
                labelResID = R.string.machine_status,
                machineDetail = machine.currentStatus,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            MachineDetailsRow(
                labelResID = R.string.serial_number,
                machineDetail = machine.serialNumber,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
        }
    }
}

@Composable
private fun MachineDetailsRow(
    @StringRes labelResID: Int, machineDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            stringResource(labelResID),
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small)),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = machineDetail.trim())
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.confirmation)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes))
            }
        })
}

