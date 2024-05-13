package com.example.venda.ui.dashboard

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.extensions.formatToSinglePrecision
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import kotlin.math.floor

/*
@Composable
fun LineChartYear(pointsData: List<Point>) {
    var xSteps = pointsData.size;
    var ySteps = 10;
    val yMax = pointsData.maxOf { it.y }
    val yMin = 0f
    //steps = yMax.toInt()

    val xAxisData = AxisData.Builder()
        .axisStepSize((yMax - yMin).dp)
        .steps(xSteps)
        .labelData { i -> (i + 1).toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(ySteps)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = (yMax ) / ySteps
            ((i * yScale) + yMin).formatToSinglePrecision()
        }.build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = Color.White
    )

    co.yml.charts.ui.linechart.LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
        ,lineChartData = lineChartData
    )
}*/

@Composable
fun LineChartYear(pointsDataVar: List<Point>) {
    val pointToAdd = Point(x = 1f, y = 0f)

    val pointsData = mutableListOf<Point>().apply {
        Log.d("DashboardScreen", "pointsDataVar $pointsDataVar")
        if (none { it.x == pointToAdd.x}) {
            Log.d("DashboardScreen", "if pointsDataVar $pointsDataVar")
            add(pointToAdd)
        }
        Log.d("DashboardScreen", "addAll $pointsDataVar")
        addAll(pointsDataVar)
    }
    Log.d("DashboardScreen", "OUT $pointsData")
    // if pointsData has 2 points with 1.0, remove pointsData[0]
    if (pointsData.size > 1 && pointsData[0].x == pointsData[1].x) {
        Log.d("DashboardScreen", "Removing duplicate point with x=1")
        pointsData.removeAt(0)
    }
    val xSteps = pointsData.size + 2
    val ySteps = 8
    val yMax = pointsData.maxOf { it.y }
    val yMin = pointsData.minOf { it.y }
    //steps = yMax.toInt()
    val xAxisStepSize = if (pointsData.size < 3 )
        150
    else if (pointsData.size < 4)
        130
    else if (pointsData.size < 6)
        110
    else if (pointsData.size < 8)
        90
    else if (pointsData.size < 10)
        70
    else if (pointsData.size < 12)
        40
    else
        30

    val xAxisData = AxisData.Builder()
        .axisStepSize(xAxisStepSize.dp)
        .steps(xSteps)
        .labelData { i -> (i + 1).toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yLabelPadding: Int = if (yMax < 9) {
        20
    }else if (yMax < 999) {
        25
    }else if (yMax < 9999) {
        30
    }else {
        40
    }

    val yAxisData = AxisData.Builder()
        .steps(ySteps)
        .labelAndAxisLinePadding(yLabelPadding.dp)
        .labelData { i ->
            // Add yMin to get the negative axis values to the scale
            val yScale = (yMax - yMin)/ySteps
            floor((i * yScale) + yMin).formatToSinglePrecision()
        }.build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = Color.White
    )

    co.yml.charts.ui.linechart.LineChart(
        modifier = Modifier
            //.fillMaxWidth()
            .height(300.dp).width(500.dp)
        ,lineChartData = lineChartData
    )

}