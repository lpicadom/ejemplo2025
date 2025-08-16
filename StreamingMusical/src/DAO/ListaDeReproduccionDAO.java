package DAO;

import BL.Cancion;
import BL.ListaDeReproduccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListaDeReproduccionDAO {

    private CancionDAO cancionDAO = new CancionDAO();

    public int guardarListaDeReproduccion(ListaDeReproduccion lista, int idUsuario) {
        String sql = "INSERT INTO listas_de_reproduccion (nombre, id_usuario) VALUES (?, ?)";
        int idGenerado = -1;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, lista.getNombre());
            pstmt.setInt(2, idUsuario);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idGenerado = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idGenerado;
    }

    public List<ListaDeReproduccion> obtenerListasPorUsuario(int idUsuario) {
        List<ListaDeReproduccion> listas = new ArrayList<>();
        String sql = "SELECT * FROM listas_de_reproduccion WHERE id_usuario = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int idLista = rs.getInt("id_lista");
                    String nombreLista = rs.getString("nombre");

                    // Obtener las canciones para cada lista de reproducción
                    List<Cancion> cancionesDeLista = obtenerCancionesDeLista(idLista);

                    ListaDeReproduccion lista = new ListaDeReproduccion(idLista, nombreLista, cancionesDeLista);
                    listas.add(lista);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listas;
    }

    public ListaDeReproduccion obtenerListaPorId(int idLista) {
        String sql = "SELECT * FROM listas_de_reproduccion WHERE id_lista = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idLista);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nombreLista = rs.getString("nombre");
                    List<Cancion> canciones = obtenerCancionesDeLista(idLista);
                    return new ListaDeReproduccion(idLista, nombreLista, canciones);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Cancion> obtenerCancionesDeLista(int idLista) {
        List<Cancion> canciones = new ArrayList<>();
        String sql = "SELECT id_cancion FROM lista_canciones WHERE id_lista = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idLista);
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

    public void guardarCancionEnLista(int idLista, int idCancion) {
        String sql = "INSERT INTO lista_canciones (id_lista, id_cancion) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idLista);
            pstmt.setInt(2, idCancion);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}