package UI;

import BL.*;

import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {
    private BL app;

    public AdminFrame(BL app) {
        this.app = app;

        setTitle("🎵 Streaming Musical - Administrador");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));

        JButton btnAgregar = new JButton("Agregar Canción Manual");
        JButton btnUsuarios = new JButton("Ver Usuarios");
        JButton btnSalir = new JButton("Cerrar Sesión");

        panel.add(btnAgregar);
        panel.add(btnUsuarios);
        panel.add(btnSalir);

        add(panel);

        btnAgregar.addActionListener(e -> {
            try {
                int idArtista = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del Artista:"));
                String nombreArtista = JOptionPane.showInputDialog(this, "Nombre del Artista:");
                String descArtista = JOptionPane.showInputDialog(this, "Descripción del Artista:");
                int idAlbum = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del Álbum:"));
                String tituloAlbum = JOptionPane.showInputDialog(this, "Título del Álbum:");
                int anio = Integer.parseInt(JOptionPane.showInputDialog(this, "Año del Álbum:"));
                String titulo = JOptionPane.showInputDialog(this, "Título de la Canción:");
                int duracion = Integer.parseInt(JOptionPane.showInputDialog(this, "Duración (segundos):"));
                String genero = JOptionPane.showInputDialog(this, "Género:");
                String ruta = JOptionPane.showInputDialog(this, "Ruta del Archivo:");
                String compositor = JOptionPane.showInputDialog(this, "Compositor:");
                double precio = Double.parseDouble(JOptionPane.showInputDialog(this, "Precio:"));

                Cancion nueva = app.agregarCancionManual(idArtista, nombreArtista, descArtista,
                        idAlbum, tituloAlbum, anio, titulo, duracion, genero, ruta, compositor, precio);

                JOptionPane.showMessageDialog(this, "✅ Canción '" + nueva.getTitulo() + "' agregada.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnUsuarios.addActionListener(e -> {
            var usuarios = app.obtenerTodosLosUsuarios();
            StringBuilder sb = new StringBuilder("--- USUARIOS ---\n");
            for (Usuario u : usuarios) {
                sb.append("ID: ").append(u.getId()).append(" - ").append(u.getNombre())
                        .append(" (").append(u.getCorreo()).append(")\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        btnSalir.addActionListener(e -> {
            app.setUsuario(null);
            this.dispose();
            new LoginFrame().setVisible(true);
        });
    }
}
