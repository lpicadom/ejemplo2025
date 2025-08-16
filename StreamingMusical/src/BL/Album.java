package BL;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private int id;
    private String titulo;
    private Artista artista;
    private List<Cancion> canciones;
    private int anioLanzamiento;

    public Album(int id, String titulo, Artista artista, int anioLanzamiento) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.canciones = new ArrayList<>();
        this.anioLanzamiento = anioLanzamiento;
    }

    // Getters y Setters
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

    public int getAnioLanzamiento() {
        return anioLanzamiento;
    }

    public void setAnioLanzamiento(int anioLanzamiento) {
        this.anioLanzamiento = anioLanzamiento;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}