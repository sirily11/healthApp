package com.example.qiweili.healthapp.health

import android.graphics.Color
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

/**
 * Created by qiweili on 2018/3/18.
 */
class GraphView(data : MutableList<Double>,spacing :Double){
    /**
     * Line graph
     */
    var series : LineGraphSeries<DataPoint>? = null

    /**
     * Bar graph
     */
    var series2 : BarGraphSeries<DataPoint>? = null
    init {
        var dataPoints = mutableListOf<DataPoint>()
        var count = 1.0
        for(i in data){
            if(count % spacing != 0.0){
                count++
                continue
            }
            dataPoints.add(DataPoint(count,i))
            count += 1.0
        }
        series = LineGraphSeries(dataPoints.toTypedArray())
        series2 = BarGraphSeries(dataPoints.toTypedArray())
        series?.thickness = 8
        series?.isDrawDataPoints = true


        series2?.color = Color.argb(255, 255, 110, 0)
        series2?.spacing = 50

    }

}