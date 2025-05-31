import java.util.Date;
import java.util.Random;

public class CuentaBancaria {

    private String numeroCuenta;
    private double saldo;
    private String tipoCuenta; // Ej: Ahorro, Corriente
    private Date fechaCreacion;
    private cliente cliente;

    public CuentaBancaria(String tipoCuenta, cliente cliente) {
        this.numeroCuenta = generarNumeroCuenta();
        this.saldo = 0.0;
        this.tipoCuenta = tipoCuenta;
        this.fechaCreacion = new Date(); // fecha actual
        this.cliente = cliente;
    }

    private String generarNumeroCuenta() {
        Random rand = new Random();
        return "CR" + (10000000 + rand.nextInt(90000000)); // Ej: CR12345678
    }

    public void depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            System.out.println("Depósito exitoso. Nuevo saldo: ₡" + saldo);
        } else {
            System.out.println("Monto inválido.");
        }
    }

    public void retirar(double monto) {
        if (monto > 0 && monto <= saldo) {
            saldo -= monto;
            System.out.println("Retiro exitoso. Nuevo saldo: ₡" + saldo);
        } else {
            System.out.println("Fondos insuficientes o monto inválido.");
        }
    }

    public void mostrarInformacion() {
        System.out.println("=== Información de la Cuenta ===");
        System.out.println("Titular: " + cliente.getNombre() +" " + cliente.getApellido());
        System.out.println("Cédula: " + cliente.getCedula());
        System.out.println("Número de Cuenta: " + numeroCuenta);
        System.out.println("Tipo de Cuenta: " + tipoCuenta);
        System.out.println("Fecha de Creación: " + fechaCreacion);
        System.out.println("Saldo: ₡" + saldo);
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "CuentaBancaria{" +
                "numeroCuenta='" + numeroCuenta + '\'' +
                ", saldo=" + saldo +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", cliente=" + cliente +
                '}';
    }

}
