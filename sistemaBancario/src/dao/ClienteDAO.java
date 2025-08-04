package dao;

import model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void guardar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (cedula, nombre, apellido, correo_electronico, direccion, sexo, profesion) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getCedula());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellido());
            stmt.setString(4, cliente.getCorreoElectronico());
            stmt.setString(5, cliente.getDireccion());
            stmt.setString(6, cliente.getSexo());
            stmt.setString(7, cliente.getProfesion());

            stmt.executeUpdate();
        }
    }

    public Cliente buscarPorCedula(String cedula) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE cedula = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo_electronico"),
                        rs.getString("direccion"),
                        rs.getString("sexo"),
                        rs.getString("profesion")
                );
            }
        }
        return null;
    }

    public List<Cliente> listar() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getString("cedula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo_electronico"),
                        rs.getString("direccion"),
                        rs.getString("sexo"),
                        rs.getString("profesion")
                );
                lista.add(c);
            }
        }
        return lista;
    }

    public void eliminar(String cedula) throws SQLException {
        String sql = "DELETE FROM cliente WHERE cedula = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cedula);
            stmt.executeUpdate();
        }
    }

    public void actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE cliente SET nombre=?, apellido=?, correo_electronico=?, direccion=?, sexo=?, profesion=? WHERE cedula=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getCorreoElectronico());
            stmt.setString(4, cliente.getDireccion());
            stmt.setString(5, cliente.getSexo());
            stmt.setString(6, cliente.getProfesion());
            stmt.setString(7, cliente.getCedula());

            stmt.executeUpdate();
        }
    }
}
