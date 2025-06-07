package BL;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String contrasena;
    private String tipoSuscripcion;
    private List<ListaDeReproduccion> listasDeReproduccion;
    private HistorialReproduccion historialReproduccion;


    public Usuario(int id, String nombre, String correo, String contrasena, String tipoSuscripcion) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.tipoSuscripcion = tipoSuscripcion;
        this.listasDeReproduccion = new ArrayList<>();
        this.historialReproduccion = new HistorialReproduccion();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipoSuscripcion() {
        return tipoSuscripcion;
    }

    public void setTipoSuscripcion(String tipoSuscripcion) {
        this.tipoSuscripcion = tipoSuscripcion;
    }

    public List<ListaDeReproduccion> getListasDeReproduccion() {
        return listasDeReproduccion;
    }

    public void setListasDeReproduccion(List<ListaDeReproduccion> listasDeReproduccion) {
        this.listasDeReproduccion = listasDeReproduccion;
    }

    public HistorialReproduccion getHistorialReproduccion() {
        return historialReproduccion;
    }

    public void setHistorialReproduccion(HistorialReproduccion historialReproduccion) {
        this.historialReproduccion = historialReproduccion;
    }
}
