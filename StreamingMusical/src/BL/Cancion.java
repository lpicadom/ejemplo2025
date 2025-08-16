package BL;

import java.util.ArrayList;
import java.util.List;

public class Cancion {
    private int id;
    private String titulo;
    private Artista artista;
    private Album album;
    private int duracionSegundos;
    private String genero;
    private String archivoRuta;
    private String compositor;
    private double precio;
    private double calificacionPromedio; // Atributo para guardar el promedio del DAO
    private List<Integer> calificaciones; // Lista para guardar calificaciones individuales

    public Cancion(int id, String titulo, Artista artista, Album album, int duracionSegundos, String genero, String archivoRuta, String compositor, double precio) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.duracionSegundos = duracionSegundos;
        this.genero = genero;
        this.archivoRuta = archivoRuta;
        this.compositor = compositor;
        this.precio = precio;
        this.calificaciones = new ArrayList<>();
    }

    // Método para calcular el promedio de las calificaciones de la lista
    public double calcularCalificacionPromedio() {
        if (calificaciones.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Integer calificacion : calificaciones) {
            sum += calificacion;
        }
        return sum / calificaciones.size();
    }

    // Método que permite al DAO establecer la calificación leída de la base de datos
    public void setCalificacionPromedio(double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public int getDuracionSegundos() {
        return duracionSegundos;
    }

    public void setDuracionSegundos(int duracionSegundos) {
        this.duracionSegundos = duracionSegundos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getArchivoRuta() {
        return archivoRuta;
    }

    public void setArchivoRuta(String archivoRuta) {
        this.archivoRuta = archivoRuta;
    }

    public String getCompositor() {
        return compositor;
    }

    public void setCompositor(String compositor) {
        this.compositor = compositor;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    public List<Integer> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<Integer> calificaciones) {
        this.calificaciones = calificaciones;
    }
}