package BL;

public class CuentaAhorro extends CuentaBancaria {
    private double tasaInteres;

    public CuentaAhorro(Cliente cliente, double montoInicial) throws Exception {
        super(cliente);
        if (montoInicial < 100) throw new Exception("La cuenta de ahorro requiere al menos $100.");
        setSaldo(montoInicial);
        tasaInteres = Configuracion.obtenerTasaAhorro(); // Desde archivo
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
        if (monto <= 0 || getSaldo() - monto < 100) throw new Exception("Saldo insuficiente.");
        setSaldo(getSaldo() - monto);
        agregarTransaccion(-monto);
    }

    public void generarInteres() {
        setSaldo(getSaldo() + getSaldo() * tasaInteres);
    }

    @Override
    public String getTipoCuenta() {
        return "Ahorro";
    }
}
