package com.example.qiweili.healthapp.health

import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

/**
 * Created by qiweili on 2018/3/18.
 */
class GraphView(data : MutableList<Double>){
    var series : LineGraphSeries<DataPoint>? = null
    init {
        var dataPoints = mutableListOf<DataPoint>()
        var count : Double = 0.0
        for(i in data){
            dataPoints.add(DataPoint(count,i))
            count++
        }
        series = LineGraphSeries(dataPoints.toTypedArray())
    }

}