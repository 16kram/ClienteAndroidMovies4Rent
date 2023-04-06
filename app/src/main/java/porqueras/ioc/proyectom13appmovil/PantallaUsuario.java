package porqueras.ioc.proyectom13appmovil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import porqueras.ioc.proyectom13appmovil.modelos.UsuarioInfoResponse;
import porqueras.ioc.proyectom13appmovil.secciones.peliculas.ListadoPeliculas;
import porqueras.ioc.proyectom13appmovil.secciones.usuarios.CambiarPassword;
import porqueras.ioc.proyectom13appmovil.secciones.usuarios.ModificarUsuario;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import porqueras.ioc.proyectom13appmovil.utilidades.Logout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla del menú del usuario
 *
 * @Author Esteban Porqueras Araque
 */
public class PantallaUsuario extends AppCompatActivity {
    private APIService apiService;
    private Button botonLogout, modificarDatos, modificarPassword, botonAlquilarPelicula, botonVerPeliculasAlquiladas;
    private TextView titulo;
    private String id;//Identificador del usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_usuario);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Menú del usuario");

        //Añadimos los campos de texto y los botones
        titulo = (TextView) findViewById(R.id.textViewTituloUsuario);
        botonLogout = (Button) findViewById(R.id.buttonLogout);
        modificarDatos = (Button) findViewById(R.id.buttonModificarDatos);
        modificarPassword = (Button) findViewById(R.id.buttonModificarPassword);
        botonAlquilarPelicula = (Button) findViewById(R.id.buttonUsuarioAlquilar);
        botonVerPeliculasAlquiladas = (Button) findViewById(R.id.buttonVerAlquileres);

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
                    id = response.body().getValue().getId();
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
                //Llamamos a Logout.sesion para cerrar la sesión
                Logout.sesion(apiService, getApplicationContext());
                //Cerramos la actividad actual
                finish();

            }
        });

        //Acción del botón modificar datos
        modificarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PantallaUsuario.this, ModificarUsuario.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });

        //Acción del botón modificar Contraseña
        modificarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PantallaUsuario.this, CambiarPassword.class);
                startActivity(i);
            }
        });

        //Acción del botón Alquilar Película
        botonAlquilarPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PantallaUsuario.this);
                builder.setTitle("Atención");
                builder.setMessage("Seleccione una película de la siguiente lista");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(PantallaUsuario.this, ListadoPeliculas.class);
                        i.putExtra("accion", "alquilar");
                        startActivityForResult(i, 9876);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        //Acción del botón ver películas alquiladas del usuario
        botonVerPeliculasAlquiladas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * Recibimos el id de la lista de películas
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9876 & resultCode == RESULT_OK) {
            String idPelicula = data.getExtras().getString("idPelicula");
            Log.d("response", "idPelícula=" + idPelicula);
            //Enviamos los datos de idUsuario e idPelícula a la actividad de AlquilerPelicula
            Intent i = new Intent(PantallaUsuario.this, AlquilerPelicula.class);
            i.putExtra("idUsuario", id);
            i.putExtra("idPelicula", idPelicula);
            startActivity(i);
        }
    }

    /**
     * Si se sale del menú de usuarios se cierra la sesión
     */
    protected void onDestroy() {
        super.onDestroy();
        //Instanciomos la incerfaz de APIService mediante Retrofit
        APIService apiService = InstanciaRetrofit.getApiService();
        //Llamamos a Logout.sesion para cerrar la sesión
        Logout.sesion(apiService, getApplicationContext());
    }
}