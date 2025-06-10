package BL;

import java.util.List;

public class BL {
    private Usuario usuario;
    private Reproductor reproductor;
    private BibliotecaMusical biblioteca;

    public BL() {
        this.reproductor = new Reproductor();
        this.biblioteca = new BibliotecaMusical();
    }


    public void crearUsuario(String nombre, String correo, String contrasena) {
        this.usuario = new Usuario(1, nombre, correo, contrasena, "Gratis");
        ListaDeReproduccion lista = new ListaDeReproduccion(1, "Mi Lista", usuario);
        usuario.getListasDeReproduccion().add(lista); // poner el id de los usuarios a que avance +1 conforme se generen mas
    }


    public Cancion agregarCancionManual(int idArtista, String nombreArtista, String descripcionArtista,
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

        return cancion; // <-- Devuelve la canción creada
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
