package BL;

import java.util.*;

public abstract class CuentaBancaria {
    private String numeroCuenta;
    private double saldo;
    private boolean activa;
    private Date fechaCreacion;
    private Cliente cliente;
    private List<Transaccion> transacciones;

    public CuentaBancaria(Cliente cliente) {
        this.numeroCuenta = generarNumeroCuenta();
        this.saldo = 0;
        this.activa = true;
        this.fechaCreacion = new Date();
        this.cliente = cliente;
        this.transacciones = new ArrayList<>();
    }

    private String generarNumeroCuenta() {
        return "CR" + (10000000 + new Random().nextInt(90000000));
    }

    public abstract void depositar(double monto) throws Exception;
    public abstract void retirar(double monto) throws Exception;

    public void agregarTransaccion(double monto) {
        transacciones.add(new Transaccion(monto, new Date(), this));
    }

    // Getters y Setters
    public String getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
    public Cliente getCliente() { return cliente; }
    public List<Transaccion> getTransacciones() { return transacciones; }

    public abstract String getTipoCuenta();
}