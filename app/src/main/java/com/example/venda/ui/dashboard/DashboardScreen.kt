package com.example.venda.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.yml.charts.common.model.Point
import com.example.venda.BottomNavBar
import com.example.venda.InventoryTopAppBar
import com.example.venda.R
import com.example.venda.ui.navigation.NavigationDestination
import java.util.Calendar


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
        val calendar: Calendar = Calendar.getInstance()
        val currentYear: Int = calendar.get(Calendar.YEAR)
        val currentMonth: Int = calendar.get(Calendar.MONTH) + 1
        val currentYearRevenue: Double = 1000.02 // Query
        val currentYearMonth: Double = 10.00 // Query
        val currentMachineStatus: List<Pair<String, Int>> = listOf(
            "Operational" to 10,
            "Out of Stock" to 5,
            "Out of Service" to 2
        )// Query
        val currentYearRevenueData: List<Point> =
            listOf(
                Point(1f, 40f), // Point(month in float, revenue of that month in float)
                Point(2f, 50f),
                Point(3f, 120f),
                Point(4f, 760f),
                Point(5f, 80f),
                Point(6f, 80f),
                Point(7f, 152f),
                Point(8f, 100f),
                Point(9f, 70f),
                Point(10f, 30f),
                Point(11f, 10f),
                Point(12f, 0f),
                )
        val currentMonthRevenueData: List<Point> =
            listOf(
                Point(1f, 100f), // Point(day in float, revenue of that day in float)
                Point(2f, 80f),
                Point(3f, 70f),
                Point(4f, 60f),
                Point(5f, 50f),
                Point(6f, 100f),
                Point(7f, 80f),
                Point(8f, 70f),
                Point(9f, 60f),
                Point(10f, 80f),
                Point(11f, 70f),
                Point(12f, 60f),
                Point(13f, 60f),
                Point(14f, 50f),
                Point(15f, 100f),
                Point(16f, 80f),
                Point(17f, 70f),
                Point(18f, 60f),
                Point(19f, 80f),
                Point(20f, 70f),
                Point(21f, 60f),
                Point(22f, 60f),

            )


        Column(
            modifier = Modifier.padding(innerPadding).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Total Revenue for this YEAR and MONTH: Big number Display.
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Total Revenue for:",
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small)),
                        fontSize = 25.sp, // Adjust the font size
                        fontWeight = FontWeight.Medium
                    )
                    // Total Revenue for this YEAR: Big number Display.
                    BigNumberDisplay(
                        title = "Year of $currentYear : $",
                        number = 1000,
                        modifier = Modifier
                    )
                    // Total Revenue for this MONTH: Big number Display.
                    BigNumberDisplay(
                        title = "Month of $currentMonth : $",
                        number = 1000,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                    )
                }

            }

            // Card for Machine Status: Table
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Machine Status",
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small)),
                        fontSize = 25.sp, // Adjust the font size
                        fontWeight = FontWeight.Medium
                    )
                    // Machine Status Current: Table
                    StatusTable()
                }

            }


            // Card for Revenue by Month for This YEAR: Bar Chart
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Revenue for $currentYear",
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small)),
                        fontSize = 20.sp, // Adjust the font size
                        fontWeight = FontWeight.Medium
                    )
                    LineChartYear(currentYearRevenueData)
                }
            }

            // Top 5 performing machines NOW: Bar Chart
            Card (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_medium)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Revenue for this Month",
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small)),
                        fontSize = 20.sp, // Adjust the font size
                        fontWeight = FontWeight.Medium
                    )
                    LineChartMonth(currentMonthRevenueData)
                }
            }



        }
    }
    // Dashboard UI
}
@Composable
fun BigNumberDisplay(title: String, number: Int, modifier: Modifier = Modifier) {
    Text(
        text = "$title $number",
        modifier = modifier//.fillMaxWidth(), // Adjust the size as needed
        ,fontSize = 20.sp, // Adjust the font size
        //fontWeight = FontWeight.Medium
    )
}

@Composable
fun StatusTable(
    statusData: List<Pair<String, Int>>
    = listOf(
    "Operational" to 10,
    "Out of Stock" to 5,
    "Out of Service" to 2
    )
) {
    Column(
        modifier = Modifier
            //.fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                //.fillMaxWidth()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Status",
                modifier = Modifier.weight(1f),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,

            )
            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                text = "Count",
                modifier = Modifier.weight(1f),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        statusData.forEach { (status, count) ->
            Row(
                modifier = Modifier
                    //.fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = status,
                    modifier = Modifier.weight(1f),
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Text(
                    text = count.toString(),
                    modifier = Modifier.weight(1f),
                    fontSize = 18.sp
                )
            }
        }
    }
}

