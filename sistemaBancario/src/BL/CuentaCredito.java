package BL;

public class CuentaCredito extends CuentaBancaria {
    private double limiteCredito;

    public CuentaCredito(Cliente cliente, double limiteCredito) throws Exception {
        super(cliente);
        if (limiteCredito <= 0) throw new Exception("Límite de crédito inválido.");
        this.limiteCredito = limiteCredito;
        setSaldo(0);
    }

    @Override
    public void depositar(double monto) throws Exception {
        throw new Exception("Las cuentas de crédito no aceptan depósitos, solo abonos.");
    }

    public void abonar(double monto) throws Exception {
        if (!isActiva()) throw new Exception("Cuenta inactiva.");
        if (monto <= 0) throw new Exception("Monto inválido.");
        if (getSaldo() + monto > 0) throw new Exception("No se permite saldo positivo.");
        setSaldo(getSaldo() + monto);
        agregarTransaccion(monto);
    }

    @Override
    public void retirar(double monto) throws Exception {
        if (!isActiva()) throw new Exception("Cuenta inactiva.");
        if (monto <= 0 || getSaldo() - monto < -limiteCredito)
            throw new Exception("Límite de crédito excedido.");
        setSaldo(getSaldo() - monto);
        agregarTransaccion(-monto);
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    @Override
    public String getTipoCuenta() {
        return "Crédito";
    }
}
