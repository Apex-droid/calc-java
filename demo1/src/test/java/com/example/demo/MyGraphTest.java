package com.example.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;


import javafx.scene.chart.XYChart;

class MyGraphTest {

    //MyGraph graph;


    @BeforeEach
    void setUp() {
        ///graph = new MyGraph(areaGraph, 10);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void PlotLine() throws NoSuchFieldException {
        Model a = new Model();
        Model b = new Model();
        a.edit("2*2");
        b.edit("2^2");
       //MyGraph graph1 = new MyGraph(new XYChart<Double, Double>() , 10);
        //MyGraph graph2 = new MyGraph(new XYChart<Double, Double>(), 10);
        a.rebuild_ex();
        b.rebuild_ex();
        //MyGraph.PlotLine(a, 1000000);
        //MyGraph.PlotLine(b,1000000);
        XYChart.Series<Double, Double> series1 = MyGraph.PlotLine(a, 5000);
        XYChart.Series<Double, Double> series2 = MyGraph.PlotLine(b, 5000);
        assertEquals(series1.getChart(), series2.getChart());
        
    }

    @Test
    void clear() {
    }

    @Test
    void testPlotLine() {

    }

    @Test
    void testClear() {
    }
}