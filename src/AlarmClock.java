import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class AlarmClock implements Runnable {

    private final LocalTime alarmTime;
    private final String filePath;
    private final Scanner scanner;

    AlarmClock(LocalTime alarmTime, String filePath, Scanner scanner) {
        this.alarmTime = alarmTime;
        this.filePath = filePath;
        this.scanner = scanner;
    }

    @Override
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime alarmDateTime = now.with(alarmTime);


        if (alarmDateTime.isBefore(now)) {
            alarmDateTime = alarmDateTime.plusDays(1);
        }

        while (LocalDateTime.now().isBefore(alarmDateTime)) {
            try {
                Thread.sleep(1000);
                LocalTime current = LocalTime.now();
                System.out.printf("\r%02d:%02d:%02d", current.getHour(), current.getMinute(), current.getSecond());
                System.out.flush();

            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted");
            }
        }
        playSound(filePath);
    }

    private void playSound(String filePath) {
        File audioFile = new File(filePath);

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            System.out.println("\nPress ENTER to Stop the alarm");
            scanner.nextLine();
            clip.stop();

        } catch (UnsupportedAudioFileException e) {
            System.out.println("Audio file not supported");
        } catch (LineUnavailableException e) {
            System.out.println("Audio is unavailable");
        } catch (IOException e) {
            System.out.println("Error reading audio file");
        }
    }
}
