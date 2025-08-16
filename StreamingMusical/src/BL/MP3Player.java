package BL;

import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class MP3Player {
    private Player player;
    private Thread playerThread;

    public void reproducir(String rutaArchivo) {
        detener();

        playerThread = new Thread(() -> {
            try (FileInputStream fis = new FileInputStream(rutaArchivo)) {
                player = new Player(fis);
                player.play();
            } catch (Exception e) {
                System.out.println("Error al reproducir MP3: " + e.getMessage());
            }
        });
        playerThread.start();
    }

    public void reproducirPreview(String rutaArchivo, int segundos) {
        detener();

        playerThread = new Thread(() -> {
            try (FileInputStream fis = new FileInputStream(rutaArchivo)) {
                long bytesToPlay = (long) (fis.available() * (segundos / (double) getDuracionEstimada(rutaArchivo)));
                player = new Player(fis);
                player.play((int) bytesToPlay);
            } catch (Exception e) {
                System.out.println("Error al reproducir MP3: " + e.getMessage());
            }
        });
        playerThread.start();
    }

    private int getDuracionEstimada(String rutaArchivo) {
        // Implementación simple para estimar la duración en segundos
        // Sería mejor usar una librería que lea metadatos MP3
        // Por ahora, un valor fijo
        return 200;
    }

    public void detener() {
        if (player != null) {
            player.close();
        }
        player = null;
    }
}