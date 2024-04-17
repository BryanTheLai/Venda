package com.example.venda.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.venda.BottomNavBar
import com.example.venda.InventoryTopAppBar
import com.example.venda.R
import com.example.venda.data.Revenue
import com.example.venda.ui.AppViewModelProvider
import com.example.venda.ui.item.formatedPrice
import com.example.venda.ui.navigation.NavigationDestination
import com.example.venda.ui.home.RevenueViewModel


object RevenueDestination : NavigationDestination {
    override val route = "revenue"
    override val titleRes = R.string.revenue_of_machine
}

/**
 * Entry route for Revenue screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RevenueScreen(
    navigateToRevenueEntry: () -> Unit,
    navigateToRevenueUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RevenueViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavController
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val revenueUiState by viewModel.revenueUiState.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            InventoryTopAppBar(
                title = stringResource(RevenueDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToRevenueEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Revenue Entry"
                )
            }
        },bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        RevenueBody(
            revenueList = revenueUiState.revenueList,
            onRevenueClick = navigateToRevenueUpdate,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun RevenueBody(
    revenueList: List<Revenue>, onRevenueClick: (Int) -> Unit, modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (revenueList.isEmpty()) {
            Text(
                text = "Something",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            InventoryList(
                revenueList = revenueList,
                onRevenueClick = { onRevenueClick(it.id) },
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun InventoryList(
    revenueList: List<Revenue>, onRevenueClick: (Revenue) -> Unit, modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = revenueList, key = { it.id }) { revenue ->
            InventoryRevenue(
                revenue = revenue,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onRevenueClick(revenue) }
            )
        }
    }
}

    @Composable
    private fun InventoryRevenue(
        revenue: Revenue, modifier: Modifier = Modifier
    ) {
        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "machineId: " + revenue.machineId.toString(),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "date: " + revenue.month.toString() + "/" + revenue.year.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text(
                    text = "revenue: " + revenue.formatedPrice(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }

