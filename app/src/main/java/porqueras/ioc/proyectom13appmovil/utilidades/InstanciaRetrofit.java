package porqueras.ioc.proyectom13appmovil.utilidades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import porqueras.ioc.proyectom13appmovil.APIService;
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
