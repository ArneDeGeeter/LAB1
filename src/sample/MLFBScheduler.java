package sample;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MLFBScheduler implements Scheduler {
    private String q;
    private int currentTime = -1;
    private Process currentProcess = null;
    private Process nextProcess = null;
    private List<Process> allProcesses = null;
    private List<Process> remainingProcesses = null;
    private List<Process> finishedProcesses = null;
    private ArrayList<ArrayList<Process>> queues = null;
    private int timeallowed;
    private int currentQ;

    public MLFBScheduler(List<Process> allProcesses, String q, int amountQueues) {
        this.q = q;
        queues = new ArrayList<>();
        for (int i = 0; i < amountQueues - 1; i++) {
            queues.add(new ArrayList<>());
        }
        this.allProcesses = allProcesses;
        remainingProcesses = new ArrayList<Process>(allProcesses);
        finishedProcesses = new ArrayList<Process>();
    }

    @Override
    public void setNextProcess(Process process) {

        timeallowed= (int) Main.eval(q.replace("x",Integer.toString(currentQ)));
        currentTime = currentTime < process.getArrivaltime() ? process.getArrivaltime() : currentTime;
        currentProcess = process;

        currentTime = process.getServicetime() - process.getTimeRun() < timeallowed ? currentTime + process.getServicetime() - process.getTimeRun() : currentTime + timeallowed;
        process.setTimeRun(process.getServicetime() - process.getTimeRun() < timeallowed ? process.getServicetime() : process.getTimeRun() + timeallowed);
        remainingProcesses.remove(process);
        if (currentQ != -1) queues.get(currentQ).remove(process);

        for (int i = 0; i < remainingProcesses.size(); i++) {
            if (remainingProcesses.get(i).getArrivaltime() <= currentTime) {
                queues.get(0).add(remainingProcesses.get(i));
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
            queues.get(currentQ+1 == queues.size() ? currentQ : currentQ + 1).add(process);
        }
        System.out.println(this.currentTime + " " + this.currentProcess);
//aaaaaaa
    }


    @Override
    public void startScheduling() {
        Comparator<Process> cmp = Comparator.<Process>comparingInt(p -> p.getArrivaltime())
                .thenComparingInt(p -> p.getArrivaltime());
        allProcesses.sort(cmp);
        while (finishedProcesses.size() != allProcesses.size()) {
            timeallowed = 1;
            while (queues.get(0).size() != 0) {
                currentQ = 0;
                setNextProcess(queues.get(0).get(0));

            }
            //Collections.sort(allProcesses, (a, b) -> a.getTimeRun() == 0 ? b.getTimeRun() == 0 ? a.getArrivaltime() < b.getArrivaltime() ? -1 : 1 : -1 : 1);
            for (int i = 1; i < queues.size(); i++) {
                if (!queues.get(i).isEmpty()&&checkAllQueuesEmpty(queues.subList(0,i))) {
                    currentQ = i;
                    setNextProcess(queues.get(i).get(0));
                    //TODO: set time allowed with function
                }
            }
            if (!remainingProcesses.isEmpty() && checkAllQueuesEmpty(queues)) {
                currentQ = -1;
                remainingProcesses.sort(cmp);
                setNextProcess(remainingProcesses.get(0));
            }

        }

    }

    private boolean checkAllQueuesEmpty(List<ArrayList<Process>> queue) {
        boolean empty = true;
        for (ArrayList a : queue) {
            empty = empty && a.isEmpty();
        }
        return empty;
    }
}
