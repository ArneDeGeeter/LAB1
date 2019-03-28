package sample;

public class Process implements Comparable {
    public Process(int pid, int arrivaltime, int servicetime) {
        this.pid = pid;
        this.arrivaltime = arrivaltime;
        this.servicetime = servicetime;
        this.timeRun = 0;
    }

    private final int pid;
    private final int arrivaltime;
    private final int servicetime;
    private int waitTime;
    private double genormaliseerdeTurnaroundTime;
    private int endtime = -1;
    private int timeRun;

    public Process(Process p) {
        this.pid = p.pid;
        this.arrivaltime = p.arrivaltime;
        this.servicetime = p.servicetime;
        this.timeRun = 0;
    }

    public void setWaitTime() {
        this.waitTime = this.endtime - this.arrivaltime - this.servicetime;
    }

    public void setGenormaliseerdeTurnaroundTime() {
        this.genormaliseerdeTurnaroundTime = (this.servicetime + this.waitTime) / this.servicetime;
    }

    public double getGenormaliseerdeTurnaroundTime() {
        return genormaliseerdeTurnaroundTime;
    }

    public int getEndtime() {
        return endtime;
    }

    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }


    public int getTimeRun() {
        return timeRun;
    }

    public void setTimeRun(int timeRun) {
        this.timeRun = timeRun;
    }


    public int getPid() {
        return pid;
    }

    public int getArrivaltime() {
        return arrivaltime;
    }

    public int getServicetime() {
        return servicetime;
    }

    public double calcTAT(int currenttime) {
        return this.servicetime + ((endtime == -1) ? currenttime : endtime) - this.arrivaltime - timeRun;
    }

    public double calcnTAT(int currenttime) {
        return calcTAT(currenttime) / this.servicetime;
    }

    @Override
    public String toString() {
        return "Process{" +
                "pid=" + pid +
                ", arrivaltime=" + arrivaltime +
                ", servicetime=" + servicetime +
                ", endtime=" + endtime +
                ", timeRun=" + timeRun +
                '}';
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public int compareTo(Object o) {
        if (this.servicetime < ((Process) o).servicetime) return -1;
        if (this.servicetime > ((Process) o).servicetime) return 1;
        return 0;

    }
}
