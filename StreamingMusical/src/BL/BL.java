package BL;

import DAO.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class BL {
    private Usuario usuario;
    private Reproductor reproductor;
    private Usuario administrador;

    private UsuarioDAO usuarioDAO;
    private CancionDAO cancionDAO;
    private ArtistaDAO artistaDAO;
    private AlbumDAO albumDAO;
    private ListaDeReproduccionDAO listaDeReproduccionDAO;
    private HistorialReproduccionDAO historialDAO;
    private UsuarioCancionDAO usuarioCancionDAO;

    public BL() {
        this.reproductor = new Reproductor();
        this.administrador = null;
        this.usuarioDAO = new UsuarioDAO();
        this.cancionDAO = new CancionDAO();
        this.artistaDAO = new ArtistaDAO();
        this.albumDAO = new AlbumDAO();
        this.listaDeReproduccionDAO = new ListaDeReproduccionDAO();
        this.historialDAO = new HistorialReproduccionDAO();
        this.usuarioCancionDAO = new UsuarioCancionDAO();

        this.administrador = usuarioDAO.obtenerAdmin();
    }

    public Usuario crearUsuario(String nombre, String correo, String contrasena) {
        Usuario nuevoUsuario = new Usuario(nombre, correo, nombre, contrasena);
        usuarioDAO.guardarUsuario(nuevoUsuario);
        this.setUsuario(nuevoUsuario);
        System.out.println("Usuario creado correctamente con bono de $2.99.");
        return nuevoUsuario;
    }

    public boolean registrarAdmin(String correo, String nombreUsuario, String contrasena) {
        if (this.administrador != null) {
            System.out.println("Ya existe un administrador registrado.");
            return false;
        }
        if (!Usuario.validarContrasena(contrasena)) {
            System.out.println("La contraseña del administrador no cumple con los requisitos.");
            return false;
        }
        this.administrador = new Usuario(-1, "Administrador", null, null, null, null, correo, nombreUsuario, contrasena, true);
        usuarioDAO.guardarAdmin(administrador);
        System.out.println("Administrador registrado correctamente.");
        return true;
    }

    public Usuario iniciarSesion(String nombreUsuario, String contrasena) {
        Usuario usuario = usuarioDAO.obtenerUsuarioPorNombreUsuario(nombreUsuario);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            // Cargar datos adicionales del usuario al iniciar sesión
            usuario.setCancionesCompradas(usuarioCancionDAO.obtenerCancionesCompradasPorUsuario(usuario.getId()));
            usuario.setListasDeReproduccion(listaDeReproduccionDAO.obtenerListasPorUsuario(usuario.getId()));
            this.setUsuario(usuario);
            System.out.println("Sesión iniciada como usuario: " + usuario.getNombre());
            return usuario;
        }
        if (this.administrador != null && this.administrador.getNombreUsuario().equals(nombreUsuario) && this.administrador.getContrasena().equals(contrasena)) {
            System.out.println("Sesión iniciada como administrador.");
            return this.administrador;
        }
        System.out.println("Nombre de usuario o contraseña incorrectos.");
        return null;
    }

    public Cancion agregarCancionManual(int idArtista, String nombreArtista, String descripcionArtista,
                                        int idAlbum, String tituloAlbum, int anioAlbum,
                                        String tituloCancion, int duracion,
                                        String genero, String ruta, String compositor, double precio) {
        Artista artista = artistaDAO.obtenerArtistaPorId(idArtista);
        if (artista == null) {
            artista = new Artista(idArtista, nombreArtista, descripcionArtista);
            artistaDAO.guardarArtista(artista);
        }

        Album album = albumDAO.obtenerAlbumPorId(idAlbum);
        if (album == null) {
            album = new Album(idAlbum, tituloAlbum, artista, anioAlbum);
            albumDAO.guardarAlbum(album);
        }

        Cancion cancion = new Cancion(0, tituloCancion, artista, album, duracion, genero, ruta, compositor, precio);
        cancionDAO.guardarCancion(cancion);
        return cancion;
    }

    public void reproducirCancion(Cancion cancion) {
        if (usuario == null) {
            reproductor.reproducir(cancion.getArchivoRuta());
        } else if (usuario.esAdmin() || usuario.haCompradoCancion(cancion)) {
            reproductor.reproducir(cancion.getArchivoRuta());
            if (!usuario.esAdmin()) {
                historialDAO.guardarEntrada(usuario.getId(), cancion.getId());
                System.out.println("Canción '" + cancion.getTitulo() + "' agregada al historial.");
            }
        } else {
            System.out.println("No has comprado esta canción. Solo puedes reproducir un preview de 30 segundos.");
            reproductor.reproducirPreview(cancion.getArchivoRuta(), 30);
        }
    }

    public void comprarCancion(Cancion cancion) {
        if (usuario == null) {
            System.out.println("Debe iniciar sesión para comprar canciones.");
            return;
        }

        if (usuario.haCompradoCancion(cancion)) {
            System.out.println("❌ Ya has comprado esta canción.");
            return;
        }

        if (usuario.getSaldo() >= cancion.getPrecio()) {
            double nuevoSaldo = usuario.getSaldo() - cancion.getPrecio();
            usuario.setSaldo(nuevoSaldo);
            usuarioCancionDAO.guardarCompra(usuario.getId(), cancion.getId());
            usuarioDAO.actualizarSaldo(usuario.getId(), nuevoSaldo);
            // Actualiza la lista en memoria del usuario después de la compra
            usuario.getCancionesCompradas().add(cancion);
            System.out.println("✅ Canción '" + cancion.getTitulo() + "' comprada. Nuevo saldo: $" + nuevoSaldo);
        } else {
            System.out.println("Saldo insuficiente para comprar esta canción.");
        }
    }

    public void mostrarHistorial() {
        if (usuario == null) {
            System.out.println("No hay usuario activo.");
            return;
        }

        List<EntradaHistorial> entradas = historialDAO.obtenerHistorialPorUsuario(usuario.getId());
        if (entradas.isEmpty()) {
            System.out.println("El historial de reproducción está vacío.");
            return;
        }

        System.out.println("🎵 Historial de reproducción:");
        for (EntradaHistorial entrada : entradas) {
            Cancion c = entrada.getCancion();
            // Verifica si la canción existe antes de imprimir
            if(c != null) {
                System.out.println("- " + c.getTitulo() + " por " + c.getArtista().getNombre() +
                        " [" + entrada.getFechaHora().toString() + "]");
            }
        }
    }

    // Método para obtener las listas de reproducción y las carga con las canciones
    public void mostrarListasDeReproduccion() {
        if (usuario == null) {
            System.out.println("No hay usuario activo.");
            return;
        }

        List<ListaDeReproduccion> listas = listaDeReproduccionDAO.obtenerListasPorUsuario(usuario.getId());
        usuario.setListasDeReproduccion(listas);

        if (listas.isEmpty()) {
            System.out.println("No tienes listas de reproducción.");
            return;
        }

        System.out.println("Mis listas de reproducción:");
        for(ListaDeReproduccion lista : listas) {
            System.out.println("Nombre: " + lista.getNombre() + " (ID: " + lista.getId() + ")");
            if (!lista.getCanciones().isEmpty()) {
                System.out.println("  Canciones:");
                for (Cancion cancion : lista.getCanciones()) {
                    System.out.println("    - " + cancion.getTitulo() + " por " + cancion.getArtista().getNombre());
                }
            } else {
                System.out.println("  (Vacía)");
            }
        }
    }

    public List<Cancion> obtenerTopCanciones() {
        return cancionDAO.obtenerTop5Canciones();
    }

    public List<Cancion> obtenerTodasLasCanciones() {
        return cancionDAO.obtenerTodasLasCanciones();
    }

    // Método agregado para obtener una canción por su ID desde la capa DAO
    public Cancion obtenerCancionPorId(int idCancion) {
        return cancionDAO.obtenerCancionPorId(idCancion);
    }

    public Cancion buscarCancionPorTitulo(String titulo) {
        return cancionDAO.buscarCancionPorTitulo(titulo);
    }

    public List<ListaDeReproduccion> obtenerListasDeReproduccion(int idUsuario) {
        return listaDeReproduccionDAO.obtenerListasPorUsuario(idUsuario);
    }

    public void crearListaDeReproduccion(String nombreLista) {
        if (usuario == null) {
            System.out.println("Debe iniciar sesión para crear listas de reproducción.");
            return;
        }
        ListaDeReproduccion nuevaLista = new ListaDeReproduccion(0, nombreLista, new ArrayList<Cancion>());
        int idGenerado = listaDeReproduccionDAO.guardarListaDeReproduccion(nuevaLista, usuario.getId());
        if (idGenerado != -1) {
            nuevaLista.setId(idGenerado);
            System.out.println("✅ Lista '" + nombreLista + "' creada correctamente.");
            // Actualiza la lista en memoria del usuario
            usuario.getListasDeReproduccion().add(nuevaLista);
        } else {
            System.out.println("❌ No se pudo crear la lista.");
        }
    }

    public void agregarCancionALista(int idLista, int idCancion) {
        listaDeReproduccionDAO.guardarCancionEnLista(idLista, idCancion);
        // Recargar la lista en memoria para que se muestre la canción
        ListaDeReproduccion listaActualizada = listaDeReproduccionDAO.obtenerListaPorId(idLista);
        if (listaActualizada != null) {
            for (ListaDeReproduccion lista : usuario.getListasDeReproduccion()) {
                if (lista.getId() == idLista) {
                    lista.setCanciones(listaActualizada.getCanciones());
                    break;
                }
            }
        }
        System.out.println("✅ Canción agregada a la base de datos.");
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioDAO.obtenerTodosLosUsuarios();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getAdministrador() {
        return administrador;
    }

    public Reproductor getReproductor() {
        return reproductor;
    }

    // Nuevo método para mostrar las canciones compradas
    public void mostrarCancionesCompradas() {
        if (usuario == null) {
            System.out.println("No hay usuario activo.");
            return;
        }

        List<Cancion> compradas = usuarioCancionDAO.obtenerCancionesCompradasPorUsuario(usuario.getId());
        if (compradas.isEmpty()) {
            System.out.println("No has comprado ninguna canción.");
            return;
        }

        System.out.println("🎵 Canciones compradas:");
        for (Cancion cancion : compradas) {
            System.out.println("- " + cancion.getTitulo() + " - " + cancion.getArtista().getNombre());
        }
    }
}