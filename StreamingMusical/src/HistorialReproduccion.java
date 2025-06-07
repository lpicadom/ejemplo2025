import java.util.ArrayList;
import java.util.List;

public class HistorialReproduccion {
    private List<EntradaHistorial> entradas;

    public HistorialReproduccion() {
        this.entradas = new ArrayList<>();  // ✅ Esto es clave
    }

    public List<EntradaHistorial> getEntradas() {
        return entradas;
    }


}
