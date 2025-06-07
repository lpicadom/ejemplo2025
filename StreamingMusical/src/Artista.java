import java.util.List;

public class Artista {
    private int id;
    private String nombre;
    private String biografia;
    private List<Album> albumes;

    public Artista(int id, String nombre, String biografia) {
        this.id = id;
        this.nombre = nombre;
        this.biografia = biografia;
        this.albumes = new java.util.ArrayList<>();
    }

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

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public List<Album> getAlbumes() {
        return albumes;
    }

}
