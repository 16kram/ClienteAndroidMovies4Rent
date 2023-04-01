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
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaResponse;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para registrar una película
 *
 * @Author Esteban Porqueras Araque
 */
public class RegistroPelicula extends AppCompatActivity {
    EditText titulo, director, genero, duracion, year, precio;
    Button botonInsertarPelicula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_pelicula);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Registro de películas");

        //Añadimos los campos de texto y los botones
        titulo = (EditText) findViewById(R.id.editTextTituloPelicula);
        director = (EditText) findViewById(R.id.editTextDirectorPelicula);
        genero = (EditText) findViewById(R.id.editTextGeneroPelicula);
        duracion = (EditText) findViewById(R.id.editTextDuracionPelicula);
        year = (EditText) findViewById(R.id.editTextYearPelicula);
        precio = (EditText) findViewById(R.id.editTextPrecioDePelicula);
        botonInsertarPelicula = (Button) findViewById(R.id.buttonRegistroPelicula);

        //Instanciomos la incerfaz de APIService mediante Retrofit
        APIService apiService = InstanciaRetrofit.getApiService();

        //Accion del boton añadir
        botonInsertarPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PeliculaResponse peliculaResponse = new PeliculaResponse(
                        titulo.getText().toString(),
                        director.getText().toString(),
                        genero.getText().toString(),
                        Integer.parseInt(String.valueOf(duracion.getText().toString())),
                        Integer.parseInt(String.valueOf(year.getText().toString())),
                        Integer.parseInt(String.valueOf(precio.getText().toString())));

                Call<PeliculaResponse> callInsertaPelicula = apiService.setPelicula(ApiUtils.TOKEN, peliculaResponse);
                callInsertaPelicula.enqueue(new Callback<PeliculaResponse>() {
                    @Override
                    public void onResponse(Call<PeliculaResponse> call, Response<PeliculaResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("response", "película insertada");
                            //Inserta la película en el servidor y muestra un Toast
                            Toast toast = Toast.makeText(getBaseContext(), "Película insertada", Toast.LENGTH_LONG);
                            toast.show();
                            finish();
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