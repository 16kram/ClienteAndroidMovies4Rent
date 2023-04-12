package porqueras.ioc.proyectom13appmovil.modelos;

import java.util.List;

/**
 * Clase DTO de lista de usuarios
 *
 * @author Esteban Porqueras Araque
 */
public class UsuarioListaResponse {
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
        private List<Usuario> content;

        public List<Usuario> getContent() {
            return content;
        }

        public void setContent(List<Usuario> content) {
            this.content = content;
        }
    }


    public class Usuario {
        private String id;
        private String nombre;
        private String apellidos;
        private String telefono;
        private String email;
        private String direccion;
        private boolean isAdmin;

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

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
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

        public boolean isAdmin() {
            return isAdmin;
        }

        public void setAdmin(boolean admin) {
            isAdmin = admin;
        }
    }

}

