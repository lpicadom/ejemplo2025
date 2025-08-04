package model;

import java.util.*;

public abstract class CuentaBancaria {
    protected String numeroCuenta;
    protected double saldo;
    protected boolean activa;
    protected Date fechaCreacion;
    protected String tipo;
    protected String cedulaCliente;

    protected List<Transaccion> transacciones;

    public CuentaBancaria(String cedulaCliente) {
        this.numeroCuenta = generarNumeroCuenta();
        this.saldo = 0;
        this.activa = true;
        this.fechaCreacion = new Date();
        this.transacciones = new ArrayList<>();
        this.cedulaCliente = cedulaCliente;
    }

    private String generarNumeroCuenta() {
        return "CR" + (10000000 + new Random().nextInt(90000000));
    }

    public abstract void depositar(double monto) throws Exception;
    public abstract void retirar(double monto) throws Exception;

    public void agregarTransaccion(double monto) {
        transacciones.add(new Transaccion(monto, new Date(), numeroCuenta));
    }

    // Getters y Setters
    public String getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }

    public Date getFechaCreacion() { return fechaCreacion; }

    public List<Transaccion> getTransacciones() { return transacciones; }

    public String getTipo() { return tipo; }

    public String getCedulaCliente() { return cedulaCliente; }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

}


