package DAO;

import BL.Usuario;
import BL.Cancion;
import BL.ListaDeReproduccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private UsuarioCancionDAO usuarioCancionDAO = new UsuarioCancionDAO();
    private ListaDeReproduccionDAO listaDeReproduccionDAO = new ListaDeReproduccionDAO();

    public Usuario obtenerUsuarioPorNombreUsuario(String nombreUsuario) {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
        Usuario usuario = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_usuario");

                    // Corrección: Leer el nombre desde 'nombre_usuario'
                    String nombre = rs.getString("nombre_usuario");

                    // Corrección: Asignar el correo a partir del nombre de usuario para evitar el error
                    String correo = rs.getString("nombre_usuario") + "@usuario.com";

                    String contrasena = rs.getString("contrasena");
                    double saldo = rs.getDouble("saldo");
                    boolean esAdmin = rs.getBoolean("es_admin");

                    List<Cancion> cancionesCompradas = usuarioCancionDAO.obtenerCancionesCompradasPorUsuario(id);
                    List<ListaDeReproduccion> listasDeReproduccion = listaDeReproduccionDAO.obtenerListasPorUsuario(id);

                    usuario = new Usuario(id, nombre, correo, nombreUsuario, contrasena, saldo, esAdmin, cancionesCompradas, listasDeReproduccion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public Usuario obtenerAdmin() {
        String sql = "SELECT * FROM usuarios WHERE es_admin = TRUE";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int id = rs.getInt("id_usuario");

                String nombre = rs.getString("nombre_usuario");
                String correo = rs.getString("nombre_usuario") + "@admin.com";

                String nombreUsuario = rs.getString("nombre_usuario");
                String contrasena = rs.getString("contrasena");
                boolean esAdmin = rs.getBoolean("es_admin");
                return new Usuario(id, nombre, correo, nombreUsuario, contrasena, 0.0, esAdmin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void guardarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, nombre_usuario, contrasena, saldo, es_admin) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getNombreUsuario());
            pstmt.setString(4, usuario.getContrasena());
            pstmt.setDouble(5, usuario.getSaldo());
            pstmt.setBoolean(6, usuario.esAdmin());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardarAdmin(Usuario admin) {
        String sql = "INSERT INTO usuarios (nombre, correo, nombre_usuario, contrasena, es_admin) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, admin.getNombre());
            pstmt.setString(2, admin.getCorreo());
            pstmt.setString(3, admin.getNombreUsuario());
            pstmt.setString(4, admin.getContrasena());
            pstmt.setBoolean(5, true);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    admin.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarSaldo(int idUsuario, double nuevoSaldo) {
        String sql = "UPDATE usuarios SET saldo = ? WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, nuevoSaldo);
            pstmt.setInt(2, idUsuario);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE es_admin = FALSE";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre_completo");
                String correo = rs.getString("correo_electronico");
                String nombreUsuario = rs.getString("nombre_usuario");
                String contrasena = rs.getString("contrasena");
                double saldo = rs.getDouble("saldo");
                boolean esAdmin = rs.getBoolean("es_admin");

                List<Cancion> cancionesCompradas = usuarioCancionDAO.obtenerCancionesCompradasPorUsuario(id);
                List<ListaDeReproduccion> listasDeReproduccion = listaDeReproduccionDAO.obtenerListasPorUsuario(id);

                Usuario usuario = new Usuario(id, nombre, correo, nombreUsuario, contrasena, saldo, esAdmin, cancionesCompradas, listasDeReproduccion);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}