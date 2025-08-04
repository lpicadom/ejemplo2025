package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaccion {
    private double monto;
    private Date fecha;
    private String numeroCuenta;

    public Transaccion(double monto, Date fecha, String numeroCuenta) {
        this.monto = monto;
        this.fecha = fecha;
        this.numeroCuenta = numeroCuenta;
    }

    public double getMonto() { return monto; }
    public Date getFecha() { return fecha; }
    public String getNumeroCuenta() { return numeroCuenta; }

    public String getResumen() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String tipo = monto >= 0 ? "Abono/Depósito" : "Retiro";
        return sdf.format(fecha) + " | " + tipo + " | Monto: ₡" + Math.abs(monto);
    }
}
