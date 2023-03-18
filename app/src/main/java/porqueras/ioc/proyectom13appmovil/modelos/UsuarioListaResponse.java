package porqueras.ioc.proyectom13appmovil.modelos;

import java.util.List;

public class UsuarioListaResponse {
    private String message;
    private List<UsuarioInfoResponse.Usuario> value;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UsuarioInfoResponse.Usuario> getValue() {
        return  value;
    }

    public void setValue(List<UsuarioInfoResponse.Usuario> value) {
        this.value = value;
    }

    public class Usuario{
        private String id;
        private String nombre;
        private String apellidos;
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
    }
}
