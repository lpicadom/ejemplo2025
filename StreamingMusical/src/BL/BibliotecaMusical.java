package BL;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaMusical {
    private List<Cancion> canciones;
    private List<Album> albumes;
    private List<Artista> artistas;

    public BibliotecaMusical() {
        this.canciones = new ArrayList<>();
        this.albumes = new ArrayList<>();
        this.artistas = new ArrayList<>();
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

    public Artista buscarArtistaPorId(int id) {
        for (Artista a : artistas) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }


}
