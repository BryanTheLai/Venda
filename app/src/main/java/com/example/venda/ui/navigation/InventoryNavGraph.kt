package com.example.venda.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


import com.example.venda.ui.home.HomeDestination
import com.example.venda.ui.home.HomeScreen
import com.example.venda.ui.item.MachineDetailsDestination
import com.example.venda.ui.item.MachineDetailsScreen
import com.example.venda.ui.item.MachineEditDestination
import com.example.venda.ui.item.MachineEditScreen
import com.example.venda.ui.item.MachineEntryDestination
import com.example.venda.ui.item.MachineEntryScreen

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun InventoryNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToMachineEntry = { navController.navigate(MachineEntryDestination.route) },
                navigateToMachineUpdate = {
                    navController.navigate("${MachineDetailsDestination.route}/${it}")
                }
            )
        }
        composable(route = MachineEntryDestination.route) {
            MachineEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = MachineDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(MachineDetailsDestination.machineIdArg) {
                type = NavType.IntType
            })
        ) {
            MachineDetailsScreen(
                navigateToEditMachine = { navController.navigate("${MachineEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(
            route = MachineEditDestination.routeWithArgs,
            arguments = listOf(navArgument(MachineEditDestination.machineIdArg) {
                type = NavType.IntType
            })
        ) {
            MachineEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
