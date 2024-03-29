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

    //Filtros usuarios
    public static final int TODOS = 0, FILTROS = 1;
    public static int filtroUsuarios = 0;
    public static String nombre;
    public static String apellidos;
    public static String username;
    public static String ordenarUsuariosPor = "Ninguno";

    //Filtros películas
    public static final int TODAS = 0, AÑO = 1, VECESALQUILADA = 2, AÑO_VECESALQUILADA = 3, DIRECTORGENERO = 4;
    public static int filtroPeliculas = 0;
    public static String titulo;
    public static String director;
    public static String genero;
    public static int año = 0;
    public static int vecesAlquilada = 0;
    public static String ordenarPeliculasPor = "Ninguno";

    //Filtro alquileres
    public static final int TODO = 0, FILTRO_PRECIO = 1, FILTRO_STRING = 2;
    public static int filtroAlquileres = 0;
    public static String usuario;
    public static String pelicula;
    public static String fechaInicio;
    public static String fechaFin;
    public static int precio;
    public static String ordenarAlquileresPor = "Ninguno";

    //Filtro ranking
    public static final int TODOS_LOS_FILTROS = 0, SOLO_FILTRO_AÑO = 1, SOLO_FILTRO_STRING = 2;
    public static int filtroRanking = 0;
    public static String tituloRanking;
    public static String directorRanking;
    public static String generoRanking;
    public static int añoRanking;

    //Activa o desactiva la opción del menú para filtrar elementos en los RecyclerView
    public static boolean menuFiltrarUsuarios = true;
    public static boolean menuFiltrarPeliculas = true;
    public static boolean menuFiltrarAlquileres = true;
    public static boolean menuFiltrarRanking = true;

    //Identificador del usuario
    public static String idUsuario;

}
