package model;

public class Administrador {
    private String cedula;
    private String nombre;
    private String apellido;
    private String correoElectronico;

    public Administrador() {}

    public Administrador(String cedula, String nombre, String apellido, String correoElectronico) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
    }

    // Getters y Setters
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
}