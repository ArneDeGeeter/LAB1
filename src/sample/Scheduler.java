package sample;

import java.util.List;

public interface Scheduler {

    void setNextProcess(Process process);

    void startScheduling();

    List<Process> getAllProcesses();
}
