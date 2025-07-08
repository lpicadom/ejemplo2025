package UI;

import BL.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class UI {
    private static Scanner scanner = new Scanner(System.in);
    private static BL app = new BL();

    public static void main(String[] args) {
        System.out.println("Bienvenido debe crear un usuario");
        crearUsuario();

        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Añadir canción");
            System.out.println("2. Usar reproductor");
            System.out.println("3. Ver historial de reproducción");
            System.out.println("4. Crear nueva lista de reproducción");
            System.out.println("5. Agregar canciones desde biblioteca a una lista");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // consumir salto2

            switch (opcion) {
                case 1 -> agregarCanciones();
                case 2 -> usarReproductor();
                case 3 -> app.mostrarHistorial();
                case 4 -> crearNuevaLista();
                case 5 -> agregarDesdeBiblioteca();
                case 0 -> salir = true;
                default -> System.out.println("Opción inválida.");
            }
        }

        System.out.println("Programa finalizado.");
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
        Usuario usuario = app.getUsuario();
        ListaDeReproduccion lista = usuario.getListasDeReproduccion().get(0); // Tomamos la primera lista

        while (agregarOtra) {
            System.out.println("\n--- Añadir Canción ---");
            // Pedimos datos como ya lo haces...
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

            // Validar la ruta del archivo con ciclo hasta que sea válida
            String ruta = "";
            boolean rutaValida = false;
            while (!rutaValida) {
                System.out.print("Ruta archivo: ");
                ruta = scanner.nextLine();

                File archivo = new File(ruta);
                if (!archivo.exists()) {
                    System.out.println("⚠ El archivo no existe. Intente nuevamente.");
                } else {
                    rutaValida = true;
                }
            }

            // Agrega a la biblioteca
            Cancion nueva = app.agregarCancionManual(idArtista, nombreArtista, descArtista, idAlbum, tituloAlbum, anio,
                    idCancion, tituloCancion, duracion, genero, ruta);

            // Agrega también a la lista de reproducción del usuario
            lista.agregarCancion(nueva);

            System.out.println("✅ Canción agregada correctamente.");

            System.out.print("¿Agregar otra canción? (s/n): ");
            String respuesta = scanner.nextLine();
            agregarOtra = respuesta.equalsIgnoreCase("s");
        }
    }

    public static void usarReproductor() {
        Usuario usuario = app.getUsuario();
        List<ListaDeReproduccion> listas = usuario.getListasDeReproduccion();

        if (usuario == null || listas.isEmpty()) {
            System.out.println("No hay usuario o listas de reproducción disponibles.");
            return;
        }

        System.out.println("Seleccione la lista que desea usar:");
        for (int i = 0; i < listas.size(); i++) {
            System.out.println((i + 1) + ". " + listas.get(i).getNombre());
        }

        int seleccionLista = scanner.nextInt();
        scanner.nextLine(); // limpiar salto
        if (seleccionLista < 1 || seleccionLista > listas.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        ListaDeReproduccion lista = listas.get(seleccionLista - 1);
        List<Cancion> canciones = lista.getCanciones();

        if (canciones.isEmpty()) {
            System.out.println("La lista de reproducción está vacía.");
            return;
        }

        boolean salir = false;

        while (!salir) {
            System.out.println("\n🎵 Canciones disponibles en la lista '" + lista.getNombre() + "':");
            for (int i = 0; i < canciones.size(); i++) {
                System.out.println((i + 1) + ". " + canciones.get(i).getTitulo());
            }

            System.out.println("\nOpciones:");
            System.out.println("0 - Reproducir toda la lista (orden aleatorio)");
            System.out.println("T - Reproducir toda la lista (en orden)");
            System.out.println("E - Eliminar canción de la lista");
            System.out.println("S - Salir del reproductor");

            System.out.print("Seleccione una canción o una opción: ");
            String entrada = scanner.nextLine().trim();

            switch (entrada.toUpperCase()) {
                case "S" -> {
                    salir = true;
                    System.out.println("Saliendo del reproductor.");
                }
                case "E" -> {
                    modificarLista(lista);
                    canciones = lista.getCanciones(); // actualizar lista
                    if (canciones.isEmpty()) {
                        System.out.println("La lista ahora está vacía. Saliendo del reproductor.");
                        salir = true;
                    }
                }
                case "T" -> {
                    System.out.println("▶ Reproduciendo lista completa (en orden):");
                    reproducirLista(canciones);
                }
                case "0" -> {
                    System.out.println("▶ Reproduciendo lista completa (aleatoria):");
                    List<Cancion> aleatorias = new ArrayList<>(canciones);
                    Collections.shuffle(aleatorias);
                    reproducirLista(aleatorias);
                }
                default -> {
                    try {
                        int seleccion = Integer.parseInt(entrada);
                        if (seleccion >= 1 && seleccion <= canciones.size()) {
                            Cancion cancion = canciones.get(seleccion - 1);
                            int index = app.getBiblioteca().getCanciones().indexOf(cancion);
                            if (index != -1) {
                                app.reproducirCancion(index);
                                System.out.println("▶ Reproduciendo: " + cancion.getTitulo());
                            } else {
                                System.out.println("Error: Canción no encontrada en la biblioteca.");
                            }
                        } else {
                            System.out.println("Número fuera de rango.");
                        }
                    } catch (NumberFormatException ex) {
                        System.out.println("Entrada inválida. Use un número, 'T', '0', 'E' o 'S'.");
                    }
                }
            }
        }
    }

    public static void modificarLista(ListaDeReproduccion lista) {
        List<Cancion> canciones = lista.getCanciones();

        if (canciones.isEmpty()) {
            System.out.println("La lista de reproducción está vacía.");
            return;
        }

        System.out.println("\n🎧 Canciones en la lista '" + lista.getNombre() + "':");
        for (int i = 0; i < canciones.size(); i++) {
            System.out.println((i + 1) + ". " + canciones.get(i).getTitulo());
        }

        System.out.print("Ingrese el número de la canción que desea eliminar (0 para cancelar): ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (opcion == 0) {
            System.out.println("Operación cancelada.");
            return;
        }

        if (opcion < 1 || opcion > canciones.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Cancion eliminada = canciones.remove(opcion - 1);
        System.out.println("❌ Canción eliminada: " + eliminada.getTitulo());
    }

    private static void reproducirLista(List<Cancion> lista) {
        for (Cancion cancion : lista) {
            int index = app.getBiblioteca().getCanciones().indexOf(cancion);
            if (index != -1) {
                app.reproducirCancion(index);
                System.out.println("🎶 Reproduciendo: " + cancion.getTitulo());
            } else {
                System.out.println("⚠ No se pudo encontrar " + cancion.getTitulo() + " en la biblioteca.");
                continue;
            }

            System.out.println("Presione ENTER para continuar o escriba 'S' para detener:");
            String respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("S")) {
                System.out.println("⏹ Reproducción detenida.");
                break;
            }
        }
    }

    public static void crearNuevaLista() {
        System.out.print("Nombre de la nueva lista: ");
        String nombre = scanner.nextLine();

        int nuevoId = app.getUsuario().getListasDeReproduccion().size() + 1;
        ListaDeReproduccion nuevaLista = new ListaDeReproduccion(nuevoId, nombre, app.getUsuario());
        app.getUsuario().getListasDeReproduccion().add(nuevaLista);

        System.out.println("✅ Lista creada: " + nombre);
    }

    public static void agregarDesdeBiblioteca() {
        Usuario usuario = app.getUsuario();
        List<ListaDeReproduccion> listas = usuario.getListasDeReproduccion();

        if (listas.isEmpty()) {
            System.out.println("No hay listas disponibles.");
            return;
        }

        System.out.println("Seleccione una lista para agregar canciones:");
        for (int i = 0; i < listas.size(); i++) {
            System.out.println((i + 1) + ". " + listas.get(i).getNombre());
        }

        int seleccionLista = scanner.nextInt(); scanner.nextLine();
        if (seleccionLista < 1 || seleccionLista > listas.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        ListaDeReproduccion lista = listas.get(seleccionLista - 1);
        List<Cancion> biblioteca = app.getBiblioteca().getCanciones();

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
            int seleccion = scanner.nextInt(); scanner.nextLine();

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



    }

