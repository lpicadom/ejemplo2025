package BL;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CuentaBancaria {
    private String numeroCuenta;
    private double saldo;
    private String tipoCuenta;
    private Date fechaCreacion;
    private Cliente cliente;

    private List<Transaccion> transacciones;

    public CuentaBancaria(String tipoCuenta, Cliente cliente) {
        this.numeroCuenta = generarNumeroCuenta();
        this.saldo = 0;
        this.tipoCuenta = tipoCuenta;
        this.fechaCreacion = new Date();
        this.cliente = cliente;
        this.transacciones = new ArrayList<>();
    }

    private String generarNumeroCuenta() {
        Random rand = new Random();
        return "CR" + (10000000 + rand.nextInt(90000000));
    }

    public void depositar(double monto) throws Exception {
        if (monto <= 0) throw new Exception("Monto inválido.");
        saldo += monto;
        transacciones.add(new Transaccion(monto, new Date(), this));
    }

    public void retirar(double monto) throws Exception {
        if (monto <= 0 || monto > saldo)
            throw new Exception("Fondos insuficientes o monto inválido.");
        saldo -= monto;
        transacciones.add(new Transaccion(-monto, new Date(), this));
    }

    public List<Transaccion> getTransacciones() {
        return transacciones;
    }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}