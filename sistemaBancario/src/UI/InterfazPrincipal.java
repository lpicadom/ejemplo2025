package UI;

import BL.*;
import javax.swing.*;
import java.awt.*;

public class InterfazPrincipal extends JFrame {
    private GestorCliente gestor = new GestorCliente();
    private DefaultListModel<CuentaBancaria> cuentas = new DefaultListModel<>();
    private static Administrador administrador = null;

    // COMPONENTES ADMINISTRADOR
    private JTextField txtAdminNombre, txtAdminApellido, txtAdminCedula, txtAdminCorreo;

    // COMPONENTES CLIENTE
    private JTextField txtNombre, txtApellido, txtCedula, txtDireccion, txtCorreo;
    private JTextArea areaListadoClientes;

    // COMPONENTES CUENTA
    private JTextField txtCedulaBuscar, txtMontoInicial;
    private JComboBox<String> cbTipoCuenta;
    private JCheckBox chkActiva;
    private JTextField txtCuentaBuscar, txtMontoOperacion;
    private JTextArea areaCuentaInfo;

    public InterfazPrincipal() {
        setTitle("Sistema Bancario");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Administrador", crearPanelAdministrador());
        tabs.addTab("Clientes", crearPanelClientes());
        tabs.addTab("Cuentas Bancarias", crearPanelCuentas());

        if (administrador == null) {
            tabs.setEnabledAt(1, false);
            tabs.setEnabledAt(2, false);
        }

        add(tabs);
        setVisible(true);
    }

