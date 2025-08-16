package BL;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String nombreUsuario;
    private String contrasena;
    private double saldo;
    private boolean esAdmin;
    private List<Cancion> cancionesCompradas;
    private List<ListaDeReproduccion> listasDeReproduccion;

    public Usuario(String nombre, String correo, String nombreUsuario, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.saldo = 2.99;
        this.esAdmin = false;
        this.cancionesCompradas = new ArrayList<>();
        this.listasDeReproduccion = new ArrayList<>();
    }

    public Usuario(int id, String nombre, String correo, String nombreUsuario, String contrasena, double saldo, boolean esAdmin) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.saldo = saldo;
        this.esAdmin = esAdmin;
        this.cancionesCompradas = new ArrayList<>();
        this.listasDeReproduccion = new ArrayList<>();
    }

    public Usuario(int id, String nombre, String identificacion, String fechaNacimiento, String nacionalidad, String avatar, String correo, String nombreUsuario, String contrasena, boolean esAdmin) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.esAdmin = esAdmin;
        this.cancionesCompradas = new ArrayList<>();
        this.listasDeReproduccion = new ArrayList<>();
    }

    public static boolean validarContrasena(String contrasena) {
        if (contrasena == null || contrasena.length() < 8 || contrasena.length() > 12) {
            return false;
        }
        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneNumero = false;
        boolean tieneEspecial = false;

        for (char c : contrasena.toCharArray()) {
            if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
            } else if (Character.isLowerCase(c)) {
                tieneMinuscula = true;
            } else if (Character.isDigit(c)) {
                tieneNumero = true;
            } else if (!Character.isLetterOrDigit(c)) {
                tieneEspecial = true;
            }
        }
        return tieneMayuscula && tieneMinuscula && tieneNumero && tieneEspecial;
    }

    public boolean haCompradoCancion(Cancion cancion) {
        for (Cancion c : cancionesCompradas) {
            if (c.getId() == cancion.getId()) {
                return true;
            }
        }
        return false;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean esAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    public List<Cancion> getCancionesCompradas() {
        return cancionesCompradas;
    }

    public void setCancionesCompradas(List<Cancion> cancionesCompradas) {
        this.cancionesCompradas = cancionesCompradas;
    }

    public List<ListaDeReproduccion> getListasDeReproduccion() {
        return listasDeReproduccion;
    }

    public void setListasDeReproduccion(List<ListaDeReproduccion> listasDeReproduccion) {
        this.listasDeReproduccion = listasDeReproduccion;
    }
}