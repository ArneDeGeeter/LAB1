package sample;

import java.util.ArrayList;
import java.util.List;

public class SchedulerOld {
    private int currentTime = 0;
    private Process currentProccess;
    private Process nextProcess;
    private List<Process> UsedProcesses=new ArrayList<>();

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public Process getCurrentProccess() {
        return currentProccess;
    }

    public void setCurrentProccess(Process currentProccess) {
        this.currentProccess = currentProccess;
    }

    public Process getNextProcess() {
        return nextProcess;
    }

    public void setNextProcess(Process nextProcess) {
        this.nextProcess = nextProcess;
        this.currentTime = currentTime >= nextProcess.getArrivaltime() ? currentTime : nextProcess.getArrivaltime();
        this.currentTime+=nextProcess.getServicetime();
        getUsedProcesses().add(nextProcess);
    }

    public List<Process> getUsedProcesses() {
        return UsedProcesses;
    }

    public void setUsedProcesses(List<Process> usedProcesses) {
        UsedProcesses = usedProcesses;
    }
}
