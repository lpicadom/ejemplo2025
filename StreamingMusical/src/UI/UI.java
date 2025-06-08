package UI;

import BL.*;

import java.util.List;
import java.util.Scanner;

public class UI {
    private static Scanner scanner = new Scanner(System.in);
    private static ControladorApp app = new ControladorApp();

    public static void main(String[] args) {
        System.out.println("Bienvenido debe crear un usuario");
        crearUsuario();

        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Añadir canción");
            System.out.println("2. Usar reproductor");
            System.out.println("3. Modificar lista de reproducción");
            System.out.println("4. Ver historial de reproducción");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // consumir salto2

            switch (opcion) {
                case 1 -> agregarCanciones();
                case 2 -> usarReproductor();
                case 3 -> crearUsuario();
                case 4 ->  app.mostrarHistorial();
                case 0 -> salir = true;
                default -> System.out.println("Opción inválida.");
            }
        }

        System.out.println("Programa finalizado.");


   /* crearUsuario();
        app.inicializarDatos();

        // Mostrar información
        Usuario usuario = app.getUsuario();
        Reproductor reproductor = app.getReproductor();
        ListaDeReproduccion lista = usuario.getListasDeReproduccion().get(0);

        System.out.println("Usuario: " + usuario.getNombre());
        System.out.println("Canción actual: " + reproductor.getCancionActual().getTitulo());
        System.out.println("Lista de reproducción: " + lista.getNombre());
        System.out.println("Historial: " + usuario.getHistorialReproduccion().getEntradas().size() + " reproducción registrada.");*/
    }

    public static void crearUsuario() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        app.crearUsuario(nombre, correo, pass);
        System.out.println("Usuario creado correctamente.");
    }


    public static void agregarCanciones() {
        boolean agregarOtra = true;

        while (agregarOtra) {
            System.out.println("\n--- Añadir Canción ---");
            System.out.print("ID Artista: ");
            int idArtista = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Nombre Artista: ");
            String nombreArtista = scanner.nextLine();
            System.out.print("Descripción Artista: ");
            String descArtista = scanner.nextLine();

            System.out.print("ID Álbum: ");
            int idAlbum = scanner.nextInt(); scanner.nextLine();
            System.out.print("Título Álbum: ");
            String tituloAlbum = scanner.nextLine();
            System.out.print("Año Álbum: ");
            int anio = scanner.nextInt(); scanner.nextLine();

            System.out.print("ID Canción: ");
            int idCancion = scanner.nextInt(); scanner.nextLine();
            System.out.print("Título Canción: ");
            String tituloCancion = scanner.nextLine();
            System.out.print("Duración (segundos): ");
            int duracion = scanner.nextInt(); scanner.nextLine();
            System.out.print("Género: ");
            String genero = scanner.nextLine();
            System.out.print("Ruta archivo: ");
            String ruta = scanner.nextLine();

            app.agregarCancionManual(idArtista, nombreArtista, descArtista, idAlbum, tituloAlbum, anio,
                    idCancion, tituloCancion, duracion, genero, ruta);

            System.out.println("Canción agregada correctamente.");

            System.out.print("¿Agregar otra canción? (s/n): ");
            String respuesta = scanner.nextLine();
            agregarOtra = respuesta.equalsIgnoreCase("s");
        }
    }

    public static void usarReproductor() {
        List<Cancion> canciones = app.getBiblioteca().getCanciones();
        Usuario usuario = app.getUsuario(); /**/
        ListaDeReproduccion lista = usuario.getListasDeReproduccion().get(0);
        if (canciones.isEmpty()) {
            System.out.println("No hay canciones disponibles.");
            return;
        }

        System.out.println("\nCanciones disponibles:");
        for (int i = 0; i < canciones.size(); i++) {
            System.out.println((i + 1) + ". " + canciones.get(i).getTitulo());
        }

        System.out.print("Seleccione una canción: ");
        int index = scanner.nextInt() - 1;
        scanner.nextLine();
        app.reproducirCancion(index);
        System.out.println("Reproduciendo..." + canciones.get(index).getTitulo( ));
        System.out.println("Lista de reproducción: " + lista.getNombre());/**/
    }

}