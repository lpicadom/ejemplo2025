package BL;

public class CuentaDebito extends CuentaBancaria {
    private double tasaInteres;

    public CuentaDebito(Cliente cliente, double montoInicial) throws Exception {
        super(cliente);
        if (montoInicial < 0) throw new Exception("Monto inicial no puede ser negativo.");
        setSaldo(montoInicial);
        tasaInteres = Configuracion.obtenerTasaDebito();
        agregarTransaccion(montoInicial);
    }

    @Override
    public void depositar(double monto) throws Exception {
        if (!isActiva()) throw new Exception("Cuenta inactiva.");
        if (monto <= 0) throw new Exception("Monto inválido.");
        setSaldo(getSaldo() + monto);
        agregarTransaccion(monto);
    }

    @Override
    public void retirar(double monto) throws Exception {
        if (!isActiva()) throw new Exception("Cuenta inactiva.");
        if (monto <= 0 || getSaldo() - monto < 0)
            throw new Exception("No se permite saldo negativo.");
        setSaldo(getSaldo() - monto);
        agregarTransaccion(-monto);
    }

    public void generarInteres() {
        setSaldo(getSaldo() + getSaldo() * tasaInteres);
    }

    @Override
    public String getTipoCuenta() {
        return "Débito";
    }
}
