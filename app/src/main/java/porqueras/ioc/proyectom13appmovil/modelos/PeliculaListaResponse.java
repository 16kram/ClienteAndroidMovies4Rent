package porqueras.ioc.proyectom13appmovil.modelos;

import java.util.List;

/**
 * Clase DTO lista de películas
 *
 * @author Esteban Porqueras Araque
 */
public class PeliculaListaResponse {
    private String message;
    private List<PeliculaListaResponse.Pelicula> value;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Pelicula> getValue() {
        return value;
    }

    public void setValue(List<Pelicula> value) {
        this.value = value;
    }

    public class Pelicula {
        private String id;
        private String titulo;
        private String genero;
        private String director;
        private int duracion;
        private int año;
        private int precio;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getGenero() {
            return genero;
        }

        public void setGenero(String genero) {
            this.genero = genero;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
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
}
