package porqueras.ioc.proyectom13appmovil.modelos;

/**
 * Clase DTO información de una película
 *
 * @author Esteban Porqueras Araque
 */
public class PeliculaInfoResponse {
    String mesagge;
    Value value;

    public String getMesagge() {
        return mesagge;
    }

    public void setMesagge(String mesagge) {
        this.mesagge = mesagge;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public class Value {
        String id;
        String titulo;
        String genero;
        String director;
        int duracion;
        int año;
        int precio;

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
