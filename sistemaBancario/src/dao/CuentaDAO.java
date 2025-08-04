package dao;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaDAO {

    public void guardar(CuentaBancaria cuenta) throws SQLException {
        String sql = "INSERT INTO cuenta_bancaria (numero_cuenta, tipo, saldo, activa, fecha_creacion, cedula_cliente, limite_credito) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cuenta.getNumeroCuenta());
            stmt.setString(2, cuenta.getTipo());
            stmt.setDouble(3, cuenta.getSaldo());
            stmt.setBoolean(4, cuenta.isActiva());
            stmt.setDate(5, new java.sql.Date(cuenta.getFechaCreacion().getTime()));
            stmt.setString(6, cuenta.getCedulaCliente());
            // El límite de crédito solo aplica a cuentas de crédito
            if (cuenta instanceof CuentaCredito) {
                stmt.setDouble(7, ((CuentaCredito) cuenta).getLimiteCredito());
            } else {
                stmt.setNull(7, Types.DOUBLE);
            }

            stmt.executeUpdate();
        }
    }

    public CuentaBancaria buscarPorNumero(String numeroCuenta) throws SQLException {
        String sql = "SELECT * FROM cuenta_bancaria WHERE numero_cuenta = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroCuenta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tipo = rs.getString("tipo");
                String cedulaCliente = rs.getString("cedula_cliente");
                boolean activa = rs.getBoolean("activa");
                double saldo = rs.getDouble("saldo");
                CuentaBancaria cuenta;
                try {
                    switch (tipo) {
                        case "Ahorro":
                            cuenta = new CuentaAhorro(cedulaCliente, saldo, 0.03);
                            break;
                        case "Debito":
                            cuenta = new CuentaDebito(cedulaCliente, saldo, 0.01);
                            break;
                        case "Credito":
                            double limite = rs.getDouble("limite_credito");
                            cuenta = new CuentaCredito(cedulaCliente, limite);
                            cuenta.setSaldo(saldo);
                            break;
                        default:
                            return null;
                    }
                    cuenta.setActiva(activa);
                    cuenta.setNumeroCuenta(rs.getString("numero_cuenta")); // Asignar el número de cuenta desde la base de datos
                    return cuenta;
                } catch (Exception e) {
                    throw new SQLException("Error al crear la cuenta: " + e.getMessage());
                }
            }
        }
        return null;
    }

    public List<CuentaBancaria> listarPorCliente(String cedulaCliente) throws SQLException {
        List<CuentaBancaria> cuentas = new ArrayList<>();
        String sql = "SELECT * FROM cuenta_bancaria WHERE cedula_cliente = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cedulaCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                try {
                    String tipo = rs.getString("tipo");
                    double saldo = rs.getDouble("saldo");
                    boolean activa = rs.getBoolean("activa");
                    String numeroCuenta = rs.getString("numero_cuenta");
                    CuentaBancaria cuenta;
                    switch (tipo) {
                        case "Ahorro":
                            cuenta = new CuentaAhorro(cedulaCliente, saldo, 0.03);
                            break;
                        case "Debito":
                            cuenta = new CuentaDebito(cedulaCliente, saldo, 0.01);
                            break;
                        case "Credito":
                            double limite = rs.getDouble("limite_credito");
                            cuenta = new CuentaCredito(cedulaCliente, limite);
                            cuenta.setSaldo(saldo);
                            break;
                        default:
                            continue;
                    }
                    cuenta.setActiva(activa);
                    cuenta.setNumeroCuenta(numeroCuenta); // Usar el método setNumeroCuenta()
                    cuentas.add(cuenta);
                } catch (Exception e) {
                    System.err.println("Error al procesar cuenta: " + e.getMessage());
                }
            }
        }

        return cuentas;
    }

    public void actualizarEstado(String numeroCuenta, boolean activa) throws SQLException {
        String sql = "UPDATE cuenta_bancaria SET activa = ? WHERE numero_cuenta = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, activa);
            stmt.setString(2, numeroCuenta);
            stmt.executeUpdate();
        }
    }

    // Nuevo método para actualizar el saldo
    public void actualizarSaldo(String numeroCuenta, double nuevoSaldo) throws SQLException {
        String sql = "UPDATE cuenta_bancaria SET saldo = ? WHERE numero_cuenta = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, nuevoSaldo);
            stmt.setString(2, numeroCuenta);
            stmt.executeUpdate();
        }
    }
}