package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import porqueras.ioc.proyectom13appmovil.modelos.LogoutResponse;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantallaUsuario extends AppCompatActivity {
    APIService apiService;
    Button botonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_usuario);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Pantalla provisional de usuario");

        //Añadimos los campos de texto y los botones
        botonLogout = (Button) findViewById(R.id.buttonLogout);

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Acción del botón Logout
        botonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<LogoutResponse> callLogoutResponse = apiService.getToken(ApiUtils.TOKEN);
                callLogoutResponse.enqueue(new Callback<LogoutResponse>() {
                    @Override
                    public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("response", "Logout Correcto, código=" + response.code());
                            //Finaliza la sesión en el servidor y muestra un Toast
                            Context context = getApplicationContext();
                            Toast toast = Toast.makeText(context, "Fin de sesión", Toast.LENGTH_SHORT);
                            toast.show();
                            finish();
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
        });
    }
}