    private JPanel crearPanelAdministrador() {
        JPanel panel = new JPanel(new GridLayout(6, 2));

        txtAdminNombre = new JTextField(); txtAdminApellido = new JTextField();
        txtAdminCedula = new JTextField(); txtAdminCorreo = new JTextField();

        panel.add(new JLabel("Nombre:")); panel.add(txtAdminNombre);
        panel.add(new JLabel("Apellido:")); panel.add(txtAdminApellido);
        panel.add(new JLabel("Cédula:")); panel.add(txtAdminCedula);
        panel.add(new JLabel("Correo electrónico:")); panel.add(txtAdminCorreo);

        JButton btnCrearAdmin = new JButton("Registrar Administrador");
        panel.add(new JLabel("")); panel.add(btnCrearAdmin);

        btnCrearAdmin.addActionListener(e -> {
            if (administrador != null) {
                mostrarError("Ya existe un administrador.");
                return;
            }
            administrador = new Administrador();
            administrador.setIdAdministrador(1);
            mostrarMensaje("Administrador registrado. Ya puede registrar clientes y cuentas.");
            dispose();
            new InterfazPrincipal();
        });

        return panel;
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
        JButton btnReporte = new JButton("Reporte por Cliente");

        JPanel botones = new JPanel();
        botones.add(btnRegistrar); botones.add(btnModificar);
        botones.add(btnEliminar); botones.add(btnListar);
        botones.add(btnReporte);

        areaListadoClientes = new JTextArea(12, 40);
        areaListadoClientes.setEditable(false);

        panel.add(form, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        panel.add(new JScrollPane(areaListadoClientes), BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> {
            try {
                if (administrador == null) throw new Exception("Primero debe crear un administrador.");
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
                for (int i = 0; i < cuentas.size(); i++) {
                    CuentaBancaria cuenta = cuentas.get(i);
                    if (cuenta.getCliente().equals(c)) {
                        areaListadoClientes.append("    Cuenta: " + cuenta.getNumeroCuenta() +
                                " | Tipo: " + cuenta.getTipoCuenta() +
                                " | Saldo: ₡" + cuenta.getSaldo() +
                                " | Activa: " + cuenta.isActiva() + "\n");
                    }
                }
                areaListadoClientes.append("\n");
            }
        });

        btnReporte.addActionListener(e -> {
            String cedula = JOptionPane.showInputDialog("Ingrese cédula del cliente:");
            Cliente c = gestor.buscarCliente(cedula);
            if (c == null) {
                mostrarError("Cliente no encontrado.");
                return;
            }
            StringBuilder reporte = new StringBuilder("Reporte de cuentas:\n");
            for (int i = 0; i < cuentas.size(); i++) {
                CuentaBancaria cuenta = cuentas.get(i);
                if (cuenta.getCliente().equals(c)) {
                    reporte.append("Cuenta: " + cuenta.getNumeroCuenta() +
                            " | Tipo: " + cuenta.getTipoCuenta() +
                            " | Saldo: ₡" + cuenta.getSaldo() +
                            " | Activa: " + cuenta.isActiva() + "\n");
                }
            }
            mostrarMensaje(reporte.toString());
        });

        return panel;
    }

    private JPanel crearPanelCuentas() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel crearCuentaPanel = new JPanel(new GridLayout(5, 2));
        txtCedulaBuscar = new JTextField();
        cbTipoCuenta = new JComboBox<>(new String[]{"Ahorro", "Débito", "Crédito"});
        txtMontoInicial = new JTextField();
        chkActiva = new JCheckBox("Cuenta activa", true);

        crearCuentaPanel.add(new JLabel("Cédula cliente:")); crearCuentaPanel.add(txtCedulaBuscar);
        crearCuentaPanel.add(new JLabel("Tipo de cuenta:")); crearCuentaPanel.add(cbTipoCuenta);
        crearCuentaPanel.add(new JLabel("Monto inicial o límite:")); crearCuentaPanel.add(txtMontoInicial);
        crearCuentaPanel.add(new JLabel("")); crearCuentaPanel.add(chkActiva);

        JButton btnCrearCuenta = new JButton("Crear Cuenta");
        crearCuentaPanel.add(new JLabel("")); crearCuentaPanel.add(btnCrearCuenta);

        JPanel operacionesPanel = new JPanel(new GridLayout(4, 2));
        txtCuentaBuscar = new JTextField(); txtMontoOperacion = new JTextField();

        operacionesPanel.add(new JLabel("Número de cuenta:")); operacionesPanel.add(txtCuentaBuscar);
        operacionesPanel.add(new JLabel("Monto operación:")); operacionesPanel.add(txtMontoOperacion);

        JButton btnDepositar = new JButton("Depositar / Abonar");
        JButton btnRetirar = new JButton("Retirar");
        JButton btnMostrarHistorial = new JButton("Mostrar Historial");
        JButton btnCambiarEstado = new JButton("Activar/Inactivar");

        operacionesPanel.add(btnDepositar); operacionesPanel.add(btnRetirar);
        operacionesPanel.add(btnMostrarHistorial); operacionesPanel.add(btnCambiarEstado);

        areaCuentaInfo = new JTextArea(10, 40);
        areaCuentaInfo.setEditable(false);

        panel.add(crearCuentaPanel, BorderLayout.NORTH);
        panel.add(operacionesPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(areaCuentaInfo), BorderLayout.SOUTH);

        btnCrearCuenta.addActionListener(e -> {
            try {
                Cliente c = gestor.buscarCliente(txtCedulaBuscar.getText());
                if (c == null) throw new Exception("Cliente no encontrado.");
                String tipo = cbTipoCuenta.getSelectedItem().toString();
                double monto = Double.parseDouble(txtMontoInicial.getText());
                CuentaBancaria cuenta;
                switch (tipo) {
                    case "Ahorro": cuenta = new CuentaAhorro(c, monto); break;
                    case "Débito": cuenta = new CuentaDebito(c, monto); break;
                    case "Crédito": cuenta = new CuentaCredito(c, monto); break;
                    default: throw new Exception("Tipo inválido.");
                }
                cuenta.setActiva(chkActiva.isSelected());
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
                double monto = Double.parseDouble(txtMontoOperacion.getText());
                if (c instanceof CuentaCredito) {
                    ((CuentaCredito) c).abonar(monto);
                } else {
                    c.depositar(monto);
                }
                mostrarMensaje("Operación exitosa. Saldo: " + c.getSaldo());
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnRetirar.addActionListener(e -> {
            try {
                CuentaBancaria c = buscarCuenta(txtCuentaBuscar.getText());
                if (c == null) throw new Exception("Cuenta no encontrada.");
                c.retirar(Double.parseDouble(txtMontoOperacion.getText()));
                mostrarMensaje("Retiro exitoso. Saldo: " + c.getSaldo());
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnMostrarHistorial.addActionListener(e -> {
            try {
                CuentaBancaria c = buscarCuenta(txtCuentaBuscar.getText());
                if (c == null) throw new Exception("Cuenta no encontrada.");
                areaCuentaInfo.setText("Cuenta " + c.getNumeroCuenta() +
                        " | Tipo: " + c.getTipoCuenta() +
                        " | Activa: " + c.isActiva() +
                        " | Saldo: ₡" + c.getSaldo() + "\n");
                for (Transaccion t : c.getTransacciones()) {
                    areaCuentaInfo.append(t.getResumen() + "\n");
                }
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnCambiarEstado.addActionListener(e -> {
            CuentaBancaria c = buscarCuenta(txtCuentaBuscar.getText());
            if (c == null) {
                mostrarError("Cuenta no encontrada.");
                return;
            }
            c.setActiva(!c.isActiva());
            mostrarMensaje("Estado cambiado. Ahora está " + (c.isActiva() ? "activa" : "inactiva"));
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