import java.time.LocalTime;

public class AlarmClock implements Runnable {

    private final LocalTime alarmTime;

    AlarmClock(LocalTime alarmTime){
        this.alarmTime = alarmTime;
    }

    @Override
    public void run(){
        while(LocalTime.now().isBefore(alarmTime)){
            try {
                Thread.sleep(1000);

                LocalTime now = LocalTime.now();

                System.out.printf("\r%02d:%02d:%02d",now.getHour(),now.getMinute(),now.getSecond());

            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted");
            }
        }

    }
}
