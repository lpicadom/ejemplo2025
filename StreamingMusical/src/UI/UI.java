package UI;

import BL.*;
import java.util.List;
import java.util.Scanner;

public class UI {
    private static Scanner scanner = new Scanner(System.in);
    private static BL app = new BL();

    public static void main(String[] args) {
        System.out.println("Bienvenido a la aplicación de Streaming Musical");

        if (app.getAdministrador() == null) {
            System.out.println("\nNo hay administrador registrado. Debe crear uno.");
            crearAdministrador();
        }

        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Registrar nuevo usuario");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> iniciarSesion();
                case 2 -> crearUsuario();
                case 0 -> salir = true;
                default -> System.out.println("Opción inválida.");
            }
        }
        System.out.println("Programa finalizado.");
    }

    public static void crearAdministrador() {
        System.out.print("Correo del administrador: ");
        String correo = scanner.nextLine();
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        String pass1, pass2;
        do {
            System.out.print("Contraseña (8-12 caracteres, 1 mayúscula, 1 minúscula, 1 número, 1 especial): ");
            pass1 = scanner.nextLine();
            System.out.print("Repita la contraseña: ");
            pass2 = scanner.nextLine();
            if (!pass1.equals(pass2)) {
                System.out.println("Las contraseñas no coinciden. Intente de nuevo.");
            } else if (!Usuario.validarContrasena(pass1)) {
                System.out.println("La contraseña no cumple con los requisitos. Intente de nuevo.");
            }
        } while (!pass1.equals(pass2) || !Usuario.validarContrasena(pass1));

        app.registrarAdmin(correo, nombreUsuario, pass1);
    }

    public static void iniciarSesion() {
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine();

        Usuario usuario = app.iniciarSesion(nombreUsuario, pass);
        if (usuario != null) {
            if (usuario.esAdmin()) {
                menuAdmin();
            } else {
                menuUsuario();
            }
        }
    }

    public static void crearUsuario() {
        System.out.println("\n--- REGISTRO DE NUEVO USUARIO ---");
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();
        String pass1, pass2;
        do {
            System.out.print("Contraseña (8-12 caracteres, 1 mayúscula, 1 minúscula, 1 número, 1 especial): ");
            pass1 = scanner.nextLine();
            System.out.print("Repita la contraseña: ");
            pass2 = scanner.nextLine();
            if (!pass1.equals(pass2)) {
                System.out.println("Las contraseñas no coinciden. Intente de nuevo.");
            } else if (!Usuario.validarContrasena(pass1)) {
                System.out.println("La contraseña no cumple con los requisitos. Intente de nuevo.");
            }
        } while (!pass1.equals(pass2) || !Usuario.validarContrasena(pass1));

        app.crearUsuario(nombre, correo, pass1);
    }

    public static void menuUsuario() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENÚ DE USUARIO ===");
            System.out.println("1. Explorar la biblioteca (buscar)");
            System.out.println("2. Ver Top 5 Canciones");
            System.out.println("3. Ver mis listas de reproducción");
            System.out.println("4. Crear nueva lista de reproducción");
            System.out.println("5. Comprar una canción");
            System.out.println("6. Ver mi historial de reproducción");
            System.out.println("7. Ver mis canciones compradas");
            System.out.println("8. Agregar canción a lista de reproducción");
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> explorarBiblioteca();
                case 2 -> verTop5Canciones();
                case 3 -> verListasDeReproduccion();
                case 4 -> crearListaDeReproduccion();
                case 5 -> comprarCancion();
                case 6 -> app.mostrarHistorial();
                case 7 -> verCancionesCompradas();
                case 8 -> agregarCancionALista();
                case 0 -> {
                    app.setUsuario(null);
                    salir = true;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    public static void explorarBiblioteca() {
        System.out.println("\n--- EXPLORAR BIBLIOTECA ---");
        System.out.println("1. Buscar por título de canción");
        System.out.println("2. Buscar por artista");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1 -> buscarPorTitulo();
            case 2 -> buscarPorArtista();
            default -> System.out.println("Opción inválida.");
        }
    }

    public static void buscarPorTitulo() {
        System.out.print("Ingrese el título de la canción: ");
        String titulo = scanner.nextLine();
        Cancion cancion = app.buscarCancionPorTitulo(titulo);
        if (cancion != null) {
            System.out.println("🎵 Canción encontrada:");
            System.out.println("Título: " + cancion.getTitulo());
            System.out.println("Artista: " + cancion.getArtista().getNombre());
            System.out.println("Álbum: " + cancion.getAlbum().getTitulo());
            System.out.println("Precio: $" + cancion.getPrecio());
            System.out.println("Calificación Promedio: " + cancion.getCalificacionPromedio() + "/10");
        } else {
            System.out.println("Canción no encontrada.");
        }
    }

    public static void buscarPorArtista() {
        System.out.println("Funcionalidad en desarrollo para la capa DAO.");
    }

    public static void verTop5Canciones() {
        System.out.println("\n=== TOP 5 CANCIONES MEJOR VALORADAS ===");
        List<Cancion> topCanciones = app.obtenerTopCanciones();
        if (topCanciones.isEmpty()) {
            System.out.println("No hay canciones para mostrar.");
            return;
        }
        for (int i = 0; i < topCanciones.size(); i++) {
            Cancion c = topCanciones.get(i);
            System.out.println((i + 1) + ". " + c.getTitulo() + " por " + c.getArtista().getNombre() +
                    " (" + c.getCalificacionPromedio() + "/10)");
        }
        System.out.print("Seleccione una canción para reproducir (0 para salir): ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();
        if (seleccion > 0 && seleccion <= topCanciones.size()) {
            app.reproducirCancion(topCanciones.get(seleccion - 1));
        }
    }

    public static void verListasDeReproduccion() {
        Usuario usuario = app.getUsuario();
        if (usuario == null) {
            System.out.println("Debe iniciar sesión para ver sus listas.");
            return;
        }

        List<ListaDeReproduccion> listas = app.obtenerListasDeReproduccion(usuario.getId());
        if (listas.isEmpty()) {
            System.out.println("No tienes listas de reproducción.");
            return;
        }

        System.out.println("\n--- MIS LISTAS DE REPRODUCCIÓN ---");
        for (int i = 0; i < listas.size(); i++) {
            System.out.println((i + 1) + ". " + listas.get(i).getNombre() + " (" + listas.get(i).getCanciones().size() + " canciones)");
        }

        System.out.print("Seleccione el número de una lista para ver su contenido (0 para salir): ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        if (seleccion > 0 && seleccion <= listas.size()) {
            ListaDeReproduccion lista = listas.get(seleccion - 1);
            System.out.println("\nContenido de la lista: " + lista.getNombre());
            for (Cancion c : lista.getCanciones()) {
                System.out.println("- " + c.getTitulo() + " por " + c.getArtista().getNombre());
            }
        }
    }

    public static void crearListaDeReproduccion() {
        Usuario usuario = app.getUsuario();
        if (usuario == null) {
            System.out.println("Debe iniciar sesión para crear una lista.");
            return;
        }

        System.out.print("Ingrese el nombre de la nueva lista: ");
        String nombre = scanner.nextLine();

        // ✅ Corrección
        app.crearListaDeReproduccion(nombre);
    }

    public static void agregarCancionALista() {
        Usuario usuario = app.getUsuario();
        if (usuario == null) {
            System.out.println("Debe iniciar sesión para agregar canciones a una lista.");
            return;
        }

        List<ListaDeReproduccion> listas = app.obtenerListasDeReproduccion(usuario.getId());
        if (listas.isEmpty()) {
            System.out.println("No tienes listas de reproducción. Primero crea una.");
            return;
        }

        System.out.println("\n--- AGREGAR CANCIÓN A LISTA ---");
        System.out.println("Seleccione una lista:");
        for (int i = 0; i < listas.size(); i++) {
            System.out.println((i + 1) + ". " + listas.get(i).getNombre());
        }

        System.out.print("Seleccione el número de la lista: ");
        int seleccionLista = scanner.nextInt();
        scanner.nextLine();

        if (seleccionLista < 1 || seleccionLista > listas.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        ListaDeReproduccion lista = listas.get(seleccionLista - 1);

        // ✅ Corrección: ahora usamos el DAO directamente
        List<Cancion> biblioteca = app.obtenerTodasLasCanciones();

        if (biblioteca.isEmpty()) {
            System.out.println("No hay canciones en la biblioteca.");
            return;
        }

        boolean seguir = true;
        while (seguir) {
            System.out.println("\n🎵 Canciones disponibles:");
            for (int i = 0; i < biblioteca.size(); i++) {
                System.out.println((i + 1) + ". " + biblioteca.get(i).getTitulo());
            }

            System.out.print("Seleccione el número de la canción a agregar (0 para salir): ");
            int seleccion = scanner.nextInt();
            scanner.nextLine();

            if (seleccion == 0) {
                seguir = false;
            } else if (seleccion >= 1 && seleccion <= biblioteca.size()) {
                Cancion cancion = biblioteca.get(seleccion - 1);
                lista.agregarCancion(cancion);
                System.out.println("✅ Canción agregada a la lista: " + lista.getNombre());
            } else {
                System.out.println("Número inválido.");
            }
        }
    }

    public static void comprarCancion() {
        Usuario usuario = app.getUsuario();
        if (usuario == null) {
            System.out.println("Debe iniciar sesión para comprar canciones.");
            return;
        }

        System.out.println("\n--- COMPRAR CANCIÓN ---");
        List<Cancion> canciones = app.obtenerTodasLasCanciones();
        if (canciones.isEmpty()) {
            System.out.println("No hay canciones disponibles para comprar.");
            return;
        }

        System.out.println("Canciones disponibles:");
        for (int i = 0; i < canciones.size(); i++) {
            System.out.println((i + 1) + ". " + canciones.get(i).getTitulo() + " - $" + canciones.get(i).getPrecio());
        }

        System.out.print("Seleccione el número de la canción a comprar (0 para salir): ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        if (seleccion > 0 && seleccion <= canciones.size()) {
            Cancion cancionAComprar = canciones.get(seleccion - 1);
            app.comprarCancion(cancionAComprar);
        } else if (seleccion != 0) {
            System.out.println("Opción inválida.");
        }
    }

    public static void verCancionesCompradas() {
        Usuario usuario = app.getUsuario();
        if (usuario == null) {
            System.out.println("Debe iniciar sesión para ver sus canciones compradas.");
            return;
        }

        List<Cancion> canciones = app.getUsuario().getCancionesCompradas();
        if (canciones.isEmpty()) {
            System.out.println("No has comprado ninguna canción.");
            return;
        }

        System.out.println("\n--- MIS CANCIONES COMPRADAS ---");
        for (int i = 0; i < canciones.size(); i++) {
            System.out.println((i + 1) + ". " + canciones.get(i).getTitulo() + " por " + canciones.get(i).getArtista().getNombre());
        }

        System.out.print("Seleccione el número de la canción a reproducir (0 para salir): ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        if (seleccion > 0 && seleccion <= canciones.size()) {
            app.reproducirCancion(canciones.get(seleccion - 1));
        }
    }

    public static void menuAdmin() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== MENÚ DE ADMINISTRADOR ===");
            System.out.println("1. Agregar nueva canción (manual)");
            System.out.println("2. Ver todos los usuarios");
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> agregarCancionManual();
                case 2 -> verUsuarios();
                case 0 -> {
                    app.setUsuario(null);
                    salir = true;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    public static void agregarCancionManual() {
        System.out.println("\n--- AGREGAR CANCIÓN MANUALMENTE ---");
        System.out.print("ID del artista: ");
        int idArtista = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nombre del artista: ");
        String nombreArtista = scanner.nextLine();
        System.out.print("Descripción del artista: ");
        String descripcionArtista = scanner.nextLine();
        System.out.print("ID del álbum: ");
        int idAlbum = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Título del álbum: ");
        String tituloAlbum = scanner.nextLine();
        System.out.print("Año del álbum: ");
        int anioAlbum = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Título de la canción: ");
        String tituloCancion = scanner.nextLine();
        System.out.print("Duración (en segundos): ");
        int duracion = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Género: ");
        String genero = scanner.nextLine();
        System.out.print("Ruta del archivo: ");
        String ruta = scanner.nextLine();
        System.out.print("Compositor: ");
        String compositor = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        scanner.nextLine();

        Cancion nuevaCancion = app.agregarCancionManual(idArtista, nombreArtista, descripcionArtista, idAlbum, tituloAlbum, anioAlbum, tituloCancion, duracion, genero, ruta, compositor, precio);
        System.out.println("✅ Canción '" + nuevaCancion.getTitulo() + "' agregada correctamente.");
    }

    public static void verUsuarios() {
        System.out.println("\n--- USUARIOS REGISTRADOS ---");
        List<Usuario> usuarios = app.obtenerTodosLosUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        for (Usuario u : usuarios) {
            System.out.println("ID: " + u.getId() + ", Nombre: " + u.getNombre() + ", Correo: " + u.getCorreo());
        }
    }
}
