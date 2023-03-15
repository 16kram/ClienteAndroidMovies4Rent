package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import porqueras.ioc.proyectom13appmovil.modelos.UsuarioInfoResponse;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import porqueras.ioc.proyectom13appmovil.utilidades.Logout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PantallaUsuario extends AppCompatActivity {
    private APIService apiService;
    private Button botonLogout;
    private TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_usuario);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Pantalla del usuario");

        //Añadimos los campos de texto y los botones
        titulo = (TextView) findViewById(R.id.textViewTituloUsuario);
        botonLogout = (Button) findViewById(R.id.buttonLogout);

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Mostramos un mensaje de bienvenida con el nombre del usuario
        Call<UsuarioInfoResponse> usuarioInfoResponseCall = apiService.getValue(ApiUtils.TOKEN);
        usuarioInfoResponseCall.enqueue(new Callback<UsuarioInfoResponse>() {
            @Override
            public void onResponse(Call<UsuarioInfoResponse> call, Response<UsuarioInfoResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("response", "Nombre usuario=" + response.body().getValue().getNombre());
                    titulo.setText("Bienvenido a MOVIES4RENT \n" + response.body().getValue().getNombre() + " eres USUARIO");
                } else {
                    Log.d("response", "Ocurrió un error al buscar el nombre de usuario, código=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<UsuarioInfoResponse> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        });

        //Acción del botón Logout
        botonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Instanciomos la incerfaz de APIService mediante Retrofit
                APIService apiService = InstanciaRetrofit.getApiService();
                //Si el método Logout.sesion devuelve false es que se ha cerrado la sesión
                if (!Logout.sesion(apiService, getApplicationContext())) {
                    //Cerramos la actividad actual
                    finish();
                }
            }
        });
    }
}