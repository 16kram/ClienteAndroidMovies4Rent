package porqueras.ioc.proyectom13appmovil.modelos;

/**
 * Clase DTO de login
 *
 * @author Esteban Porqueras Araque
 */
public class LoginResponse {
    private String username;
    private String password;
    private String message;
    private LoginResponseValue value;

    public LoginResponse(String username, String password) {
        this.username = username;
        this.password = password;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginResponseValue getValue() {
        return value;
    }

    public class LoginResponseValue {
        private String token;
        private boolean isAdmin;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public boolean isAdmin() {
            return isAdmin;
        }

        public void setAdmin(boolean admin) {
            this.isAdmin = admin;
        }
    }

}
