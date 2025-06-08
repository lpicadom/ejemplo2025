package BL;

import java.time.LocalDateTime;
import java.util.List;

public class ControladorApp {
    private Usuario usuario;
    private Reproductor reproductor;
    private BibliotecaMusical biblioteca;

    public ControladorApp() {
        this.reproductor = new Reproductor();
        this.biblioteca = new BibliotecaMusical();
    }


    public void crearUsuario(String nombre, String correo, String contrasena) {
        this.usuario = new Usuario(1, nombre, correo, contrasena, "Gratis");
        ListaDeReproduccion lista = new ListaDeReproduccion(1, "Mi Lista", usuario);
        usuario.getListasDeReproduccion().add(lista);
    }


    public void agregarCancionManual(int idArtista, String nombreArtista, String descripcionArtista,
                                     int idAlbum, String tituloAlbum, int anioAlbum,
                                     int idCancion, String tituloCancion, int duracion,
                                     String genero, String ruta) {

        // Buscar si ya existe el artista
        Artista artista = biblioteca.buscarArtistaPorId(idArtista);
        if (artista == null) {
            artista = new Artista(idArtista, nombreArtista, descripcionArtista);
            biblioteca.getArtistas().add(artista);
        }

        // Buscar si ya existe el álbum
        Album album = artista.buscarAlbumPorId(idAlbum);
        if (album == null) {
            album = new Album(idAlbum, tituloAlbum, artista, anioAlbum);
            artista.getAlbumes().add(album);
            biblioteca.getAlbumes().add(album);
        }

        // Crear la canción
        Cancion cancion = new Cancion(idCancion, tituloCancion, artista, album, duracion, genero, ruta);
        album.getCanciones().add(cancion);
        biblioteca.getCanciones().add(cancion);
    }

    public void reproducirCancion(int index) {
        if (index >= 0 && index < biblioteca.getCanciones().size()) {
            Cancion cancion = biblioteca.getCanciones().get(index);
            reproductor.setCancionActual(cancion);
            reproductor.setEstado("Reproduciendo");

            if (usuario != null) {
                EntradaHistorial entrada = new EntradaHistorial(cancion, java.time.LocalDateTime.now());
                usuario.getHistorialReproduccion().getEntradas().add(entrada);
            }
        }
    }

    public void mostrarHistorial() {
        if (usuario == null) {
            System.out.println("No hay usuario activo.");
            return;
        }

        List<EntradaHistorial> entradas = usuario.getHistorialReproduccion().getEntradas();

        if (entradas.isEmpty()) {
            System.out.println("El historial de reproducción está vacío.");
            return;
        }

        System.out.println("🎵 Historial de reproducción:");
        for (EntradaHistorial entrada : entradas) {
            Cancion c = entrada.getCancion();
            System.out.println("- " + c.getTitulo() + " por " + c.getArtista().getNombre() +
                    " [" + entrada.getFechaHora().toString() + "]");
        }
    }

    public void inicializarDatos() {
        // Crear artista
        Artista artista = new Artista(1, "Queen", "Banda de rock británica.");
        Album album = new Album(1, "A Night at the Opera", artista, 1975);
        artista.getAlbumes().add(album);

        // Crear canción
        Cancion cancion = new Cancion(1, "Bohemian Rhapsody", artista, album, 354, "Rock", "ruta/bohemian.mp3");
        album.getCanciones().add(cancion);

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
