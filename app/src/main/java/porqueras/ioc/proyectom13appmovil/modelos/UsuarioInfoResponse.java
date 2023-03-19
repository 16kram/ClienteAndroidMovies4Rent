package porqueras.ioc.proyectom13appmovil.modelos;

public class UsuarioInfoResponse {
    private String message;
    private Usuario value;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Usuario getValue() {
        return value;
    }

    public void setValue(Usuario value) {
        this.value = value;
    }

    public class Usuario {
        private String id;
        private String nombre;
        private String apellidos;
        private String telefono;
        private String email;
        private String direccion;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getApellidos() {
            return apellidos;
        }

        public void setApellidos(String apellidos) {
            this.apellidos = apellidos;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }
    }
}
