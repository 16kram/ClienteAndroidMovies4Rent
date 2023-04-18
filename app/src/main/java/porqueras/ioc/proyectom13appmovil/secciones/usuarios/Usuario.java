package porqueras.ioc.proyectom13appmovil.secciones.usuarios;

/**
 * Clase usuario que utiliza el RecyclerView para mostrar los usuarios
 *
 * @Author Esteban Porqueras Araque
 */
public class Usuario {
    private String nombre;
    private String apellidos;
    private String Telefono;
    private String email;
    private String direccion;
    private boolean isAdmin;

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
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
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
