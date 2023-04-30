package porqueras.ioc.proyectom13appmovil.utilidades;

import static android.os.Build.VERSION_CODES.R;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import porqueras.ioc.proyectom13appmovil.APIService;
import porqueras.ioc.proyectom13appmovil.MainActivity;
import porqueras.ioc.proyectom13appmovil.PantallaUsuario;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.NullConverterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase para obtener una instancia de APIService mediante retrofit
 *
 * @author Esteban Porqueras Araque
 */
public class InstanciaRetrofit {
    private static Retrofit retrofit = null;

    /**
     * Se obtiene una instancia de APIService para interactuar con el servidor
     *
     * @return APIService instancia para interactuar con el servidor.
     */
    public static APIService getApiService() {
        if (retrofit == null) {
            //Configuración de la conexión de red
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(ApiUtils.sslContext.getSocketFactory(), (X509TrustManager) ApiUtils.trustAllCerts[0])
                    .hostnameVerifier((hostname, session) -> true)
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .build();

            //Creamos la instancia de Gson y Retrofit
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-ddd HH:mm:ss")
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiUtils.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(new NullConverterFactory())//Si la cadena devuelta es nula o vacía evita el error-->End of input at line 1 column 1 path $
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(APIService.class);
    }
}
