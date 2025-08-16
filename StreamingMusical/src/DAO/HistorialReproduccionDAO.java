package DAO;

import BL.Cancion;
import BL.EntradaHistorial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class HistorialReproduccionDAO {
    private CancionDAO cancionDAO = new CancionDAO();

    public void guardarEntrada(int idUsuario, int idCancion) {
        String sql = "INSERT INTO historial_reproduccion (id_usuario, id_cancion) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            pstmt.setInt(2, idCancion);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<EntradaHistorial> obtenerHistorialPorUsuario(int idUsuario) {
        List<EntradaHistorial> historial = new ArrayList<>();
        String sql = "SELECT * FROM historial_reproduccion WHERE id_usuario = ? ORDER BY fecha_hora_reproduccion DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int idCancion = rs.getInt("id_cancion");
                    Timestamp timestamp = rs.getTimestamp("fecha_hora_reproduccion");

                    // Obtener la canción completa en lugar de solo su ID
                    Cancion cancion = cancionDAO.obtenerCancionPorId(idCancion);

                    if(cancion != null) {
                        EntradaHistorial entrada = new EntradaHistorial(cancion, timestamp.toLocalDateTime());
                        historial.add(entrada);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historial;
    }
}