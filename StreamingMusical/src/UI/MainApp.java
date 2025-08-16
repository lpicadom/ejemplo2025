package UI;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        // Estilo retro (opcional: puedes quitarlo)
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
