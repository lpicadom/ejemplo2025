package DAO;

import BL.Cancion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioCancionDAO {

    private CancionDAO cancionDAO = new CancionDAO();

    public void guardarCompra(int idUsuario, int idCancion) {
        String sql = "INSERT INTO usuarios_canciones (id_usuario, id_cancion) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idCancion);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cancion> obtenerCancionesCompradasPorUsuario(int idUsuario) {
        List<Cancion> canciones = new ArrayList<>();
        String sql = "SELECT id_cancion FROM usuarios_canciones WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int idCancion = rs.getInt("id_cancion");
                    Cancion cancion = cancionDAO.obtenerCancionPorId(idCancion);
                    if (cancion != null) {
                        canciones.add(cancion);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return canciones;
    }
}