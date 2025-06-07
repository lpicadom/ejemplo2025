public class Reproductor {
    private String estado;
    private Cancion cancionActual;
    private int volumen;

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
    }

    public int getVolumen() {
        return volumen;
    }

    public void setVolumen(int volumen) {
        this.volumen = volumen;
    }
}
