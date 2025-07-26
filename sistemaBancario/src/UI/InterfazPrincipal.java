package UI;

import BL.*;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class InterfazPrincipal extends JFrame {
    private GestorCliente gestor = new GestorCliente();
    private DefaultListModel<CuentaBancaria> cuentas = new DefaultListModel<>();

    // COMPONENTES CLIENTE
    private JTextField txtNombre, txtApellido, txtCedula, txtDireccion, txtCorreo;
    private JTextArea areaListadoClientes;

    // COMPONENTES CUENTA
    private JTextField txtCedulaBuscar, txtTipoCuenta, txtMontoInicial;
    private JTextField txtCuentaBuscar, txtMontoOperacion;
    private JTextArea areaCuentaInfo;

    public InterfazPrincipal() {
        setTitle("Sistema Bancario");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Clientes", crearPanelClientes());
        tabs.addTab("Cuentas Bancarias", crearPanelCuentas());

        add(tabs);
        setVisible(true);
    }

    private JPanel crearPanelClientes() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(6, 2));
        txtNombre = new JTextField(); txtApellido = new JTextField();
        txtCedula = new JTextField(); txtDireccion = new JTextField();
        txtCorreo = new JTextField();

        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Apellido:")); form.add(txtApellido);
        form.add(new JLabel("Cédula:")); form.add(txtCedula);
        form.add(new JLabel("Dirección:")); form.add(txtDireccion);
        form.add(new JLabel("Correo electrónico:")); form.add(txtCorreo);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnListar = new JButton("Listar");

        JPanel botones = new JPanel();
        botones.add(btnRegistrar); botones.add(btnModificar);
        botones.add(btnEliminar); botones.add(btnListar);

        areaListadoClientes = new JTextArea(12, 40);
        areaListadoClientes.setEditable(false);

        panel.add(form, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        panel.add(new JScrollPane(areaListadoClientes), BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> {
            try {
                Cliente c = getClienteFromForm();
                gestor.registrarCliente(c);
                mostrarMensaje("Cliente registrado correctamente.");
                limpiarFormulario();
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnModificar.addActionListener(e -> {
            try {
                Cliente c = getClienteFromForm();
                gestor.modificarCliente(c.getCedula(), c);
                mostrarMensaje("Cliente modificado correctamente.");
                limpiarFormulario();
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                gestor.eliminarCliente(txtCedula.getText());
                mostrarMensaje("Cliente eliminado correctamente.");
                limpiarFormulario();
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnListar.addActionListener(e -> {
            areaListadoClientes.setText("");
            for (Cliente c : gestor.listarClientes()) {
                areaListadoClientes.append(c.toString() + "\n");
                // Mostrar cuentas del cliente con saldo
                for (int i = 0; i < cuentas.size(); i++) {
                    CuentaBancaria cuenta = cuentas.get(i);
                    if (cuenta.getCliente().equals(c)) {
                        areaListadoClientes.append("    Cuenta: " + cuenta.getNumeroCuenta() +
                                " | Tipo: " + cuenta.getTipoCuenta() +
                                " | Saldo: ₡" + cuenta.getSaldo() + "\n");
                    }
                }
                areaListadoClientes.append("\n");
            }
        });

        return panel;
    }

    private JPanel crearPanelCuentas() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel crearCuentaPanel = new JPanel(new GridLayout(4, 2));
        txtCedulaBuscar = new JTextField();
        txtTipoCuenta = new JTextField();
        txtMontoInicial = new JTextField();

        crearCuentaPanel.add(new JLabel("Cédula cliente:")); crearCuentaPanel.add(txtCedulaBuscar);
        crearCuentaPanel.add(new JLabel("Tipo de cuenta:")); crearCuentaPanel.add(txtTipoCuenta);
        crearCuentaPanel.add(new JLabel("Monto inicial:")); crearCuentaPanel.add(txtMontoInicial);

        JButton btnCrearCuenta = new JButton("Crear Cuenta");
        crearCuentaPanel.add(new JLabel("")); crearCuentaPanel.add(btnCrearCuenta);

        JPanel operacionesPanel = new JPanel(new GridLayout(4, 2));
        txtCuentaBuscar = new JTextField(); txtMontoOperacion = new JTextField();

        operacionesPanel.add(new JLabel("Número de cuenta:")); operacionesPanel.add(txtCuentaBuscar);
        operacionesPanel.add(new JLabel("Monto operación:")); operacionesPanel.add(txtMontoOperacion);

        JButton btnDepositar = new JButton("Depositar");
        JButton btnRetirar = new JButton("Retirar");
        JButton btnMostrarHistorial = new JButton("Mostrar Historial");

        operacionesPanel.add(btnDepositar); operacionesPanel.add(btnRetirar);
        operacionesPanel.add(new JLabel("")); operacionesPanel.add(btnMostrarHistorial);

        areaCuentaInfo = new JTextArea(10, 40);
        areaCuentaInfo.setEditable(false);

        panel.add(crearCuentaPanel, BorderLayout.NORTH);
        panel.add(operacionesPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(areaCuentaInfo), BorderLayout.SOUTH);

        btnCrearCuenta.addActionListener(e -> {
            try {
                Cliente c = gestor.buscarCliente(txtCedulaBuscar.getText());
                if (c == null) throw new Exception("Cliente no encontrado.");
                CuentaBancaria cuenta = new CuentaBancaria(txtTipoCuenta.getText(), c);
                double monto = Double.parseDouble(txtMontoInicial.getText());
                cuenta.depositar(monto);
                cuentas.addElement(cuenta);
                mostrarMensaje("Cuenta creada: " + cuenta.getNumeroCuenta());
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnDepositar.addActionListener(e -> {
            try {
                CuentaBancaria c = buscarCuenta(txtCuentaBuscar.getText());
                if (c == null) throw new Exception("Cuenta no encontrada.");
                c.depositar(Double.parseDouble(txtMontoOperacion.getText()));
                mostrarMensaje("Depósito exitoso. Saldo: ₡" + c.getSaldo());
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnRetirar.addActionListener(e -> {
            try {
                CuentaBancaria c = buscarCuenta(txtCuentaBuscar.getText());
                if (c == null) throw new Exception("Cuenta no encontrada.");
                c.retirar(Double.parseDouble(txtMontoOperacion.getText()));
                mostrarMensaje("Retiro exitoso. Saldo: ₡" + c.getSaldo());
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnMostrarHistorial.addActionListener(e -> {
            try {
                CuentaBancaria c = buscarCuenta(txtCuentaBuscar.getText());
                if (c == null) throw new Exception("Cuenta no encontrada.");
                areaCuentaInfo.setText("Historial de transacciones cuenta " + c.getNumeroCuenta() + ":\n");
                for (Transaccion t : c.getTransacciones()) {
                    areaCuentaInfo.append(t.getResumen() + "\n");
                }
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        return panel;
    }

    private Cliente getClienteFromForm() {
        Cliente c = new Cliente();
        c.setNombre(txtNombre.getText());
        c.setApellido(txtApellido.getText());
        c.setCedula(txtCedula.getText());
        c.setDireccion(txtDireccion.getText());
        c.setCorreoElectronico(txtCorreo.getText());
        return c;
    }

    private void limpiarFormulario() {
        txtNombre.setText(""); txtApellido.setText(""); txtCedula.setText("");
        txtDireccion.setText(""); txtCorreo.setText("");
    }

    private CuentaBancaria buscarCuenta(String numero) {
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getNumeroCuenta().equals(numero)) return cuentas.get(i);
        }
        return null;
    }

    private void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}