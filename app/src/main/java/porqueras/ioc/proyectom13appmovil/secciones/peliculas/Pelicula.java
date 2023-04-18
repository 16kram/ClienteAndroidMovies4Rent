package porqueras.ioc.proyectom13appmovil.secciones.peliculas;

/**
 * Clase película que utiliza el RecyclerView para mostrar las películas
 *
 * @Author Esteban Porqueras Araque
 */
public class Pelicula {
    private String tituloPelicula;
    private int precioAlquiler;

    public String getTituloPelicula() {
        return tituloPelicula;
    }

    public void setTituloPelicula(String tituloPelicula) {
        this.tituloPelicula = tituloPelicula;
    }

    public int getPrecioAlquiler() {
        return precioAlquiler;
    }

    public void setPrecioAlquiler(int precioAlquiler) {
        this.precioAlquiler = precioAlquiler;
    }
}
