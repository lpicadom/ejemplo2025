package BL;

import java.time.LocalDateTime;

public class Compra {
    private Usuario usuario;
    private Cancion cancion;
    private LocalDateTime fechaCompra;
    private double precioCompra;

    public Compra(Usuario usuario, Cancion cancion, double precioCompra) {
        this.usuario = usuario;
        this.cancion = cancion;
        this.fechaCompra = LocalDateTime.now();
        this.precioCompra = precioCompra;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }
}