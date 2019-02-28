package sample;

public class Process {
    public Process(int pid, int arrivaltime, int servicetime) {
        this.pid = pid;
        this.arrivaltime = arrivaltime;
        this.servicetime = servicetime;
        this.timeRun = 0;
    }

    private final int pid;
    private final int arrivaltime;
    private final int servicetime;

    public Process(Process p) {
        this.pid = p.pid;
        this.arrivaltime = p.arrivaltime;
        this.servicetime = p.servicetime;
        this.timeRun = 0;
    }

    public int getEndtime() {
        return endtime;
    }

    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }

    private int endtime = -1;

    public int getTimeRun() {
        return timeRun;
    }

    public void setTimeRun(int timeRun) {
        this.timeRun = timeRun;
    }

    private int timeRun;

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
    public double calcnTAT(int currenttime){
        System.out.println(pid+ " "+calcTAT(currenttime)/this.servicetime);
        return calcTAT(currenttime)/this.servicetime;
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
}
