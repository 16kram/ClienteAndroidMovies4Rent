package porqueras.ioc.proyectom13appmovil.modelos;

import java.util.List;

/**
 * Clase DTO para mostrar la información de un alquiler por su identificador
 *
 * @Author Esteban Porqueras Araque
 */
public class AlquilerPeliculasPorId {
    private String message;
    private AlquilerId value;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AlquilerId getValue() {
        return value;
    }

    public void setValue(AlquilerId value) {
        this.value = value;
    }

    public class AlquilerId {
        private String id;
        private String peliculaId;
        private String usuariId;
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
