package model;

public class CuentaDebito extends CuentaBancaria {
    private double tasaInteres;

    public CuentaDebito(String cedulaCliente, double montoInicial, double tasa) throws Exception {
        super(cedulaCliente);
        if (montoInicial < 0) throw new Exception("El saldo inicial no puede ser negativo.");
        this.tipo = "Débito";
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
        if (monto <= 0 || saldo - monto < 0)
            throw new Exception("Saldo insuficiente.");
        saldo -= monto;
        agregarTransaccion(-monto);
    }

    public void generarInteres() {
        saldo += saldo * tasaInteres;
    }
}