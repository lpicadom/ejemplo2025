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

public class HistorialReproduccionDAO {

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
                    Cancion cancion = new CancionDAO().obtenerCancionPorId(rs.getInt("id_cancion"));
                    Timestamp fechaHora = rs.getTimestamp("fecha_hora_reproduccion");
                    historial.add(new EntradaHistorial(cancion, fechaHora.toLocalDateTime()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historial;
    }
}