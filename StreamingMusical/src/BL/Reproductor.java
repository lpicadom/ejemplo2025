package BL;

public class Reproductor {
    private String estado;
    private Cancion cancionActual;
    private int volumen;
    private MP3Player mp3Player = new MP3Player();

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cancion getCancionActual() {
        return cancionActual;
    }

    public void setCancionActual(Cancion cancionActual) {
        this.cancionActual = cancionActual;
        if (cancionActual != null) {
            mp3Player.reproducir(cancionActual.getArchivoRuta());
        }
    }

    public void reproducirPreview(String rutaArchivo, int segundos) {
        mp3Player.reproducirPreview(rutaArchivo, segundos);
    }

    public void reproducir(String rutaArchivo) {
        mp3Player.reproducir(rutaArchivo);
    }

    public int getVolumen() {
        return volumen;
    }

    public void setVolumen(int volumen) {
        this.volumen = volumen;
    }
}