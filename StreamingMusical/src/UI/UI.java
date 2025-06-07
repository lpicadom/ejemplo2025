package UI;

import BL.ControladorApp;
import BL.ListaDeReproduccion;
import BL.Reproductor;
import BL.Usuario;

public class UI {
    public static class Main {
        public static void main(String[] args) {
            ControladorApp app = new ControladorApp();
            app.inicializarDatos();

            // Mostrar información
            Usuario usuario = app.getUsuario();
            Reproductor reproductor = app.getReproductor();
            ListaDeReproduccion lista = usuario.getListasDeReproduccion().get(0);

            System.out.println("Usuario: " + usuario.getNombre());
            System.out.println("Canción actual: " + reproductor.getCancionActual().getTitulo());
            System.out.println("Lista de reproducción: " + lista.getNombre());
            System.out.println("Historial: " + usuario.getHistorialReproduccion().getEntradas().size() + " reproducción registrada.");
        }
    }
}
