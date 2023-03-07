package com.example.demo;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

import java.util.function.Function;

public class MyGraph {
    //private XYChart<Double, Double> graph;
    //private double range;

    //public MyGraph( final double range) {
        //this.graph = series;
      //  this.range = range;
    //}

    public static XYChart.Series<Double, Double> PlotLine (Model model, double range) {
        model.rebuild_ex();
        //model.half_done = true;
        final XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
        for (double x = -range; x <= range; x = x + 1000) {
            plotPoint(x, model.y_graf_func(x), series);
        }
        //graph.getData().add(series);
        return series;
    }

    private static void plotPoint(final double x, final double y,
                           final XYChart.Series<Double, Double> series) {
        series.getData().add(new XYChart.Data<Double, Double>(x, y));
    }

    //public void clear() {
      //  graph.getData().clear();
    //}
}