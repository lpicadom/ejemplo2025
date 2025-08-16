package UI;

import BL.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserFrame extends JFrame {
    private BL app;

    public UserFrame(BL app) {
        this.app = app;

        setTitle("🎵 Streaming Musical - Usuario");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));

        JButton btnExplorar = new JButton("Explorar Biblioteca");
        JButton btnTop = new JButton("Top 5 Canciones");
        JButton btnListas = new JButton("Mis Listas de Reproducción");
        JButton btnCrearLista = new JButton("Crear Lista");
        JButton btnComprar = new JButton("Comprar Canción");
        JButton btnHistorial = new JButton("Historial");
        JButton btnCompradas = new JButton("Canciones Compradas");
        JButton btnSalir = new JButton("Cerrar Sesión");

        panel.add(btnExplorar);
        panel.add(btnTop);
        panel.add(btnListas);
        panel.add(btnCrearLista);
        panel.add(btnComprar);
        panel.add(btnHistorial);
        panel.add(btnCompradas);
        panel.add(btnSalir);

        add(panel);

        // Listeners
        btnExplorar.addActionListener(e -> {
            String titulo = JOptionPane.showInputDialog(this, "Título de la canción:");
            if (titulo != null) {
                Cancion c = app.buscarCancionPorTitulo(titulo);
                if (c != null) {
                    JOptionPane.showMessageDialog(this, "🎵 " + c.getTitulo() + " - " + c.getArtista().getNombre());
                } else {
                    JOptionPane.showMessageDialog(this, "No encontrada.");
                }
            }
        });

        btnTop.addActionListener(e -> {
            List<Cancion> top = app.obtenerTopCanciones();
            StringBuilder sb = new StringBuilder("Top 5:\n");
            for (int i = 0; i < top.size(); i++) {
                sb.append((i + 1)).append(". ").append(top.get(i).getTitulo())
                        .append(" - ").append(top.get(i).getArtista().getNombre()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        btnHistorial.addActionListener(e -> app.mostrarHistorial());

        btnSalir.addActionListener(e -> {
            app.setUsuario(null);
            this.dispose();
            new LoginFrame().setVisible(true);
        });
    }
}
