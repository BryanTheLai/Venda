package com.example.venda.ui.dashboard

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


object DashboardScreenDestination : NavigationDestination {
    override val route = "dashboard"
    override val titleRes = R.string.dashboard_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(DashboardScreenDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        }, bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        Text(modifier = Modifier.padding(innerPadding), text = "Dashboard Screen")
    }
    // Dashboard UI
}