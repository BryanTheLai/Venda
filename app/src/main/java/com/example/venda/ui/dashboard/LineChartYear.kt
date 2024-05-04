package com.example.venda.ui.dashboard

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
fun LineChartYear(pointsData: List<Point>) {
    var xSteps = pointsData.size - 1;
    var ySteps = 10;
    val yMax = pointsData.maxOf { it.y }
    val yMin = pointsData.minOf { it.y }
    //steps = yMax.toInt()
    var xAxisStepSize = 10
    if (pointsData.size < 3 )
        xAxisStepSize = 150
    else if (pointsData.size < 4)
        xAxisStepSize = 130
    else if (pointsData.size < 6)
        xAxisStepSize = 110
    else if (pointsData.size < 8)
        xAxisStepSize = 90
    else if (pointsData.size < 10)
        xAxisStepSize = 70
    else if (pointsData.size < 12)
        xAxisStepSize = 40
    else
        xAxisStepSize = 30

    val xAxisData = AxisData.Builder()
        .axisStepSize(xAxisStepSize.dp)
        .steps(xSteps)
        .labelData { i -> (i + 1).toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()

    var yLabelPadding = 20
    if (yMax < 9) {
        yLabelPadding = 20
    }else if (yMax < 999) {
        yLabelPadding = 25
    }else if (yMax < 9999) {
        yLabelPadding = 30
    }else {
        yLabelPadding = 40
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