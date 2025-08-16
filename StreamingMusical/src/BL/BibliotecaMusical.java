package BL;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class BibliotecaMusical {
    private List<Cancion> canciones;
    private List<Album> albumes;
    private List<Artista> artistas;
    private List<Usuario> usuarios;

    public BibliotecaMusical() {
        this.canciones = new ArrayList<>();
        this.albumes = new ArrayList<>();
        this.artistas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public List<Cancion> getTop5Canciones() {
        List<Cancion> topCanciones = new ArrayList<>(canciones);
        // Ordena las canciones de mayor a menor calificación promedio
        Collections.sort(topCanciones, (c1, c2) -> Double.compare(c2.getCalificacionPromedio(), c1.getCalificacionPromedio()));
        // Devuelve las primeras 5 canciones o menos si no hay suficientes
        return topCanciones.subList(0, Math.min(topCanciones.size(), 5));
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public List<Artista> getArtistas() {
        return artistas;
    }

    public List<Album> getAlbumes() {
        return albumes;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Artista buscarArtistaPorId(int id) {
        for (Artista a : artistas) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public Cancion buscarCancionPorTitulo(String titulo) {
        for (Cancion c : canciones) {
            if (c.getTitulo().equalsIgnoreCase(titulo)) {
                return c;
            }
        }
        return null;
    }
}