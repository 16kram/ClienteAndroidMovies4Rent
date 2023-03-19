package porqueras.ioc.proyectom13appmovil.utilidades;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import porqueras.ioc.proyectom13appmovil.APIService;
import porqueras.ioc.proyectom13appmovil.modelos.LogoutResponse;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase para cerrar la sesión con el servidor
 *
 * @author Esteban Porqueras Araque
 */
public class Logout {

    /**
     * Cierra la sesión con el servidor
     *
     * @param apiService la interface de Retrofit.
     * @param context    el contexto de la aplicación.
     */
    public static void sesion(APIService apiService, Context context) {
        Call<LogoutResponse> callLogoutResponse = apiService.getToken(ApiUtils.TOKEN);
        callLogoutResponse.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("response", "Logout Correcto, código=" + response.code());
                    //Finaliza la sesión en el servidor y muestra un Toast
                    Toast toast = Toast.makeText(context, "Fin de sesión", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Log.d("response", "Ocurrió un error en la petición Logout, código=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        });
    }
}
