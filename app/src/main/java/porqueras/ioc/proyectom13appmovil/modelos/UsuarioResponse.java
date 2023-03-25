package porqueras.ioc.proyectom13appmovil.modelos;

/**
 * Clase DTO de Usuario
 *
 * @author Esteban Porqueras Araque
 */
public class UsuarioResponse {
    private String email;
    private String username;
    private String password;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String direccion;

    public UsuarioResponse(String email, String username, String password, String nombre, String apellidos, String telefono, String direccion) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.direccion = direccion;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
