package com.example.venda.ui.home

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.venda.R
import com.example.venda.data.Revenue
import com.example.venda.ui.AppViewModelProvider
import com.example.venda.ui.item.formatedPrice
import com.example.venda.ui.navigation.NavigationDestination


object RevenueDestination : NavigationDestination {
    override val route = "revenue"
    override val titleRes = R.string.revenue_of_machine
}

@Composable
fun RevenueScreen(
    //navigateToRevenueEntry: () -> Unit,
    navigateToRevenueUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RevenueViewModel = viewModel(factory = AppViewModelProvider.Factory),
    //navController: NavController
) {
    val revenueUiState by viewModel.revenueUiState.collectAsState()
    RevenueBody(
        revenueList = revenueUiState.revenueList,
        onRevenueClick = navigateToRevenueUpdate,
        modifier = modifier
            .fillMaxSize()
    )

}

@Composable
private fun RevenueBody(
    revenueList: List<Revenue>, onRevenueClick: (Int) -> Unit, modifier: Modifier = Modifier
) {
    Column(
        //horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.padding_extra_large))
        ) {
            Text(
                text = "Date ( Y/m )",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "Revenue",
                style = MaterialTheme.typography.titleSmall
            )
        }
        if (revenueList.isEmpty()) {
            Text(
                text = "Empty Revenue",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            InventoryListLoop(
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
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onRevenueClick(revenue) }
            )
        }
    }
}

@Composable
private fun InventoryListLoop(
    revenueList: List<Revenue>, onRevenueClick: (Revenue) -> Unit, modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        for (revenue in revenueList) {
            InventoryRevenue(
                revenue = revenue,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
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
                /*
                Text(
                    text = "machineId: " + revenue.machineId.toString(),
                    style = MaterialTheme.typography.titleSmall,
                )*/
                Text(
                    text = revenue.year.toString() + "/" + revenue.month.toString() ,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = revenue.formatedPrice(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

