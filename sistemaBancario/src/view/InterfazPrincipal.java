package view;

import controller.ControladorPrincipal;
import model.Cliente;
import model.CuentaAhorro;
import model.CuentaBancaria;
import model.CuentaCredito;
import model.CuentaDebito;
import model.Transaccion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InterfazPrincipal extends JFrame {
    private ControladorPrincipal controlador;
    private JTabbedPane tabbedPane;
    private JPanel panelClientes;
    private JPanel panelCuentas;
    private JTextField txtCedula, txtNombre, txtApellido, txtCorreo, txtDireccion, txtSexo, txtProfesion;
    private JTextArea areaClientes;

    // Campos para la gestión de cuentas
    private JTextField txtCedulaClienteCuenta;
    private JComboBox<String> cmbTipoCuenta;
    private JTextField txtMontoInicial;
    private JTextField txtNumeroCuenta;
    private JTextField txtMontoOperacion;
    private JTextArea areaCuentaInfo;
    private JButton btnCrearCuenta, btnDepositar, btnRetirar, btnMostrarHistorial, btnListarCuentas;

    public InterfazPrincipal() {
        controlador = new ControladorPrincipal();
        setTitle("Sistema Bancario");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();
        panelClientes = crearPanelClientes();
        panelCuentas = crearPanelCuentas();

        tabbedPane.addTab("Clientes", panelClientes);
        tabbedPane.addTab("Cuentas", panelCuentas);

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel crearPanelClientes() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridLayout(8, 2));

        txtCedula = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtCorreo = new JTextField();
        txtDireccion = new JTextField();
        txtSexo = new JTextField();
        txtProfesion = new JTextField();

        form.add(new JLabel("Cédula:"));
        form.add(txtCedula);
        form.add(new JLabel("Nombre:"));
        form.add(txtNombre);
        form.add(new JLabel("Apellido:"));
        form.add(txtApellido);
        form.add(new JLabel("Correo:"));
        form.add(txtCorreo);
        form.add(new JLabel("Dirección:"));
        form.add(txtDireccion);
        form.add(new JLabel("Sexo:"));
        form.add(txtSexo);
        form.add(new JLabel("Profesión:"));
        form.add(txtProfesion);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnListar = new JButton("Listar");

        JPanel botones = new JPanel();
        botones.add(btnRegistrar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        botones.add(btnListar);

        areaClientes = new JTextArea(15, 40);
        areaClientes.setEditable(false);

        panel.add(form, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        panel.add(new JScrollPane(areaClientes), BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> {
            try {
                Cliente cliente = obtenerClienteDelFormulario();
                controlador.registrarCliente(cliente);
                mostrarMensaje("Cliente registrado con éxito.");
                limpiarFormularioClientes();
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnModificar.addActionListener(e -> {
            try {
                Cliente cliente = obtenerClienteDelFormulario();
                controlador.modificarCliente(cliente);
                mostrarMensaje("Cliente modificado con éxito.");
                limpiarFormularioClientes();
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                String cedula = txtCedula.getText();
                if (cedula.isEmpty()) throw new Exception("Debe ingresar la cédula.");
                controlador.eliminarCliente(cedula);
                mostrarMensaje("Cliente eliminado.");
                limpiarFormularioClientes();
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnListar.addActionListener(e -> {
            try {
                List<Cliente> lista = controlador.listarClientes();
                areaClientes.setText("");
                for (Cliente c : lista) {
                    areaClientes.append(c.getCedula() + " | " + c.getNombre() + " " + c.getApellido() + "\n");
                }
            } catch (Exception ex) {
                mostrarError("Error al listar: " + ex.getMessage());
            }
        });

        return panel;
    }

    private JPanel crearPanelCuentas() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel crearCuentaPanel = new JPanel(new GridLayout(4, 2));
        txtCedulaClienteCuenta = new JTextField();
        cmbTipoCuenta = new JComboBox<>(new String[]{"Ahorro", "Debito", "Credito"});
        txtMontoInicial = new JTextField();

        crearCuentaPanel.add(new JLabel("Cédula cliente:"));
        crearCuentaPanel.add(txtCedulaClienteCuenta);
        crearCuentaPanel.add(new JLabel("Tipo de cuenta:"));
        crearCuentaPanel.add(cmbTipoCuenta);
        crearCuentaPanel.add(new JLabel("Monto inicial:"));
        crearCuentaPanel.add(txtMontoInicial);

        btnCrearCuenta = new JButton("Crear Cuenta");
        crearCuentaPanel.add(new JLabel(""));
        crearCuentaPanel.add(btnCrearCuenta);

        JPanel operacionesPanel = new JPanel(new GridLayout(4, 2));
        txtNumeroCuenta = new JTextField();
        txtMontoOperacion = new JTextField();

        operacionesPanel.add(new JLabel("Número de cuenta:"));
        operacionesPanel.add(txtNumeroCuenta);
        operacionesPanel.add(new JLabel("Monto operación:"));
        operacionesPanel.add(txtMontoOperacion);

        btnDepositar = new JButton("Depositar");
        btnRetirar = new JButton("Retirar");
        btnMostrarHistorial = new JButton("Mostrar Historial");
        btnListarCuentas = new JButton("Listar Cuentas del Cliente");

        operacionesPanel.add(btnDepositar);
        operacionesPanel.add(btnRetirar);
        operacionesPanel.add(btnMostrarHistorial);
        operacionesPanel.add(btnListarCuentas);

        areaCuentaInfo = new JTextArea(10, 40);
        areaCuentaInfo.setEditable(false);

        panel.add(crearCuentaPanel, BorderLayout.NORTH);
        panel.add(operacionesPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(areaCuentaInfo), BorderLayout.SOUTH);

        btnCrearCuenta.addActionListener(e -> {
            try {
                String cedula = txtCedulaClienteCuenta.getText();
                String tipo = (String) cmbTipoCuenta.getSelectedItem();
                double monto = Double.parseDouble(txtMontoInicial.getText());

                CuentaBancaria nuevaCuenta;
                if (tipo.equals("Ahorro")) {
                    nuevaCuenta = new CuentaAhorro(cedula, monto, 0.03);
                } else if (tipo.equals("Debito")) {
                    nuevaCuenta = new CuentaDebito(cedula, monto, 0.01);
                } else if (tipo.equals("Credito")) {
                    nuevaCuenta = new CuentaCredito(cedula, monto);
                } else {
                    throw new Exception("Tipo de cuenta no válido.");
                }

                controlador.registrarCuenta(nuevaCuenta);
                mostrarMensaje("Cuenta " + tipo + " creada con éxito. Número: " + nuevaCuenta.getNumeroCuenta());
                limpiarFormularioCuentas();
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnDepositar.addActionListener(e -> {
            try {
                String numeroCuenta = txtNumeroCuenta.getText();
                double monto = Double.parseDouble(txtMontoOperacion.getText());
                CuentaBancaria cuenta = controlador.buscarCuenta(numeroCuenta);

                if (cuenta == null) {
                    throw new Exception("Cuenta no encontrada.");
                }

                // Las cuentas de crédito tienen un método 'abonar' en lugar de 'depositar'
                if (cuenta instanceof CuentaCredito) {
                    ((CuentaCredito) cuenta).abonar(monto);
                } else {
                    cuenta.depositar(monto);
                }

                mostrarMensaje("Operación exitosa. Saldo actual: " + cuenta.getSaldo());
                limpiarFormularioOperaciones();
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnRetirar.addActionListener(e -> {
            try {
                String numeroCuenta = txtNumeroCuenta.getText();
                double monto = Double.parseDouble(txtMontoOperacion.getText());
                CuentaBancaria cuenta = controlador.buscarCuenta(numeroCuenta);

                if (cuenta == null) {
                    throw new Exception("Cuenta no encontrada.");
                }

                cuenta.retirar(monto);
                mostrarMensaje("Operación exitosa. Saldo actual: " + cuenta.getSaldo());
                limpiarFormularioOperaciones();
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        btnListarCuentas.addActionListener(e -> {
            try {
                String cedula = txtCedulaClienteCuenta.getText();
                if (cedula.isEmpty()) throw new Exception("Debe ingresar la cédula del cliente.");

                List<CuentaBancaria> cuentas = controlador.listarCuentasPorCliente(cedula);
                areaCuentaInfo.setText("Cuentas del cliente " + cedula + ":\n");
                for (CuentaBancaria c : cuentas) {
                    areaCuentaInfo.append("Número: " + c.getNumeroCuenta() + " | Tipo: " + c.getTipo() + " | Saldo: " + c.getSaldo() + "\n");
                }
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        return panel;
    }

    private Cliente obtenerClienteDelFormulario() throws Exception {
        if (txtCedula.getText().isEmpty()) throw new Exception("Cédula requerida.");
        Cliente c = new Cliente();
        c.setCedula(txtCedula.getText());
        c.setNombre(txtNombre.getText());
        c.setApellido(txtApellido.getText());
        c.setCorreoElectronico(txtCorreo.getText());
        c.setDireccion(txtDireccion.getText());
        c.setSexo(txtSexo.getText());
        c.setProfesion(txtProfesion.getText());
        return c;
    }

    private void limpiarFormularioClientes() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtSexo.setText("");
        txtProfesion.setText("");
    }

    private void limpiarFormularioCuentas() {
        txtCedulaClienteCuenta.setText("");
        txtMontoInicial.setText("");
    }

    private void limpiarFormularioOperaciones() {
        txtNumeroCuenta.setText("");
        txtMontoOperacion.setText("");
    }

    private void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}