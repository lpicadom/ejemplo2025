import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Crear artista
        Artista artista = new Artista(1, "Queen", "Banda de rock británica.");

        // Crear álbum
        Album album = new Album(1, "A Night at the Opera", artista, 1975);
        artista.getAlbumes().add(album);

        // Crear canción
        Cancion cancion = new Cancion(1, "Bohemian Rhapsody", artista, album, 354, "Rock", "ruta/bohemian.mp3");
        album.getCanciones().add(cancion);

        // Crear usuario
        Usuario usuario = new Usuario(1, "Luis", "luis@example.com", "1234", "Premium");

        // Crear lista de reproducción
        ListaDeReproduccion lista = new ListaDeReproduccion(1, "Favoritas", usuario);
        lista.getCanciones().add(cancion);
        usuario.getListasDeReproduccion().add(lista);

        // Usar historial
        EntradaHistorial entrada = new EntradaHistorial(cancion, LocalDateTime.now());
        usuario.getHistorialReproduccion().getEntradas().add(entrada);

        // Crear reproductor
        Reproductor reproductor = new Reproductor();
        reproductor.setCancionActual(cancion);
        reproductor.setEstado("Reproduciendo");

        // Biblioteca musical
        BibliotecaMusical biblioteca = new BibliotecaMusical();
        biblioteca.getArtistas().add(artista);
        biblioteca.getAlbumes().add(album);
        biblioteca.getCanciones().add(cancion);

        // Mostrar información
        System.out.println("Usuario: " + usuario.getNombre());
        System.out.println("Canción actual: " + reproductor.getCancionActual().getTitulo());
        System.out.println("Lista de reproducción: " + lista.getNombre());
        System.out.println("Historial: " + usuario.getHistorialReproduccion().getEntradas().size() + " reproducción registrada.");
    }
}