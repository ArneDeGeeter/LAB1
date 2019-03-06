package sample;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.util.LinkedList;
import java.util.List;

public class Chart extends ApplicationFrame {

    public Chart(final String title, List<double[]> points) {

        super(title);
        final XYSeries series = new XYSeries("Hello World");

        for(double[] point : points)
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
        chartPanel.setPreferredSize(new java.awt.Dimension(500*4, 270*4));
        setContentPane(chartPanel);

    }
}