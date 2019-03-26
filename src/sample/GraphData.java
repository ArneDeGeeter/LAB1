package sample;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GraphData {
    private static final int AANTALPERCENTIELEN = 100;
    private List<List<Process>> percentielen;

    public static void generatePercentileGraph(Scheduler scheduler){
        GraphData gd = new GraphData((ArrayList) scheduler.getAllProcesses());
        gd.postProcess();
        gd.generatePercentileChart();
    }

    public static void generateRelativeServiceTimeGraph(Scheduler scheduler){
        GraphData gd = new GraphData((ArrayList) scheduler.getAllProcesses());
        gd.postProcess();
        gd.generateRelativeServiceTimeChart();
    }

    public GraphData(ArrayList<Process> processen){
        int pointer=0;

        Collections.sort(processen);

        int aantalProcessenPerPercentiel = processen.size()/AANTALPERCENTIELEN;
        percentielen = new LinkedList<List<Process>>();

        LinkedList<Process> percentiel;

        for(int i=0;i<AANTALPERCENTIELEN;i++)
        {
            percentiel = new LinkedList<>();

            for(int j=0;j<aantalProcessenPerPercentiel;j++)
            {
                percentiel.add(processen.get(pointer));
                pointer++;
            }

            percentielen.add(percentiel);
        }
    }

    //Deze functie bepaalt de time spent waiting per process
    public void postProcess(){
        for(List<Process> percentiel : percentielen)
        {
            for(Process process : percentiel)
            {
                process.setWaitTime();
                process.setGenormaliseerdeTurnaroundTime();
            }
        }
    }

    public void generatePercentileChart(){
         List<double[]> points = new LinkedList<>();
         double[] point;

         int counter=1;
         for(List<Process> percentiel : percentielen)
         {
             point = new double[2];
             point[0] = counter;
             point[1] = 0;

             for(Process process : percentiel)
             {
                 point[1] += process.getGenormaliseerdeTurnaroundTime();
             }

             point[1] /= percentiel.size();
             points.add(point);

             counter++;
         }

         Chart chart = new Chart("test", points);
         chart.setAlwaysOnTop(true);
         chart.pack();
         chart.setSize(600, 400);
         chart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         chart.setVisible(true);
    }

    public void generateRelativeServiceTimeChart(){
        List<double[]> points = new LinkedList<>();
        double[] point;

        double gemiddeldeServiceTime;
        for(List<Process> percentiel : percentielen)
        {
            gemiddeldeServiceTime = berekenGemiddeleServiceTime(percentiel);

            point = new double[2];
            point[0] = gemiddeldeServiceTime;
            point[1] = 0;

            for(Process process : percentiel)
            {
                point[1] += process.getGenormaliseerdeTurnaroundTime();
            }

            point[1] /= percentiel.size();
            points.add(point);
        }

        Chart chart = new Chart("test", points);
        chart.setAlwaysOnTop(true);
        chart.pack();
        chart.setSize(600, 400);
        chart.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        chart.setVisible(true);
    }

    private static double berekenGemiddeleServiceTime(List<Process> percentiel){
        int totaleServiceTime=0;

        for(Process process : percentiel)
        {
            totaleServiceTime += process.getServicetime();
        }

        return totaleServiceTime / percentiel.size();
    }
}
