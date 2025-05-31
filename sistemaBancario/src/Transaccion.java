import java.util.Date;

public class Transaccion {

    private String id;
    private String tipo; // Ej. "Depósito" o "Retiro"
    private double monto;
    private Date fecha;
    private CuentaBancaria cuenta; // Asociación con la cuenta


    public Transaccion(String id, String tipo, double monto, Date fecha, CuentaBancaria cuenta) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
        this.cuenta = cuenta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public CuentaBancaria getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaBancaria cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", cuenta=" + cuenta +
                '}';
    }
}
