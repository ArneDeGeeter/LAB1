package sample;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FCFSScheduler implements Scheduler {
    private int currentTime = -1;
    private Process currentProcess = null;
    private Process nextProcess = null;
    private List<Process> allProcesses = null;
    private List<Process> arrivedProcesses = null;
    private List<Process> finishedProcesses = null;

    public FCFSScheduler(List<Process> processes) {
        allProcesses = processes;
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
     //   System.out.println(this.currentTime + " " + this.currentProcess);

    }


    @Override
    public void startScheduling() {
        Comparator<Process> cmp = Comparator.<Process>comparingInt(p -> p.getArrivaltime())
                .thenComparingInt(p -> p.getArrivaltime());
        allProcesses.sort(cmp);
        int amountProcesses=0;
        while (finishedProcesses.size() != allProcesses.size()) {
            setNextProcess(allProcesses.get(amountProcesses++));
        }
        System.out.println(currentTime);
    }

    public List<Process> getAllProcesses() {
        return allProcesses;
    }
}
