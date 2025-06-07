package BL;

import java.time.LocalDateTime;

public class ControladorApp {
    private Usuario usuario;
    private Reproductor reproductor;
    private BibliotecaMusical biblioteca;

    public ControladorApp() {
        this.reproductor = new Reproductor();
        this.biblioteca = new BibliotecaMusical();
    }

    public void inicializarDatos() {
        // Crear artista
        Artista artista = new Artista(1, "Queen", "Banda de rock británica.");
        Album album = new Album(1, "A Night at the Opera", artista, 1975);
        artista.getAlbumes().add(album);

        // Crear canción
        Cancion cancion = new Cancion(1, "Bohemian Rhapsody", artista, album, 354, "Rock", "ruta/bohemian.mp3");
        album.getCanciones().add(cancion);

        // Crear usuario
        this.usuario = new Usuario(1, "Luis", "luis@example.com", "1234", "Premium");

        // Crear lista de reproducción
        ListaDeReproduccion lista = new ListaDeReproduccion(1, "Favoritas", usuario);
        lista.getCanciones().add(cancion);
        usuario.getListasDeReproduccion().add(lista);

        // Historial
        EntradaHistorial entrada = new EntradaHistorial(cancion, LocalDateTime.now());
        usuario.getHistorialReproduccion().getEntradas().add(entrada);

        // Reproductor
        reproductor.setCancionActual(cancion);
        reproductor.setEstado("Reproduciendo");

        // Biblioteca
        biblioteca.getArtistas().add(artista);
        biblioteca.getAlbumes().add(album);
        biblioteca.getCanciones().add(cancion);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Reproductor getReproductor() {
        return reproductor;
    }

    public BibliotecaMusical getBiblioteca() {
        return biblioteca;
    }
}
