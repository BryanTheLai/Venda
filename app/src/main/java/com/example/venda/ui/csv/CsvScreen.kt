package com.example.venda.ui.csv

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.venda.BottomNavBar
import com.example.venda.InventoryTopAppBar
import com.example.venda.R
import com.example.venda.ui.navigation.NavigationDestination


object CsvScreenDestination : NavigationDestination {
    override val route = "csv"
    override val titleRes = R.string.csv_title
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CsvScreen(
    navController: NavController
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(CsvScreenDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        }, bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        Text(modifier = Modifier.padding(innerPadding), text = "CSV Screen")
    }
}