package porqueras.ioc.proyectom13appmovil.modelos;

/**
 * Clase DTO para mofificar la contraseña del usuario
 */
public class PasswordUpdate {
    String password;
    String confirmPassword;

    public PasswordUpdate(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
