package dao;

import model.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministradorDAO {

    public void guardar(Administrador administrador) throws SQLException {
        String sql = "INSERT INTO administrador (cedula, nombre, apellido, correo_electronico) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, administrador.getCedula());
            stmt.setString(2, administrador.getNombre());
            stmt.setString(3, administrador.getApellido());
            stmt.setString(4, administrador.getCorreoElectronico());
            stmt.executeUpdate();
        }
    }

    public Administrador buscar() throws SQLException {
        String sql = "SELECT * FROM administrador LIMIT 1";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return new Administrador(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo_electronico")
                );
            }
        }
        return null;
    }
}