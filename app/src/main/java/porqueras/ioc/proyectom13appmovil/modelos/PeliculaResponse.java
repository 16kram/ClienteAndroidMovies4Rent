package porqueras.ioc.proyectom13appmovil.modelos;

/**
 * Clase DTO de película
 *
 * @author Esteban Porqueras Araque
 */
public class PeliculaResponse {
    private String titulo;
    private String director;
    private String genero;
    private int duracion;
    private int año;
    private int precio;

    public PeliculaResponse(String titulo, String genero, String director, int duracion, int año, int precio) {
        this.titulo = titulo;
        this.director = director;
        this.genero = genero;
        this.duracion = duracion;
        this.año = año;
        this.precio = precio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
