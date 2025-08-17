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
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));

        JButton btnExplorar = new JButton("Explorar Biblioteca (Comprar/Reproducir)");
        JButton btnTop = new JButton("Ver Top 5 Canciones");
        JButton btnHistorial = new JButton("Ver Historial");
        JButton btnListas = new JButton("Ver Listas de Reproducción");
        JButton btnNuevaLista = new JButton("Crear Lista de Reproducción");
        JButton btnCompradas = new JButton("Ver Canciones Compradas");
        JButton btnReproducirCompradas = new JButton("Reproducir Canciones Compradas");
        JButton btnSalir = new JButton("Cerrar Sesión");

        panel.add(btnExplorar);
        panel.add(btnTop);
        panel.add(btnHistorial);
        panel.add(btnListas);
        panel.add(btnNuevaLista);
        panel.add(btnCompradas);
        panel.add(btnReproducirCompradas);
        panel.add(btnSalir);

        add(panel);

        // --- BOTÓN EXPLORAR ---
        btnExplorar.addActionListener(e -> {
            List<Cancion> canciones = app.obtenerTodasLasCanciones();
            if (canciones.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay canciones disponibles.");
                return;
            }

            String[] opciones = canciones.stream()
                    .map(c -> c.getId() + " - " + c.getTitulo() + " (" + c.getPrecio() + "$)")
                    .toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(this,
                    "Selecciona una canción:", "Explorar Biblioteca",
                    JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

            if (seleccion != null) {
                int id = Integer.parseInt(seleccion.split(" - ")[0]);
                Cancion c = app.obtenerCancionPorId(id);

                String[] acciones = {"Comprar", "Reproducir", "Cancelar"};
                int opcion = JOptionPane.showOptionDialog(this,
                        "¿Qué deseas hacer con '" + c.getTitulo() + "'?",
                        "Acción",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null, acciones, acciones[0]);

                if (opcion == 0) { // Comprar
                    JOptionPane.showMessageDialog(this, app.comprarCancion(c));
                } else if (opcion == 1) { // Reproducir
                    JOptionPane.showMessageDialog(this, app.reproducirCancion(c));
                }
            }
        });

        // --- BOTÓN TOP 5 ---
        btnTop.addActionListener(e -> {
            List<Cancion> canciones = app.obtenerTopCanciones();
            if (canciones.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay canciones disponibles.");
                return;
            }
            StringBuilder sb = new StringBuilder("=== TOP 5 ===\n");
            for (int i = 0; i < canciones.size(); i++) {
                sb.append(i + 1).append(". ")
                        .append(canciones.get(i).getTitulo())
                        .append(" - ").append(canciones.get(i).getArtista().getNombre())
                        .append(" (").append(canciones.get(i).getCalificacionPromedio()).append("/10)\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        });

        // --- BOTÓN HISTORIAL ---
        btnHistorial.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, app.mostrarHistorial());
        });

        // --- BOTÓN VER LISTAS ---
        btnListas.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, app.mostrarListasDeReproduccion());
        });

        // --- BOTÓN CREAR LISTA ---
        btnNuevaLista.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Nombre de la nueva lista:");
            if (nombre != null && !nombre.isBlank()) {
                JOptionPane.showMessageDialog(this, app.crearListaDeReproduccion(nombre));
            }
        });

        // --- BOTÓN VER COMPRADAS ---
        btnCompradas.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, app.mostrarCancionesCompradas());
        });

        // --- BOTÓN REPRODUCIR COMPRADAS ---
        btnReproducirCompradas.addActionListener(e -> {
            List<Cancion> compradas = app.getUsuario().getCancionesCompradas();
            if (compradas == null || compradas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No has comprado canciones aún.");
                return;
            }

            String[] opciones = compradas.stream()
                    .map(c -> c.getId() + " - " + c.getTitulo())
                    .toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(this,
                    "Selecciona una canción:", "Reproducir Canciones Compradas",
                    JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

            if (seleccion != null) {
                int id = Integer.parseInt(seleccion.split(" - ")[0]);
                Cancion c = app.obtenerCancionPorId(id);
                JOptionPane.showMessageDialog(this, app.reproducirCancion(c));
            }
        });

        // --- BOTÓN SALIR ---
        btnSalir.addActionListener(e -> {
            app.setUsuario(null);
            this.dispose();
            new LoginFrame().setVisible(true);
        });
    }
}
