package BL;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaccion {
    private double monto;
    private Date fecha;
    private CuentaBancaria cuenta;

    public Transaccion(double monto, Date fecha, CuentaBancaria cuenta) {
        this.monto = monto;
        this.fecha = fecha;
        this.cuenta = cuenta;
    }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public CuentaBancaria getCuenta() { return cuenta; }
    public void setCuenta(CuentaBancaria cuenta) { this.cuenta = cuenta; }

    public String getResumen() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String tipo = monto >= 0 ? "Depósito" : "Retiro";
        return sdf.format(fecha) + " | " + tipo + " | ₡" + Math.abs(monto);
    }
}