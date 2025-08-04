package view;

import controller.ControladorPrincipal;
import model.Administrador;
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

        // Menú principal
        JMenuBar menuBar = new JMenuBar();
        JMenu menuAdmin = new JMenu("Administrador");
        JMenuItem miRegistrarAdmin = new JMenuItem("Registrar Administrador");
        menuAdmin.add(miRegistrarAdmin);

        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem miListarClientes = new JMenuItem("Listar todos los Clientes");
        JMenuItem miListarCuentas = new JMenuItem("Listar todas las Cuentas");
        JMenuItem miReporteCliente = new JMenuItem("Reporte de Cuentas por Cliente");
        menuReportes.add(miListarClientes);
        menuReportes.add(miListarCuentas);
        menuReportes.add(miReporteCliente);

        menuBar.add(menuAdmin);
        menuBar.add(menuReportes);
        setJMenuBar(menuBar);

        add(tabbedPane);
        setVisible(true);

        // ActionListeners para el menú
        miRegistrarAdmin.addActionListener(e -> {
            try {
                if (controlador.obtenerAdministrador() != null) {
                    mostrarError("Ya existe un administrador registrado.");
                } else {
                    registrarAdministrador();
                }
            } catch (Exception ex) {
                mostrarError("Error al verificar administrador: " + ex.getMessage());
            }
        });

        miListarClientes.addActionListener(e -> listarTodosLosClientes());

        miListarCuentas.addActionListener(e -> listarTodasLasCuentas());

        miReporteCliente.addActionListener(e -> generarReporteCliente());
    }

    private void registrarAdministrador() {
        JDialog dialog = new JDialog(this, "Registrar Administrador", true);
        dialog.setLayout(new GridLayout(5, 2));
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);

        JTextField txtCedulaAdmin = new JTextField();
        JTextField txtNombreAdmin = new JTextField();
        JTextField txtApellidoAdmin = new JTextField();
        JTextField txtCorreoAdmin = new JTextField();
        JButton btnGuardarAdmin = new JButton("Guardar");

        dialog.add(new JLabel("Cédula:"));
        dialog.add(txtCedulaAdmin);
        dialog.add(new JLabel("Nombre:"));
        dialog.add(txtNombreAdmin);
        dialog.add(new JLabel("Apellido:"));
        dialog.add(txtApellidoAdmin);
        dialog.add(new JLabel("Correo:"));
        dialog.add(txtCorreoAdmin);
        dialog.add(btnGuardarAdmin);

        btnGuardarAdmin.addActionListener(e -> {
            try {
                Administrador admin = new Administrador(
                        txtCedulaAdmin.getText(),
                        txtNombreAdmin.getText(),
                        txtApellidoAdmin.getText(),
                        txtCorreoAdmin.getText()
                );
                controlador.registrarAdministrador(admin);
                mostrarMensaje("Administrador registrado con éxito.");
                dialog.dispose();
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });

        dialog.setVisible(true);
    }

    private void listarTodosLosClientes() {
        try {
            List<Cliente> lista = controlador.listarClientes();
            areaClientes.setText("Lista de todos los Clientes:\n\n");
            for (Cliente c : lista) {
                areaClientes.append("Cédula: " + c.getCedula() + " | Nombre: " + c.getNombre() + " " + c.getApellido() + "\n");
            }
        } catch (Exception ex) {
            mostrarError("Error al listar clientes: " + ex.getMessage());
        }
    }

    private void listarTodasLasCuentas() {
        try {
            List<CuentaBancaria> cuentas = controlador.listarTodasLasCuentas();
            areaCuentaInfo.setText("Lista de todas las Cuentas Bancarias:\n\n");
            for (CuentaBancaria c : cuentas) {
                areaCuentaInfo.append("Número: " + c.getNumeroCuenta() + " | Tipo: " + c.getTipo() + " | Saldo: " + c.getSaldo() + " | Cliente: " + c.getCedulaCliente() + "\n");
            }
        } catch (Exception ex) {
            mostrarError("Error al listar cuentas: " + ex.getMessage());
        }
    }

    private void generarReporteCliente() {
        String cedula = JOptionPane.showInputDialog(this, "Ingrese la cédula del cliente para el reporte:");
        if (cedula != null && !cedula.isEmpty()) {
            try {
                List<CuentaBancaria> cuentas = controlador.listarCuentasPorCliente(cedula);
                areaCuentaInfo.setText("Reporte de cuentas para el cliente " + cedula + ":\n\n");
                for (CuentaBancaria c : cuentas) {
                    areaCuentaInfo.append("--- Cuenta " + c.getTipo() + " ---\n");
                    areaCuentaInfo.append("Número: " + c.getNumeroCuenta() + "\n");
                    areaCuentaInfo.append("Saldo: " + c.getSaldo() + "\n");
                    areaCuentaInfo.append("Estado: " + (c.isActiva() ? "Activa" : "Inactiva") + "\n");
                    areaCuentaInfo.append("Fecha de Creación: " + c.getFechaCreacion() + "\n");
                    if (c instanceof CuentaCredito) {
                        areaCuentaInfo.append("Límite de Crédito: " + ((CuentaCredito) c).getLimiteCredito() + "\n");
                    }
                    areaCuentaInfo.append("-----------------------------\n");
                }
            } catch (Exception ex) {
                mostrarError("Error al generar reporte: " + ex.getMessage());
            }
        }
    }

    private JPanel crearPanelClientes() {
        // ... (el código para crear el panel de clientes es el mismo)
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
        // ... (el código para crear el panel de cuentas es el mismo)
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
                controlador.depositar(numeroCuenta, monto);
                mostrarMensaje("Depósito exitoso.");
                refrescarListaCuentas(txtCedulaClienteCuenta.getText());
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });
        btnRetirar.addActionListener(e -> {
            try {
                String numeroCuenta = txtNumeroCuenta.getText();
                double monto = Double.parseDouble(txtMontoOperacion.getText());
                controlador.retirar(numeroCuenta, monto);
                mostrarMensaje("Retiro exitoso.");
                refrescarListaCuentas(txtCedulaClienteCuenta.getText());
            } catch (Exception ex) {
                mostrarError(ex.getMessage());
            }
        });
        btnMostrarHistorial.addActionListener(e -> {
            try {
                String numeroCuenta = txtNumeroCuenta.getText();
                if (numeroCuenta.isEmpty()) throw new Exception("Debe ingresar el número de cuenta.");

                List<Transaccion> historial = controlador.obtenerHistorialTransacciones(numeroCuenta);
                areaCuentaInfo.setText("Historial de transacciones de la cuenta " + numeroCuenta + ":\n");
                for (Transaccion t : historial) {
                    areaCuentaInfo.append(t.getResumen() + "\n");
                }
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

    // Método auxiliar para refrescar la lista de cuentas
    private void refrescarListaCuentas(String cedulaCliente) {
        try {
            List<CuentaBancaria> cuentas = controlador.listarCuentasPorCliente(cedulaCliente);
            areaCuentaInfo.setText("Cuentas del cliente " + cedulaCliente + ":\n");
            for (CuentaBancaria c : cuentas) {
                areaCuentaInfo.append("Número: " + c.getNumeroCuenta() + " | Tipo: " + c.getTipo() + " | Saldo: " + c.getSaldo() + "\n");
            }
        } catch (Exception ex) {
            mostrarError("Error al refrescar las cuentas: " + ex.getMessage());
        }
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