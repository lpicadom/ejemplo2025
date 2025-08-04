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
            if (cuenta instanceof CuentaCredito) {
                stmt.setDouble(7, ((CuentaCredito) cuenta).getLimiteCredito());
            } else {
                stmt.setNull(7, Types.DOUBLE);
            }

            stmt.executeUpdate();
        }
    }

    // Método auxiliar para crear un objeto CuentaBancaria desde un ResultSet
    private CuentaBancaria crearCuentaDesdeResultSet(ResultSet rs) throws SQLException {
        String numeroCuenta = rs.getString("numero_cuenta");
        String tipo = rs.getString("tipo");
        String cedulaCliente = rs.getString("cedula_cliente");
        boolean activa = rs.getBoolean("activa");
        double saldo = rs.getDouble("saldo");

        CuentaBancaria cuenta = null;

        try {
            // Normalizar el tipo de cuenta para comparar sin importar mayúsculas/minúsculas o tildes
            String tipoNormalizado = tipo.trim().toLowerCase();

            switch (tipoNormalizado) {
                case "ahorro":
                    cuenta = new CuentaAhorro(cedulaCliente, saldo, 0.03);
                    break;
                case "debito":
                case "débito": // Añadimos el caso con tilde
                    cuenta = new CuentaDebito(cedulaCliente, saldo, 0.01);
                    break;
                case "credito":
                case "crédito": // Añadimos el caso con tilde
                    double limite = rs.getDouble("limite_credito");
                    cuenta = new CuentaCredito(cedulaCliente, limite);
                    cuenta.setSaldo(saldo);
                    break;
                default:
                    System.err.println("ADVERTENCIA: Tipo de cuenta no reconocido: '" + tipo + "'");
                    return null;
            }

            if (cuenta != null) {
                cuenta.setNumeroCuenta(numeroCuenta);
                cuenta.setActiva(activa);
            }

            return cuenta;
        } catch (Exception e) {
            System.err.println("ERROR: Fallo al crear la cuenta " + numeroCuenta + " del tipo '" + tipo + "'.");
            e.printStackTrace();
            return null;
        }
    }

    public CuentaBancaria buscarPorNumero(String numeroCuenta) throws SQLException {
        String sql = "SELECT * FROM cuenta_bancaria WHERE numero_cuenta = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroCuenta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return crearCuentaDesdeResultSet(rs);
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
                CuentaBancaria cuenta = crearCuentaDesdeResultSet(rs);
                if (cuenta != null) {
                    cuentas.add(cuenta);
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

    public void actualizarSaldo(String numeroCuenta, double nuevoSaldo) throws SQLException {
        String sql = "UPDATE cuenta_bancaria SET saldo = ? WHERE numero_cuenta = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, nuevoSaldo);
            stmt.setString(2, numeroCuenta);
            stmt.executeUpdate();
        }
    }

    public void guardarTransaccion(Transaccion transaccion) throws SQLException {
        String sql = "INSERT INTO transaccion (numero_cuenta, monto, fecha) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transaccion.getNumeroCuenta());
            stmt.setDouble(2, transaccion.getMonto());
            stmt.setTimestamp(3, new java.sql.Timestamp(transaccion.getFecha().getTime()));
            stmt.executeUpdate();
        }
    }

    public List<Transaccion> listarTransacciones(String numeroCuenta) throws SQLException {
        List<Transaccion> transacciones = new ArrayList<>();
        String sql = "SELECT * FROM transaccion WHERE numero_cuenta = ? ORDER BY fecha DESC";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numeroCuenta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaccion t = new Transaccion(
                        rs.getDouble("monto"),
                        rs.getTimestamp("fecha"),
                        rs.getString("numero_cuenta")
                );
                transacciones.add(t);
            }
        }
        return transacciones;
    }
}