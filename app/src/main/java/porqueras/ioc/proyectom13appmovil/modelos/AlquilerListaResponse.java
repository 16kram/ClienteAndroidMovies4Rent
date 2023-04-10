package porqueras.ioc.proyectom13appmovil.modelos;

import java.util.List;

/**
 * Clase DTO lista alquileres
 */
public class AlquilerListaResponse {
    private String message;
    private List<AlquilerListaResponse.Alquiler> value;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Alquiler> getValue() {
        return value;
    }

    public void setValue(List<Alquiler> value) {
        this.value = value;
    }

    public class Alquiler {
        private String id;
        private String peliculaId;
        private String usuariId;
        private String fechaInicio;
        private String fechafin;
        private int precio;
        private String estado;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPeliculaId() {
            return peliculaId;
        }

        public void setPeliculaId(String peliculaId) {
            this.peliculaId = peliculaId;
        }

        public String getUsuariId() {
            return usuariId;
        }

        public void setUsuariId(String usuariId) {
            this.usuariId = usuariId;
        }

        public String getFechaInicio() {
            return fechaInicio;
        }

        public void setFechaInicio(String fechaInicio) {
            this.fechaInicio = fechaInicio;
        }

        public String getFechafin() {
            return fechafin;
        }

        public void setFechafin(String fechafin) {
            this.fechafin = fechafin;
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
