package BL;

import javazoom.jl.player.Player;
import java.io.FileInputStream;

public class MP3Player {
    private Player player;
    private Thread playerThread;

    public void reproducir(String rutaArchivo) {
        detener(); // Detener si hay uno ya reproduciéndose

        playerThread = new Thread(() -> {
            try (FileInputStream fis = new FileInputStream(rutaArchivo)) {
                player = new Player(fis);
                player.play(); // Este bloquea hasta que termina
            } catch (Exception e) {
                System.out.println("Error al reproducir MP3: " + e.getMessage());
            }
        });

        playerThread.start();
    }

    public void detener() {
        if (player != null) {
            player.close();
        }
        player = null;
    }
}