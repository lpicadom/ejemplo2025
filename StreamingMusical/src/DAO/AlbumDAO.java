package DAO;

import BL.Album;
import BL.Artista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlbumDAO {

    public Album obtenerAlbumPorId(int id) {
        String sql = "SELECT * FROM albumes WHERE id_album = ?";
        Album album = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Artista artista = new ArtistaDAO().obtenerArtistaPorId(rs.getInt("id_artista"));
                    album = new Album(
                            rs.getInt("id_album"),
                            rs.getString("titulo"),
                            artista,
                            rs.getInt("anio_lanzamiento")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return album;
    }

    public List<Album> obtenerTodosLosAlbumes() {
        List<Album> albumes = new ArrayList<>();
        String sql = "SELECT * FROM albumes";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Artista artista = new ArtistaDAO().obtenerArtistaPorId(rs.getInt("id_artista"));
                Album album = new Album(
                        rs.getInt("id_album"),
                        rs.getString("titulo"),
                        artista,
                        rs.getInt("anio_lanzamiento")
                );
                albumes.add(album);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return albumes;
    }

    public void guardarAlbum(Album album) {
        String sql = "INSERT INTO albumes (titulo, anio_lanzamiento, id_artista) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, album.getTitulo());
            pstmt.setInt(2, album.getAnioLanzamiento());
            pstmt.setInt(3, album.getArtista().getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}