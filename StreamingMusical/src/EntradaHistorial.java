import java.time.LocalDateTime;

public class EntradaHistorial {
    private Cancion cancion;
    private LocalDateTime fechaHora;

    public EntradaHistorial(Cancion cancion, LocalDateTime fechaHora) {
        this.cancion = cancion;
        this.fechaHora = fechaHora;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
}
