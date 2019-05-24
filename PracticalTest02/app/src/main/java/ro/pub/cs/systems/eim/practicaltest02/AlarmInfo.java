package ro.pub.cs.systems.eim.practicaltest02;

public class AlarmInfo {
    String ip_client;
    int hour;
    int minute;

    public AlarmInfo(){
        hour = 0;
        minute =0;
        ip_client = "0.0.0.0";
    }

    public AlarmInfo(String ip, int hour, int minute){
        this.ip_client = ip;
        this.hour = hour;
        this.minute = minute;
    }
}
