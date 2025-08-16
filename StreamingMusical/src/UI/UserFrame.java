package UI;

import BL.BL;
import BL.Cancion;
import BL.ListaDeReproduccion;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UserFrame extends JFrame {
    private BL app;

    private JButton btnExplorar, btnTop, btnHistorial, btnCompradas, btnCrearLista, btnVerListas, btnSalir;

    public UserFrame(BL app) {
        this.app = app;
        setTitle("Usuario: " + app.getUsuario().getNombreUsuario());
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Componentes
        btnExplorar = new JButton("Explorar Canción");
        btnTop = new JButton("Top 5 Canciones");
        btnHistorial = new JButton("Historial");
        btnCompradas = new JButton("Canciones Compradas");
        btnCrearLista = new JButton("Crear Lista");
        btnVerListas = new JButton("Mis listas de reproducción");
        btnSalir = new JButton("Cerrar Sesión");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));
        panel.add(btnExplorar);
        panel.add(btnTop);
        panel.add(btnHistorial);
        panel.add(btnCompradas);
        panel.add(btnCrearLista);
        panel.add(btnVerListas);
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

        // Listener para ver canciones compradas
        btnCompradas.addActionListener(e -> app.mostrarCancionesCompradas());

        // Listener para crear lista de reproducción
        btnCrearLista.addActionListener(e -> {
            String nombreLista = JOptionPane.showInputDialog(this, "Nombre de la nueva lista:");
            if (nombreLista != null && !nombreLista.trim().isEmpty()) {
                app.crearListaDeReproduccion(nombreLista);
            }
        });

        // Listener para ver listas de reproducción
        btnVerListas.addActionListener(e -> app.mostrarListasDeReproduccion());

        btnSalir.addActionListener(e -> {
            app.setUsuario(null);
            this.dispose();
            new LoginFrame().setVisible(true);
        });
    }
}