package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import porqueras.ioc.proyectom13appmovil.modelos.UsuarioInfoResponse;
import porqueras.ioc.proyectom13appmovil.secciones.peliculas.GestionPeliculas;
import porqueras.ioc.proyectom13appmovil.secciones.usuarios.GestionUsuarios;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import porqueras.ioc.proyectom13appmovil.utilidades.Logout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla del menú del administrador
 *
 * @author Esteban Porqueras Araque
 */
public class PantallaAdministrador extends AppCompatActivity {
    private APIService apiService;
    private Button botonLogoutAdmin;
    private Button botonGestionUsuarios;
    private Button botonGestionPeliculas;
    private Button botonGestionAlquiler;
    private TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_administrador);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos los campos de texto y los botones
        titulo = (TextView) findViewById(R.id.textViewTituloAdministrador);
        botonLogoutAdmin = (Button) findViewById(R.id.buttonLogoutAdmin);
        botonGestionUsuarios = (Button) findViewById(R.id.buttonGestionUsuarios);
        botonGestionPeliculas = (Button) findViewById(R.id.buttonGestionPeliculas);
        botonGestionAlquiler = (Button) findViewById(R.id.buttonGestionAlquilerPeliculas);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Movies4Rent");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setSubtitle("Menú del administrador");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_baseline_admin_panel_settings_24);

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Mostramos un mensaje de bienvenida con el nombre del usuario
        Call<UsuarioInfoResponse> usuarioInfoResponseCall = apiService.getValue(ApiUtils.TOKEN);
        usuarioInfoResponseCall.enqueue(new Callback<UsuarioInfoResponse>() {
            @Override
            public void onResponse(Call<UsuarioInfoResponse> call, Response<UsuarioInfoResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("response", "Nombre usuario=" + response.body().getValue().getNombre());
                    titulo.setText("Bienvenido a MOVIES4RENT \n" + response.body().getValue().getNombre() + " eres ADMINISTRADOR");
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
        botonLogoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Instanciomos la incerfaz de APIService mediante Retrofit
                APIService apiService = InstanciaRetrofit.getApiService();
                //Llamamos a Logout.sesion para cerrar la sesión
                Logout.sesion(apiService, getApplicationContext());
                //Cerramos la actividad actual
                finish();

            }
        });

        //Acción del botón de gestión de usuarios
        botonGestionUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PantallaAdministrador.this, GestionUsuarios.class);
                startActivity(i);
            }
        });

        //Acción del botón de gestión de películas
        botonGestionPeliculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PantallaAdministrador.this, GestionPeliculas.class);
                startActivity(i);
            }
        });

        //Acción del botón de gestión del alquiler de las películas
        botonGestionAlquiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PantallaAdministrador.this, GestionAlquilerPeliculas.class);
                startActivity(i);
            }
        });

    }

    /**
     * Si se sale del menú de administrador se cierra la sesión
     */
    protected void onDestroy() {
        super.onDestroy();
        //Instanciomos la incerfaz de APIService mediante Retrofit
        APIService apiService = InstanciaRetrofit.getApiService();
        //Llamamos a Logout.sesion para cerrar la sesión
        Logout.sesion(apiService, getApplicationContext());
    }
}