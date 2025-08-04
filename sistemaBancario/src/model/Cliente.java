package model;

public class Cliente {
    private String cedula;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String direccion;
    private String sexo;
    private String profesion;

    public Cliente() {}

    public Cliente(String cedula, String nombre, String apellido, String correoElectronico, String direccion, String sexo, String profesion) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.sexo = sexo;
        this.profesion = profesion;
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

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getProfesion() { return profesion; }
    public void setProfesion(String profesion) { this.profesion = profesion; }
}
