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

public class Logout {
    public static boolean sesion = true;

    //Hace un logout
    //Devuelve false si se ha cerrado la sesión
    public static boolean sesion(APIService apiService, Context context) {
        Call<LogoutResponse> callLogoutResponse = apiService.getToken(ApiUtils.TOKEN);
        callLogoutResponse.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("response", "Logout Correcto, código=" + response.code());
                    //Finaliza la sesión en el servidor y muestra un Toast
                    //Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Fin de sesión", Toast.LENGTH_SHORT);
                    toast.show();
                    sesion = false;//Ha hecho un logout
                } else {
                    Log.d("response", "Ocurrió un error en la petición Logout, código=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        });
        return sesion;
    }
}
