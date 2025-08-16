package DAO;

import BL.Artista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistaDAO {

    public Artista obtenerArtistaPorId(int id) {
        String sql = "SELECT * FROM artistas WHERE id_artista = ?";
        Artista artista = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    artista = new Artista(
                            rs.getInt("id_artista"),
                            rs.getString("nombre"),
                            rs.getString("biografia")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artista;
    }

    public List<Artista> obtenerTodosLosArtistas() {
        List<Artista> artistas = new ArrayList<>();
        String sql = "SELECT * FROM artistas";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Artista artista = new Artista(
                        rs.getInt("id_artista"),
                        rs.getString("nombre"),
                        rs.getString("biografia")
                );
                artistas.add(artista);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artistas;
    }

    public void guardarArtista(Artista artista) {
        String sql = "INSERT INTO artistas (nombre, biografia) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, artista.getNombre());
            pstmt.setString(2, artista.getBiografia());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}