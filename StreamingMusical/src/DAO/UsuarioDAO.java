package DAO;

import BL.Cancion;
import BL.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final UsuarioCancionDAO usuarioCancionDAO = new UsuarioCancionDAO();

    public Usuario obtenerUsuarioPorNombreUsuario(String nombreUsuario) {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
        Usuario usuario = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_usuario");
                    usuario = new Usuario(
                            id,
                            rs.getString("nombre_completo"),
                            rs.getString("correo_electronico"),
                            rs.getString("nombre_usuario"),
                            rs.getString("contrasena"),
                            rs.getDouble("saldo"),
                            rs.getBoolean("es_admin")
                    );
                    // Nuevo: Cargar las canciones compradas al obtener el usuario
                    List<Cancion> cancionesCompradas = usuarioCancionDAO.obtenerCancionesCompradas(id);
                    usuario.setCancionesCompradas(cancionesCompradas);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE es_admin = 0";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_completo"),
                        rs.getString("correo_electronico"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena"),
                        rs.getDouble("saldo"),
                        rs.getBoolean("es_admin")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public Usuario obtenerAdmin() {
        String sql = "SELECT * FROM usuarios WHERE es_admin = 1 LIMIT 1";
        Usuario admin = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                admin = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_completo"),
                        rs.getString("correo_electronico"),
                        rs.getString("nombre_usuario"),
                        rs.getString("contrasena"),
                        rs.getDouble("saldo"),
                        rs.getBoolean("es_admin")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }

    public void guardarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre_completo, correo_electronico, nombre_usuario, contrasena, saldo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getCorreo());
            pstmt.setString(3, usuario.getNombreUsuario());
            pstmt.setString(4, usuario.getContrasena());
            pstmt.setDouble(5, usuario.getSaldo());

            pstmt.executeUpdate();
            System.out.println("Usuario guardado en la base de datos.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void guardarAdmin(Usuario admin) {
        String sql = "INSERT INTO usuarios (nombre_completo, correo_electronico, nombre_usuario, contrasena, es_admin) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, admin.getNombre());
            pstmt.setString(2, admin.getCorreo());
            pstmt.setString(3, admin.getNombreUsuario());
            pstmt.setString(4, admin.getContrasena());
            pstmt.setBoolean(5, true);

            pstmt.executeUpdate();
            System.out.println("Administrador guardado en la base de datos.");
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
}