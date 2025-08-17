package BL;

import DAO.*;
import java.util.List;
import java.util.ArrayList;

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
        this.usuarioDAO = new UsuarioDAO();
        this.cancionDAO = new CancionDAO();
        this.artistaDAO = new ArtistaDAO();
        this.albumDAO = new AlbumDAO();
        this.listaDeReproduccionDAO = new ListaDeReproduccionDAO();
        this.historialDAO = new HistorialReproduccionDAO();
        this.usuarioCancionDAO = new UsuarioCancionDAO();

        this.administrador = usuarioDAO.obtenerAdmin();
    }

    public String crearUsuario(String nombre, String correo, String contrasena) {
        Usuario nuevoUsuario = new Usuario(nombre, correo, nombre, contrasena);
        usuarioDAO.guardarUsuario(nuevoUsuario);
        this.setUsuario(nuevoUsuario);
        return "✅ Usuario creado correctamente con bono de $2.99.";
    }

    public String registrarAdmin(String correo, String nombreUsuario, String contrasena) {
        if (this.administrador != null) {
            return "❌ Ya existe un administrador registrado.";
        }
        if (!Usuario.validarContrasena(contrasena)) {
            return "❌ La contraseña del administrador no cumple con los requisitos.";
        }
        this.administrador = new Usuario(-1, "Administrador", null, null, null, null, correo, nombreUsuario, contrasena, true);
        usuarioDAO.guardarAdmin(administrador);
        return "✅ Administrador registrado correctamente.";
    }

    public Usuario iniciarSesion(String nombreUsuario, String contrasena) {
        Usuario usuario = usuarioDAO.obtenerUsuarioPorNombreUsuario(nombreUsuario);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            usuario.setCancionesCompradas(usuarioCancionDAO.obtenerCancionesCompradasPorUsuario(usuario.getId()));
            usuario.setListasDeReproduccion(listaDeReproduccionDAO.obtenerListasPorUsuario(usuario.getId()));
            this.setUsuario(usuario);
            return usuario;
        }
        if (this.administrador != null && this.administrador.getNombreUsuario().equals(nombreUsuario) && this.administrador.getContrasena().equals(contrasena)) {
            return this.administrador;
        }
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

    public String reproducirCancion(Cancion cancion) {
        if (usuario == null) {
            reproductor.reproducir(cancion.getArchivoRuta());
            return "🎵 Reproduciendo canción (modo invitado).";
        } else if (usuario.esAdmin() || usuario.haCompradoCancion(cancion)) {
            reproductor.reproducir(cancion.getArchivoRuta());
            if (!usuario.esAdmin()) {
                historialDAO.guardarEntrada(usuario.getId(), cancion.getId());
            }
            return "🎵 Reproduciendo: " + cancion.getTitulo();
        } else {
            reproductor.reproducirPreview(cancion.getArchivoRuta(), 30);
            return "⚠️ No has comprado esta canción. Solo puedes escuchar un preview de 30s.";
        }
    }

    public String comprarCancion(Cancion cancion) {
        if (usuario == null) {
            return "Debe iniciar sesión para comprar canciones.";
        }
        if (usuario.haCompradoCancion(cancion)) {
            return "❌ Ya has comprado esta canción.";
        }
        if (usuario.getSaldo() >= cancion.getPrecio()) {
            double nuevoSaldo = usuario.getSaldo() - cancion.getPrecio();
            usuario.setSaldo(nuevoSaldo);
            usuarioCancionDAO.guardarCompra(usuario.getId(), cancion.getId());
            usuarioDAO.actualizarSaldo(usuario.getId(), nuevoSaldo);
            usuario.getCancionesCompradas().add(cancion);
            return "✅ Canción '" + cancion.getTitulo() + "' comprada. Nuevo saldo: $" + nuevoSaldo;
        } else {
            return "❌ Saldo insuficiente.";
        }
    }

    public String mostrarHistorial() {
        if (usuario == null) {
            return "No hay usuario activo.";
        }

        List<EntradaHistorial> entradas = historialDAO.obtenerHistorialPorUsuario(usuario.getId());
        if (entradas.isEmpty()) {
            return "El historial de reproducción está vacío.";
        }

        StringBuilder sb = new StringBuilder("🎵 Historial de reproducción:\n");
        for (EntradaHistorial entrada : entradas) {
            Cancion c = entrada.getCancion();
            if (c != null) {
                sb.append("- ").append(c.getTitulo())
                        .append(" por ").append(c.getArtista().getNombre())
                        .append(" [").append(entrada.getFechaHora()).append("]\n");
            }
        }
        return sb.toString();
    }

    public String mostrarListasDeReproduccion() {
        if (usuario == null) {
            return "No hay usuario activo.";
        }

        List<ListaDeReproduccion> listas = listaDeReproduccionDAO.obtenerListasPorUsuario(usuario.getId());
        usuario.setListasDeReproduccion(listas);

        if (listas.isEmpty()) {
            return "No tienes listas de reproducción.";
        }

        StringBuilder sb = new StringBuilder("📂 Mis listas de reproducción:\n");
        for (ListaDeReproduccion lista : listas) {
            sb.append("Nombre: ").append(lista.getNombre()).append(" (ID: ").append(lista.getId()).append(")\n");
            if (!lista.getCanciones().isEmpty()) {
                sb.append("  Canciones:\n");
                for (Cancion cancion : lista.getCanciones()) {
                    sb.append("    - ").append(cancion.getTitulo())
                            .append(" por ").append(cancion.getArtista().getNombre()).append("\n");
                }
            } else {
                sb.append("  (Vacía)\n");
            }
        }
        return sb.toString();
    }

    public List<Cancion> obtenerTopCanciones() {
        return cancionDAO.obtenerTop5Canciones();
    }

    public List<Cancion> obtenerTodasLasCanciones() {
        return cancionDAO.obtenerTodasLasCanciones();
    }

    public Cancion obtenerCancionPorId(int idCancion) {
        return cancionDAO.obtenerCancionPorId(idCancion);
    }

    public Cancion buscarCancionPorTitulo(String titulo) {
        return cancionDAO.buscarCancionPorTitulo(titulo);
    }

    public List<ListaDeReproduccion> obtenerListasDeReproduccion(int idUsuario) {
        return listaDeReproduccionDAO.obtenerListasPorUsuario(idUsuario);
    }

    public String crearListaDeReproduccion(String nombreLista) {
        if (usuario == null) {
            return "Debe iniciar sesión para crear listas.";
        }
        ListaDeReproduccion nuevaLista = new ListaDeReproduccion(0, nombreLista, new ArrayList<Cancion>());
        int idGenerado = listaDeReproduccionDAO.guardarListaDeReproduccion(nuevaLista, usuario.getId());
        if (idGenerado != -1) {
            nuevaLista.setId(idGenerado);
            usuario.getListasDeReproduccion().add(nuevaLista);
            return "✅ Lista '" + nombreLista + "' creada correctamente.";
        } else {
            return "❌ No se pudo crear la lista.";
        }
    }

    public String agregarCancionALista(int idLista, int idCancion) {
        listaDeReproduccionDAO.guardarCancionEnLista(idLista, idCancion);
        ListaDeReproduccion listaActualizada = listaDeReproduccionDAO.obtenerListaPorId(idLista);
        if (listaActualizada != null) {
            for (ListaDeReproduccion lista : usuario.getListasDeReproduccion()) {
                if (lista.getId() == idLista) {
                    lista.setCanciones(listaActualizada.getCanciones());
                    break;
                }
            }
        }
        return "✅ Canción agregada a la lista.";
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

    public String mostrarCancionesCompradas() {
        if (usuario == null) {
            return "No hay usuario activo.";
        }

        List<Cancion> compradas = usuarioCancionDAO.obtenerCancionesCompradasPorUsuario(usuario.getId());
        if (compradas.isEmpty()) {
            return "No has comprado ninguna canción.";
        }

        StringBuilder sb = new StringBuilder("🎵 Canciones compradas:\n");
        for (Cancion cancion : compradas) {
            sb.append("- ").append(cancion.getTitulo())
                    .append(" - ").append(cancion.getArtista().getNombre()).append("\n");
        }
        return sb.toString();
    }
}
