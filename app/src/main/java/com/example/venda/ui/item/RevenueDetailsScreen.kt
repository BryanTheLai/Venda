package com.example.venda.ui.item

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import com.example.venda.data.Revenue
import com.example.venda.ui.AppViewModelProvider
import com.example.venda.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object RevenueDetailsDestination : NavigationDestination {
    override val route = "revenue_details"
    override val titleRes = R.string.revenue_detail_title
    const val revenueIdArg = "revenueId"
    val routeWithArgs = "$route/{$revenueIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RevenueDetailsScreen(
    navigateToEditRevenue: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RevenueDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(RevenueDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditRevenue(uiState.value.revenueDetails.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_revenue_title),
                )
            }
        }, bottomBar = {BottomNavBar(navController = navController)}
        , modifier = modifier
    ) { innerPadding ->
        RevenueDetailsBody(
            revenueDetailsUiState = uiState.value,
            //onSellRevenue = { viewModel.reduceQuantityByOne() },
            onDelete = {
               coroutineScope.launch {
                   viewModel.deleteRevenue()
                   Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show()
                   navigateBack()
               }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun RevenueDetailsBody(
    revenueDetailsUiState: RevenueDetailsUiState,
    //onSellRevenue: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        RevenueDetails(
            revenue = revenueDetailsUiState.revenueDetails.toRevenue(),
            modifier = Modifier.fillMaxWidth()
        )
        /*
        Button(
            onClick = onSellRevenue,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = !revenueDetailsUiState.outOfStock
        ) {
            Text(stringResource(R.string.sell))
        }
        */

        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.delete))
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun RevenueDetails(
    revenue: Revenue, modifier: Modifier = Modifier
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
//            //DELETE LATER
//            RevenueDetailsRow(
//                labelResID = R.string.name,
//                revenueDetail = revenue.id.toString(),
//                modifier = Modifier.padding(
//                    horizontal = dimensionResource(id = R.dimen.padding_medium)
//                )
//            )
//            RevenueDetailsRow(
//                labelResID = R.string.name,
//                revenueDetail = revenue.machineId.toString(),
//                modifier = Modifier.padding(
//                    horizontal = dimensionResource(id = R.dimen.padding_medium)
//                )
//            )
//            // DELETE TILL HERE
            RevenueDetailsRow(
                labelResID = R.string.month_revenue,
                revenueDetail = revenue.formatedPrice(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            RevenueDetailsRow(
                labelResID = R.string.year_month,
                revenueDetail = "${revenue.year}/${revenue.month}",
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )

        }
    }
}

@Composable
private fun RevenueDetailsRow(
    @StringRes labelResID: Int, revenueDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            stringResource(labelResID),
            modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_small))
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = revenueDetail, fontWeight = FontWeight.Bold)
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

