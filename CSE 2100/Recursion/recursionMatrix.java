// Mark Cabanero
// CSE 2100: Assignment 3
// October 2, 2016

import java.util.ArrayList;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;

public class recursionMatrix {
    public static void main(String[] args) {
        // Create an array list to hold all the charts.
        ArrayList<XYChart> charts = new ArrayList<>();

        // Initialize the recursion method.
        recursion r = new recursion();
        double[] xData = new double[10000];     // xData    = n being passed through.
        double[] p1aData = new double[10000];   // p1aData  = Problem 1's output.
        double[] p1bData = new double[10000];   // p1bData  = Optimized problem 1's output.
        double[] p2aData = new double[10000];   // p2aData  = Problem 2's output.
        double[] p2bData = new double[10000];   // p2bData  = Optimized problem 2's output.

        for (int i = 1; i < 10001; i++) {
            // Run the recursion on each algorithm for the necessary n.
            // Casted as double so it can be graphed.
            xData[i - 1] = (double) i;
            p1aData[i - 1] = (double) r.problem1a(i);
            p1bData[i - 1] = (double) r.problem1b(i);
            p2aData[i - 1] = (double) r.problem2a(i);
            p2bData[i - 1] = (double) r.problem2b(i);
        }

        // Generate the four charts.
        for (int i = 0; i < 4; i++) {
            // Initialization and styling of each chart.
            XYChart chart = new XYChartBuilder().xAxisTitle("n").yAxisTitle("Number of Times Called").width(1280).height(720).build();
            chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
            chart.getStyler().setChartTitleVisible(false);
            chart.getStyler().setLegendPosition(LegendPosition.InsideSE);
            chart.getStyler().setMarkerSize(4);

            if (i < 2) {
                // If it's problem 1, the highest stopping time is 26.
                chart.getStyler().setYAxisMin(0);
                chart.getStyler().setYAxisMax(26);
            } else {
                // Otherwise, it goes up to 260.
                chart.getStyler().setYAxisMin(0);
                chart.getStyler().setYAxisMax(260);
            }

            // Basic if/else to change which graph has what data.
            if (i == 0) {
                chart.addSeries("Problem 1", xData, p1aData);
            } else if (i == 1) {
                chart.addSeries("Problem 1 Optimized", xData, p1bData);
            } else if (i == 2) {
                chart.addSeries("Problem 2", xData, p2aData);
            } else {
                chart.addSeries("Problem 2 Optimized", xData, p2bData);
            }
            charts.add(chart);
        }
        // Display the charts.
        new SwingWrapper<>(charts).displayChartMatrix();
    }
}