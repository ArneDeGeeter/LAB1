package sample;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RRScheduler implements Scheduler {
    private final int q;
    private int currentTime = -1;
    private Process currentProcess = null;
    private Process nextProcess = null;
    private List<Process> allProcesses = null;
    private List<Process> remainingProcesses = null;
    private List<Process> arrivedProcesses = null;
    private List<Process> finishedProcesses = null;

    public RRScheduler(List<Process> allProcesses, int q) {
        this.q = q;
        this.allProcesses = allProcesses;
        remainingProcesses = new ArrayList<Process>(allProcesses);
        arrivedProcesses = new ArrayList<Process>();
        finishedProcesses = new ArrayList<Process>();
    }

    @Override
    public void setNextProcess(Process process) {
        currentTime = currentTime < process.getArrivaltime() ? process.getArrivaltime() : currentTime;
        currentProcess = process;

        currentTime = process.getServicetime() - process.getTimeRun() < q ? currentTime + process.getServicetime() - process.getTimeRun() : currentTime + q;
        process.setTimeRun(process.getServicetime() - process.getTimeRun() < q ? process.getServicetime() : process.getTimeRun() + q);
        remainingProcesses.remove(process);
        arrivedProcesses.remove(process);

        for (int i = 0; i < remainingProcesses.size(); i++) {
            if (remainingProcesses.get(i).getArrivaltime() <= currentTime) {
                arrivedProcesses.add(remainingProcesses.get(i));
                remainingProcesses.remove(remainingProcesses.get(i));
                i--;
            } else {
                break;
            }
        }
        if (process.getServicetime() <= process.getTimeRun()) {
            finishedProcesses.add(process);
            process.setEndtime(currentTime);

        } else {
            arrivedProcesses.add(process);
        }
    //    System.out.println(this.currentTime + " " + this.currentProcess);

    }


    @Override
    public void startScheduling() {
        Comparator<Process> cmp = Comparator.<Process>comparingInt(p -> p.getArrivaltime())
                .thenComparingInt(p -> p.getArrivaltime());
        allProcesses.sort(cmp);
        while (finishedProcesses.size() != allProcesses.size()) {
            while (arrivedProcesses.size() != 0) {
                setNextProcess(arrivedProcesses.get(0));

            }
            //Collections.sort(allProcesses, (a, b) -> a.getTimeRun() == 0 ? b.getTimeRun() == 0 ? a.getArrivaltime() < b.getArrivaltime() ? -1 : 1 : -1 : 1);
            if (!remainingProcesses.isEmpty()) {
                remainingProcesses.sort(cmp);
                setNextProcess(remainingProcesses.get(0));
            }

        }
    }

    public List<Process> getAllProcesses() {
        return allProcesses;
    }

    public void setAllProcesses(List<Process> allProcesses) {
        this.allProcesses = allProcesses;
    }
}
