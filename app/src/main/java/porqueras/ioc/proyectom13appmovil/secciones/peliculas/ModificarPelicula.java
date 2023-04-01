package porqueras.ioc.proyectom13appmovil.secciones.peliculas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import porqueras.ioc.proyectom13appmovil.APIService;
import porqueras.ioc.proyectom13appmovil.R;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaInfoResponse;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaResponse;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para actualizar los datos de las películas
 *
 * @Author Esteban Porqueras Araque
 */
public class ModificarPelicula extends AppCompatActivity {
    APIService apiService;
    EditText tituloModificar, directorModificar, generoModificar, duracionModificar, yearModificar, precioModificar;
    Button botonModificarPelicula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_pelicula);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Modificar películas");

        //Recogemos el id de la película seleccionada el listado de las películas
        Bundle extras = getIntent().getExtras();
        String idPelicula = extras.getString("idPelicula");
        Log.d("response", "idPelicula desde la actividad ModificarPelicula=" + idPelicula);

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Añadimos los campos de texto y el botón
        tituloModificar = (EditText) findViewById(R.id.editTextTituloPeliculaModificar);
        directorModificar = (EditText) findViewById(R.id.editTextDirectorPeliculaModificar);
        generoModificar = (EditText) findViewById(R.id.editTextGeneroPeliculaModificar);
        duracionModificar = (EditText) findViewById(R.id.editTextDuracionPeliculaModificar);
        yearModificar = (EditText) findViewById(R.id.editTextYearPeliculaModificar);
        precioModificar = (EditText) findViewById(R.id.editTextPrecioDePeliculaModificar);
        botonModificarPelicula = (Button) findViewById(R.id.buttonRegistroPeliculaModificar);

        //Rellenamos los campos con la película seleccionada
        Call<PeliculaInfoResponse> callObtenerPelicula = apiService.getPelicula(idPelicula, ApiUtils.TOKEN);
        callObtenerPelicula.enqueue(new Callback<PeliculaInfoResponse>() {
            @Override
            public void onResponse(Call<PeliculaInfoResponse> call, Response<PeliculaInfoResponse> response) {
                if (response.isSuccessful()) {
                    tituloModificar.setText(response.body().getValue().getTitulo());
                    generoModificar.setText(response.body().getValue().getGenero());
                    directorModificar.setText(response.body().getValue().getDirector());
                    duracionModificar.setText(String.valueOf(response.body().getValue().getDuracion()));
                    yearModificar.setText(String.valueOf(response.body().getValue().getAño()));
                    precioModificar.setText(String.valueOf(response.body().getValue().getPrecio()));
                } else {
                    Log.d("response", "Hubo un problema con la obtención de la película, código=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<PeliculaInfoResponse> call, Throwable t) {
                Log.d("response", "Error de red");
            }
        });

        //Acción del botón Modificar
        botonModificarPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se actualizan los datos de la película
                PeliculaResponse peliculaResponse = new PeliculaResponse(
                        tituloModificar.getText().toString(),
                        directorModificar.getText().toString(),
                        generoModificar.getText().toString(),
                        Integer.parseInt(duracionModificar.getText().toString()),
                        Integer.parseInt(yearModificar.getText().toString()),
                        Integer.parseInt(precioModificar.getText().toString())
                );
                Call<PeliculaResponse> callActualizarPelicula = apiService.updatePelicula(idPelicula, ApiUtils.TOKEN, peliculaResponse);
                callActualizarPelicula.enqueue(new Callback<PeliculaResponse>() {
                    @Override
                    public void onResponse(Call<PeliculaResponse> call, Response<PeliculaResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("response", "Película actualizada");
                            //Modifica la película en el servidor y muestra un Toast
                            Toast toast = Toast.makeText(getBaseContext(), "Película actualizada", Toast.LENGTH_LONG);
                            toast.show();
                            finish();
                        } else {
                            Log.d("response", "Película no actualizada, código=" + response.code());
                            //No se puede modificar la película debido a un error en el servidor y muestra un Toast
                            Toast toast = Toast.makeText(getBaseContext(), "Película no actualizada"
                                    + "\nCódigo de error " + response.code(), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PeliculaResponse> call, Throwable t) {

                    }
                });

            }
        });

    }
}