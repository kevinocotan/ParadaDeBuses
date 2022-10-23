package sv.edu.catolica.paradadebuses;

public class Datos {
    private int id;
    private String descripcion;
    private String tipo;
    private String latitud;
    private String longitud;
    private String nombre;
    private String foto;

    public Datos(int id, String descripcion, String tipo, String latitud, String longitud, String nombre, String foto) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.latitud = latitud;
        this.longitud = longitud;
        this.nombre = nombre;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

