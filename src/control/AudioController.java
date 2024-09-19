package control;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AudioController {
    private Clip audioClip;
    private String audioPath = "src/Elements/Audio/";

    private void play(String fileName) {
        String filePath = audioPath + fileName;
        try {
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                System.err.println("File not found: " + filePath);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            audioClip.start();
            System.out.println("Playing: " + fileName);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error playing the file: " + fileName);
            e.printStackTrace();
        }
    }

    public void playMiss() {
        play("miss.wav");
    }

    public void stop() {
        if (audioClip != null && audioClip.isRunning()) {
            audioClip.stop();
            audioClip.close();
            System.out.println("Playback stopped.");
        }
    }

    public void listAudioFiles() {
        File directory = new File(audioPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Directory not found: " + audioPath);
            return;
        }

        try {
            Files.list(Paths.get(audioPath))
                    .filter(Files::isRegularFile)
                    .forEach(path -> System.out.println(path.getFileName().toString()));
        } catch (IOException e) {
            System.err.println("Error listing audio files.");
            e.printStackTrace();
        }
    }
}
