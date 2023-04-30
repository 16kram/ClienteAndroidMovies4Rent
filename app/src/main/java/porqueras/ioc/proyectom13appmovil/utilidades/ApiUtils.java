package porqueras.ioc.proyectom13appmovil.utilidades;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

/**
 * Clase de constantes de la aplicación
 *
 * @author Esteban Porqueras Araque
 */
public class ApiUtils {
    //URL del servidor
    public static final String BASE_URL = "https://10.0.2.2:8080";

    //Token
    public static String TOKEN;

    //Administrador=true o usuario=false
    public static boolean administrador;

    //Objeto para cargar el certificado
    public static TrustManager[] trustAllCerts;

    //Objeto SSL
    public static SSLContext sslContext;

    //Filtros películas
    public static final int TODAS=0,AÑO=1,VECESALQUILADA=2,AÑO_VECESALQUILADA=3,DIRECTORGENERO=4;
    public static int filtroPeliculas=0;
    public static String director=null;
    public static String genero=null;
    public static int año=0;
    public static int vecesAlquilada=0;
    public static String ordenarPeliculasPor=null;

}
