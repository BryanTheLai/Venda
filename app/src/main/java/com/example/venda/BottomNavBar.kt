package com.example.venda

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.venda.ui.home.HomeDestination
import com.example.venda.ui.home.RevenueDestination


@Composable
fun BottomNavBar(
    navController: NavController
) {
    val items = listOf("Home", "Dashboard", "CSV")
    val homeIcon = painterResource(id = R.drawable.baseline_home_24) // Replace with your own SVG file
    val dashboardIcon = painterResource(id = R.drawable.baseline_insert_chart_24) // Replace with your own SVG file
    val csvIcon = painterResource(id = R.drawable.baseline_insert_drive_file_24) // Replace with your own SVG file

    var selectedItem by rememberSaveable { mutableStateOf(SharedPreferencesHelper.getSelectedItem()) }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    when (index) {
                        0 -> Icon(painter = homeIcon, contentDescription = item)
                        1 -> Icon(painter = dashboardIcon, contentDescription = item)
                        2 -> Icon(painter = csvIcon, contentDescription = item)
                        else -> Icon(painter = homeIcon, contentDescription = item)
                    }
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    SharedPreferencesHelper.setSelectedItem(index)
                    when (index) {
                        0 -> navController.navigate(HomeDestination.route) // Assuming "home" is the route for mainscreen.kt
                        1 -> navController.navigate(RevenueDestination.route) // Assuming "dashboard" is the route for dashboardscreen.kt
                        2 -> navController.navigate("CsvScreen")// Handle CSV selection (optional, based on your navigation setup)
                    }
                },
            )
        }
    }

}