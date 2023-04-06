package porqueras.ioc.proyectom13appmovil.modelos;

/**
 * Clase DTO que muestra información de la película que se ha alquilado
 *
 * @Author Esteban Porqueras Araque
 */
public class DetalleAlquiler {
    private String message;
    private Alquiler value;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Alquiler getValue() {
        return value;
    }

    public void setValue(Alquiler value) {
        this.value = value;
    }

    public class Alquiler {
        private String id;
        private String pelicula;
        private String usuari;
        private String fechaInicio;
        private String fechaFin;
        private int precio;
        private String estado;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPelicula() {
            return pelicula;
        }

        public void setPelicula(String pelicula) {
            this.pelicula = pelicula;
        }

        public String getUsuari() {
            return usuari;
        }

        public void setUsuari(String usuari) {
            this.usuari = usuari;
        }

        public String getFechaInicio() {
            return fechaInicio;
        }

        public void setFechaInicio(String fechaInicio) {
            this.fechaInicio = fechaInicio;
        }

        public String getFechaFin() {
            return fechaFin;
        }

        public void setFechaFin(String fechaFin) {
            this.fechaFin = fechaFin;
        }

        public int getPrecio() {
            return precio;
        }

        public void setPrecio(int precio) {
            this.precio = precio;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }
}
