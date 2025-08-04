package controller;

import dao.ClienteDAO;
import dao.CuentaDAO;
import model.*;

import java.sql.SQLException;
import java.util.List;

public class ControladorPrincipal {
    private ClienteDAO clienteDAO;
    private CuentaDAO cuentaDAO;

    public ControladorPrincipal() {
        clienteDAO = new ClienteDAO();
        cuentaDAO = new CuentaDAO();
    }

    // MÉTODOS CLIENTE
    public void registrarCliente(Cliente cliente) throws Exception {
        if (clienteDAO.buscarPorCedula(cliente.getCedula()) != null) {
            throw new Exception("El cliente con esa cédula ya está registrado.");
        }
        clienteDAO.guardar(cliente);
    }

    public void modificarCliente(Cliente cliente) throws Exception {
        if (clienteDAO.buscarPorCedula(cliente.getCedula()) == null) {
            throw new Exception("Cliente no encontrado.");
        }
        clienteDAO.actualizar(cliente);
    }

    public void eliminarCliente(String cedula) throws Exception {
        if (clienteDAO.buscarPorCedula(cedula) == null) {
            throw new Exception("Cliente no encontrado.");
        }
        clienteDAO.eliminar(cedula);
    }

    public Cliente buscarCliente(String cedula) throws SQLException {
        return clienteDAO.buscarPorCedula(cedula);
    }

    public List<Cliente> listarClientes() throws SQLException {
        return clienteDAO.listar();
    }

    // MÉTODOS CUENTA
    public void registrarCuenta(CuentaBancaria cuenta) throws Exception {
        cuentaDAO.guardar(cuenta);
    }

    public void depositar(String numeroCuenta, double monto) throws Exception {
        CuentaBancaria cuenta = cuentaDAO.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            throw new Exception("Cuenta no encontrada.");
        }

        // Las cuentas de crédito no permiten depósitos, solo abonos.
        if (cuenta instanceof CuentaCredito) {
            throw new Exception("Use el método abonar para cuentas de crédito.");
        }

        cuenta.depositar(monto);
        cuentaDAO.actualizarSaldo(cuenta.getNumeroCuenta(), cuenta.getSaldo());
    }

    public void retirar(String numeroCuenta, double monto) throws Exception {
        CuentaBancaria cuenta = cuentaDAO.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            throw new Exception("Cuenta no encontrada.");
        }

        cuenta.retirar(monto);
        cuentaDAO.actualizarSaldo(cuenta.getNumeroCuenta(), cuenta.getSaldo());
    }

    public void abonar(String numeroCuenta, double monto) throws Exception {
        CuentaBancaria cuenta = cuentaDAO.buscarPorNumero(numeroCuenta);
        if (cuenta == null) {
            throw new Exception("Cuenta no encontrada.");
        }

        if (!(cuenta instanceof CuentaCredito)) {
            throw new Exception("Solo se puede abonar a cuentas de crédito.");
        }

        ((CuentaCredito) cuenta).abonar(monto);
        cuentaDAO.actualizarSaldo(cuenta.getNumeroCuenta(), cuenta.getSaldo());
    }

    public CuentaBancaria buscarCuenta(String numeroCuenta) throws SQLException {
        return cuentaDAO.buscarPorNumero(numeroCuenta);
    }

    public List<CuentaBancaria> listarCuentasPorCliente(String cedulaCliente) throws SQLException {
        return cuentaDAO.listarPorCliente(cedulaCliente);
    }

    public void cambiarEstadoCuenta(String numeroCuenta, boolean activa) throws SQLException {
        cuentaDAO.actualizarEstado(numeroCuenta, activa);
    }
}
