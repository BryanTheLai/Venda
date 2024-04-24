package com.example.venda.ui.dashboard

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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


@Composable
fun LineChartMonth(pointsData: List<Point>) {
    var xSteps = pointsData.size - 1;
    var ySteps = 10;
    val yMax = pointsData.maxOf { it.y }
    val yMin = pointsData.minOf { it.y }
    //steps = yMax.toInt()
    var xAxisStepSize = 10
    if (pointsData.size < 7 )
        xAxisStepSize = 100
    else if (pointsData.size < 10)
        xAxisStepSize = 80
    else if (pointsData.size < 15)
        xAxisStepSize = 50
    else if (pointsData.size < 20)
        xAxisStepSize = 30
    else
        xAxisStepSize = 20


    val xAxisData = AxisData.Builder()
        .axisStepSize(xAxisStepSize.dp)
        .steps(xSteps)
        .labelData { i -> (i + 1).toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(ySteps)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            // Add yMin to get the negative axis values to the scale
            val yScale = (yMax - yMin)/ySteps
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

}