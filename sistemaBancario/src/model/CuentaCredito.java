package model;

public class CuentaCredito extends CuentaBancaria {
    private double limiteCredito;

    public CuentaCredito(String cedulaCliente, double limiteCredito) throws Exception {
        super(cedulaCliente);
        if (limiteCredito <= 0) throw new Exception("El límite de crédito debe ser positivo.");
        this.tipo = "Crédito";
        this.limiteCredito = limiteCredito;
        this.saldo = 0;
    }

    @Override
    public void depositar(double monto) throws Exception {
        throw new Exception("Las cuentas de crédito no permiten depósitos, solo abonos.");
    }

    public void abonar(double monto) throws Exception {
        if (!activa) throw new Exception("Cuenta inactiva.");
        if (monto <= 0 || saldo + monto > 0)
            throw new Exception("No se puede superar saldo cero en una cuenta de crédito.");
        saldo += monto;
        agregarTransaccion(monto);
    }

    @Override
    public void retirar(double monto) throws Exception {
        if (!activa) throw new Exception("Cuenta inactiva.");
        if (monto <= 0 || saldo - monto < -limiteCredito)
            throw new Exception("Límite de crédito excedido.");
        saldo -= monto;
        agregarTransaccion(-monto);
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }
}