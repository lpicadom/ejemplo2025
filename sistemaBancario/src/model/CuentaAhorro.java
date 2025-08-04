package model;

public class CuentaAhorro extends CuentaBancaria {
    private double tasaInteres;

    public CuentaAhorro(String cedulaCliente, double montoInicial, double tasa) throws Exception {
        super(cedulaCliente);
        if (montoInicial < 100) {
            throw new Exception("La cuenta de ahorro requiere al menos $100.");
        }
        this.tipo = "Ahorro";
        this.saldo = montoInicial;
        this.tasaInteres = tasa;
        agregarTransaccion(montoInicial);
    }

    @Override
    public void depositar(double monto) throws Exception {
        if (!activa) throw new Exception("Cuenta inactiva.");
        if (monto <= 0) throw new Exception("Monto inválido.");
        saldo += monto;
        agregarTransaccion(monto);
    }

    @Override
    public void retirar(double monto) throws Exception {
        if (!activa) throw new Exception("Cuenta inactiva.");
        if (monto <= 0 || saldo - monto < 100)
            throw new Exception("No se puede retirar. Saldo mínimo: $100.");
        saldo -= monto;
        agregarTransaccion(-monto);
    }

    public void generarInteres() {
        saldo += saldo * tasaInteres;
    }
}
