package sample;

import javafx.scene.chart.PieChart;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SPNScheduler implements Scheduler {

    private int currentTime = -1;
    private Process currentProcess = null;
    private Process nextProcess = null;
    private List<Process> allProcesses = null;
    private List<Process> remainingProcesses = null;
    private List<Process> arrivedProcesses = null;
    private List<Process> finishedProcesses = null;

    public SPNScheduler(List<Process> allProcesses) {
        this.allProcesses = allProcesses;
        remainingProcesses = new ArrayList<Process>(allProcesses);
        arrivedProcesses = new ArrayList<Process>();
        finishedProcesses = new ArrayList<Process>();
    }

    @Override
    public void setNextProcess(Process process) {
        this.currentProcess = process;
        this.currentTime = currentTime >= currentProcess.getArrivaltime() ? currentTime : currentProcess.getArrivaltime();
        this.currentTime += currentProcess.getServicetime();
        finishedProcesses.add(currentProcess);
        process.setEndtime(currentTime);
        remainingProcesses.remove(currentProcess);
        arrivedProcesses.remove(currentProcess);
        System.out.println(this.currentTime + " " + this.currentProcess+"SPN");
        for (int i = 0; i < remainingProcesses.size(); i++) {
            if (remainingProcesses.get(i).getArrivaltime() <= currentTime) {
                arrivedProcesses.add(remainingProcesses.get(i));
                remainingProcesses.remove(remainingProcesses.get(i));
                i--;
            } else {
                break;
            }
        }

    }


    @Override
    public void startScheduling() {
        Comparator<Process> cmp = Comparator.<Process>comparingInt(p -> p.getArrivaltime())
                .thenComparingInt(p -> p.getArrivaltime());
        allProcesses.sort(cmp);
        int amountProcesses = 0;
        while (finishedProcesses.size() != allProcesses.size()) {

            if (arrivedProcesses.size() != 0) {
                setNextProcess(getSPN(arrivedProcesses));

            } else {
                setNextProcess(allProcesses.get(amountProcesses));
            }
            amountProcesses++;
        }
        System.out.println(currentTime);

    }

    private Process getSPN(List<Process> arrivedProcesses) {
        int scheduleTime = Integer.MAX_VALUE;
        Process shortestProcess = null;
        for (Process p : arrivedProcesses) {
            if (p.getServicetime() < scheduleTime) {
                shortestProcess = p;
                scheduleTime=p.getServicetime();
            }

        }
        return shortestProcess;
    }
}
