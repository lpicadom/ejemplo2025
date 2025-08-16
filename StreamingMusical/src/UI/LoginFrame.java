package UI;

import BL.BL;
import BL.Usuario;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private BL app;

    public LoginFrame() {
        app = new BL();

        setTitle("🎵 Streaming Musical - Login");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        JLabel lblUsuario = new JLabel("Usuario:");
        JLabel lblContrasena = new JLabel("Contraseña:");
        txtUsuario = new JTextField();
        txtContrasena = new JPasswordField();

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistro = new JButton("Registrar Usuario");

        JPanel panelUsuario = new JPanel(new GridLayout(1, 2));
        panelUsuario.add(lblUsuario);
        panelUsuario.add(txtUsuario);

        JPanel panelPass = new JPanel(new GridLayout(1, 2));
        panelPass.add(lblContrasena);
        panelPass.add(txtContrasena);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnLogin);
        panelBotones.add(btnRegistro);

        add(panelUsuario);
        add(panelPass);
        add(panelBotones);

        // Acción de login
        btnLogin.addActionListener(e -> {
            String nombreUsuario = txtUsuario.getText();
            String contrasena = new String(txtContrasena.getPassword());

            Usuario usuario = app.iniciarSesion(nombreUsuario, contrasena);
            if (usuario != null) {
                JOptionPane.showMessageDialog(this, "Bienvenido " + usuario.getNombre());
                this.dispose();
                if (usuario.esAdmin()) {
                    new AdminFrame(app).setVisible(true);
                } else {
                    new UserFrame(app).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
            }
        });

        // Acción de registro
        btnRegistro.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Ingrese nombre completo:");
            String correo = JOptionPane.showInputDialog(this, "Ingrese correo electrónico:");
            String pass = JOptionPane.showInputDialog(this, "Ingrese contraseña:");

            if (nombre != null && correo != null && pass != null) {
                app.crearUsuario(nombre, correo, pass);
                JOptionPane.showMessageDialog(this, "Usuario registrado correctamente.");
            }
        });
    }
}
