package porqueras.ioc.proyectom13appmovil.modelos;

import java.util.List;

/**
 * Clase DTO lista de películas
 *
 * @author Esteban Porqueras Araque
 */

public class PeliculaListaResponse {
    private String message;
    private Value value;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }


    public class Value {
        private List<Pelicula> content;

        public List<Pelicula> getContent() {
            return content;
        }

        public void setContent(List<Pelicula> content) {
            this.content = content;
        }
    }


    public class Pelicula {
        private String id;
        private String titulo;
        private String director;
        private String genero;
        private int duracion;
        private int año;
        private int precio;
        private int vecesAlquilada;

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

        public int getVecesAlquilada() {
            return vecesAlquilada;
        }

        public void setVecesAlquilada(int vecesAlquilada) {
            this.vecesAlquilada = vecesAlquilada;
        }
    }
}

