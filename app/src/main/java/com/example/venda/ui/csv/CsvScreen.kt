package com.example.venda.ui.csv

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.venda.BottomNavBar
import com.example.venda.InventoryTopAppBar
import com.example.venda.R
import com.example.venda.data.Machine
import com.example.venda.ui.AppViewModelProvider
import com.example.venda.ui.navigation.NavigationDestination
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object CsvScreenDestination : NavigationDestination {
    override val route = "csv"
    override val titleRes = R.string.csv_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CsvScreen(
    navController: NavController,
    viewModel: CsvViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    val uiState = viewModel.csvUiState.collectAsState()
    val data: List<Machine> = uiState.value.machineList

    val csvString: String = listToCsv(data) // This is the string format for csv file
    val saveFileLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                saveFile(context, csvString, it)
            }
        }
    }
    Scaffold(
        topBar = {
            InventoryTopAppBar(
                title = stringResource(CsvScreenDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        Column (
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
            ) {
            if (data.isEmpty()) {
                Text(
                    text = "No Machines",
                )
            } else {
                Button(
                    onClick = {
                    openFilePicker(saveFileLauncher)
                }) {
                    Text(text = "Get All Machines as CSV")
                }
            }
        }
    }
}

// Function to convert list to CSV (placed outside of composable)
fun listToCsv(machineDetails: List<Machine>): String {
    val stringBuilder = StringBuilder()

    // Add header row with all properties (assuming they are all relevant for CSV)
    stringBuilder.appendLine("id,name,price,capacity,model,year,month,day,location,currentStatus,serialNumber")

    // Add data rows
    machineDetails.forEach { detail ->
        stringBuilder.appendLine(
            "${detail.id},${detail.name},${detail.price}," +
                    "${detail.capacity},${detail.model}," +
                    "${detail.year},${detail.month},${detail.day}," +
                    "${detail.location},${detail.currentStatus},${detail.serialNumber}"
        )
    }
    return stringBuilder.toString()
}

fun openFilePicker(saveFileLauncher: ActivityResultLauncher<Intent>) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val date = LocalDateTime.now().format(formatter)

    val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "text/csv"
        putExtra(Intent.EXTRA_TITLE, "Machines-$date")
    }
    saveFileLauncher.launch(intent)
}

fun saveFile(context: Context, csvString: String, uri: Uri) {
    try {
        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            outputStream.write(csvString.toByteArray())
            Toast.makeText(context, "CSV file saved at: $uri", Toast.LENGTH_LONG).show()
        }
    } catch (e: Exception) {
        Log.e(TAG, "Error saving CSV file", e)
        Toast.makeText(context, "Error saving CSV file", Toast.LENGTH_LONG).show()
    }
}

