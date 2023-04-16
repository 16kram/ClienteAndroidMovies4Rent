package porqueras.ioc.proyectom13appmovil.modelos;

import java.util.List;

/**
 * Clase DTO lista alquileres
 *
 * @Author Esteban Porqueras Araque
 */
public class AlquilerListaResponse {
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
        private List<Alquiler> content;

        public List<Alquiler> getContent() {
            return content;
        }

        public void setContent(List<Alquiler> content) {
            this.content = content;
        }
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

