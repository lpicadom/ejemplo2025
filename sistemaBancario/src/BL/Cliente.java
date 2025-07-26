package BL;

public class Cliente extends Usuario {
    private int idUsuario;

    public Cliente() {}

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cliente)) return false;
        Cliente c = (Cliente) obj;
        return this.getCedula().equals(c.getCedula());
    }

    @Override
    public int hashCode() {
        return this.getCedula().hashCode();
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellido() + " | Cédula: " + getCedula();
    }
}