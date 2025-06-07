package BL;

import java.util.ArrayList;
import java.util.List;

public class ListaDeReproduccion {
    private int id;
    private String nombre;
    private Usuario propietario;
    private List<Cancion> canciones;

    public ListaDeReproduccion(int id, String nombre, Usuario propietario) {
        this.id = id;
        this.nombre = nombre;
        this.propietario = propietario;
        this.canciones = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void setCanciones(List<Cancion> canciones) {
        this.canciones = canciones;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
