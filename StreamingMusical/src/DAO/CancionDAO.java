package DAO;

import BL.Album;
import BL.Artista;
import BL.Cancion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CancionDAO {
    private final ArtistaDAO artistaDAO = new ArtistaDAO();
    private final AlbumDAO albumDAO = new AlbumDAO();

    public Cancion obtenerCancionPorId(int idCancion) {
        String sql = "SELECT * FROM canciones WHERE id_cancion = ?";
        Cancion cancion = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idCancion);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Artista artista = artistaDAO.obtenerArtistaPorId(rs.getInt("id_artista"));
                    Album album = albumDAO.obtenerAlbumPorId(rs.getInt("id_album"));

                    cancion = new Cancion(
                            rs.getInt("id_cancion"),
                            rs.getString("titulo"),
                            artista,
                            album,
                            rs.getInt("duracion_segundos"),
                            rs.getString("genero"),
                            rs.getString("ruta_archivo"),
                            rs.getString("compositor"),
                            rs.getDouble("precio")
                    );
                    cancion.setCalificacionPromedio(rs.getDouble("calificacion_promedio"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cancion;
    }

    public List<Cancion> obtenerTop5Canciones() {
        List<Cancion> canciones = new ArrayList<>();
        String sql = "SELECT * FROM canciones ORDER BY calificacion_promedio DESC LIMIT 5";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Artista artista = artistaDAO.obtenerArtistaPorId(rs.getInt("id_artista"));
                Album album = albumDAO.obtenerAlbumPorId(rs.getInt("id_album"));
                Cancion cancion = new Cancion(
                        rs.getInt("id_cancion"),
                        rs.getString("titulo"),
                        artista,
                        album,
                        rs.getInt("duracion_segundos"),
                        rs.getString("genero"),
                        rs.getString("ruta_archivo"),
                        rs.getString("compositor"),
                        rs.getDouble("precio")
                );
                cancion.setCalificacionPromedio(rs.getDouble("calificacion_promedio"));
                canciones.add(cancion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return canciones;
    }

    public List<Cancion> obtenerTodasLasCanciones() {
        List<Cancion> canciones = new ArrayList<>();
        String sql = "SELECT * FROM canciones";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Artista artista = artistaDAO.obtenerArtistaPorId(rs.getInt("id_artista"));
                Album album = albumDAO.obtenerAlbumPorId(rs.getInt("id_album"));
                Cancion cancion = new Cancion(
                        rs.getInt("id_cancion"),
                        rs.getString("titulo"),
                        artista,
                        album,
                        rs.getInt("duracion_segundos"),
                        rs.getString("genero"),
                        rs.getString("ruta_archivo"),
                        rs.getString("compositor"),
                        rs.getDouble("precio")
                );
                cancion.setCalificacionPromedio(rs.getDouble("calificacion_promedio"));
                canciones.add(cancion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return canciones;
    }

    public Cancion buscarCancionPorTitulo(String titulo) {
        String sql = "SELECT * FROM canciones WHERE titulo = ?";
        Cancion cancion = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, titulo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Artista artista = artistaDAO.obtenerArtistaPorId(rs.getInt("id_artista"));
                    Album album = albumDAO.obtenerAlbumPorId(rs.getInt("id_album"));

                    cancion = new Cancion(
                            rs.getInt("id_cancion"),
                            rs.getString("titulo"),
                            artista,
                            album,
                            rs.getInt("duracion_segundos"),
                            rs.getString("genero"),
                            rs.getString("ruta_archivo"),
                            rs.getString("compositor"),
                            rs.getDouble("precio")
                    );
                    cancion.setCalificacionPromedio(rs.getDouble("calificacion_promedio"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cancion;
    }

    public void guardarCancion(Cancion cancion) {
        String sql = "INSERT INTO canciones (titulo, id_artista, id_album, duracion_segundos, genero, ruta_archivo, compositor, precio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cancion.getTitulo());
            pstmt.setInt(2, cancion.getArtista().getId());
            pstmt.setInt(3, cancion.getAlbum().getId());
            pstmt.setInt(4, cancion.getDuracionSegundos());
            pstmt.setString(5, cancion.getGenero());
            pstmt.setString(6, cancion.getArchivoRuta());
            pstmt.setString(7, cancion.getCompositor());
            pstmt.setDouble(8, cancion.getPrecio());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}