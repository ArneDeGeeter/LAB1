package sample;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Chart extends ApplicationFrame {

    public Chart(final String title, LinkedList<double[]> points) {

        super(title);
        final XYSeries series = new XYSeries("Hello World");

        for (double[] point : points)
            series.add(point[0], point[1]);

        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Hello World",
                "Service Time Percentile",
                "Normalized Turnaround Time",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500 * 4, 270 * 4));
        setContentPane(chartPanel);
    }

    public Chart(final String title, ArrayList<LinkedList<double[]>> pointsList) {

        super(title);
        final XYSeriesCollection data = new XYSeriesCollection();
        for (int i = 0; i < pointsList.size(); i++) {
            XYSeries series = null;
            if (i == 0) {
                series = new XYSeries("FCFS");
            } else if (i == 1) {
                series = new XYSeries("HRRN");

            } else if (i == 2) {

                series = new XYSeries("MLFB");
            } else if (i == 3) {

                series = new XYSeries("RR");
            } else if (i == 4) {
                series = new XYSeries("SPN");

            } else {

                series = new XYSeries("STR");
            }

            for (double[] point : pointsList.get(i))
                series.add(point[0], point[1]);

            data.addSeries(series);
        }

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Normalized Turnaround Time",
                "Service Time",
                "Normalized Turnaround Time",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500 * 4, 270 * 4));
        setContentPane(chartPanel);
    }
}