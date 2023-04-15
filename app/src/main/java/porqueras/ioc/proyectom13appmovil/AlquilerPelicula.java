package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import porqueras.ioc.proyectom13appmovil.modelos.DetalleAlquiler;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaInfoResponse;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla donde se realiza el alquiler de la película
 *
 * @Author Esteban Porqueras Araque
 */
public class AlquilerPelicula extends AppCompatActivity {
    private String idUsuario, idPelicula;
    private TextView tituloPeliculaAlquilada, directorPeliculaAlquilada, duracionPeliculaAlquilada, añoPeliculaAlquilada, precioPeliculaAlquilada;
    private Button alquilarPelicula;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alquiler_pelicula);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Movies4Rent");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setSubtitle("Alquiler de películas");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_baseline_video_call_24);

        //Recuperamos el idUsuario e idPelicula
        Bundle extras = getIntent().getExtras();
        idUsuario = extras.getString("idUsuario");
        idPelicula = extras.getString("idPelicula");

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Añadimos el texto y el botón
        alquilarPelicula = (Button) findViewById(R.id.buttonAlquilar);
        tituloPeliculaAlquilada = (TextView) findViewById(R.id.textViewTituloPeliculaAlquilada);
        directorPeliculaAlquilada = (TextView) findViewById(R.id.textViewDirectorAlquiler);
        duracionPeliculaAlquilada = (TextView) findViewById(R.id.textViewDuracionPeliculaAlquiler);
        añoPeliculaAlquilada = (TextView) findViewById(R.id.textViewAñoPeliculaAlquiler);
        precioPeliculaAlquilada = (TextView) findViewById(R.id.textViewPrecioPeliculaAlquiler);

        //Conseguimos la información de la película y la ponemos en pantalla
        Call<PeliculaInfoResponse> informacionPelicula = apiService.getPelicula(idPelicula, ApiUtils.TOKEN);
        informacionPelicula.enqueue(new Callback<PeliculaInfoResponse>() {
            @Override
            public void onResponse(Call<PeliculaInfoResponse> call, Response<PeliculaInfoResponse> response) {
                if (response.isSuccessful()) {
                    tituloPeliculaAlquilada.setText(response.body().getValue().getTitulo());
                    directorPeliculaAlquilada.setText("Director: " + response.body().getValue().getDirector());
                    duracionPeliculaAlquilada.setText("Duración " + String.valueOf(response.body().getValue().getDuracion()) + " minutos");
                    añoPeliculaAlquilada.setText("Año: " + String.valueOf(response.body().getValue().getAño()));
                    precioPeliculaAlquilada.setText("Precio de alquiler: " + String.valueOf(response.body().getValue().getPrecio()) + " euros");
                } else {
                    Log.d("response", "La información de la peícula no ha sido conseguida, código: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<PeliculaInfoResponse> call, Throwable t) {
                Log.d("response", "Error en la red: " + t.getMessage());
            }
        });


        //Acción del botón Alquilar Película
        alquilarPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlquilerPelicula.this);
                builder.setTitle("Alquiler de películas");
                builder.setMessage("¿Está seguro de que desea alquilar esta película?");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Alquila la pelúcula y recibe los detalles del alquiler
                        Call<DetalleAlquiler> nuevoAlquiler = apiService.newAlquiler(idPelicula, idUsuario, ApiUtils.TOKEN);
                        nuevoAlquiler.enqueue(new Callback<DetalleAlquiler>() {
                            @Override
                            public void onResponse(Call<DetalleAlquiler> call, Response<DetalleAlquiler> response) {
                                if (response.isSuccessful()) {
                                    //Mostramos la fecha de devolución
                                    String fechaFin = response.body().getValue().getFechaFin();
                                    AlertDialog.Builder mensaje = new AlertDialog.Builder(AlquilerPelicula.this);
                                    mensaje.setTitle("Atención");
                                    mensaje.setMessage("La fecha límite para devolver la película" +
                                            " es el " + fechaFin);
                                    mensaje.setPositiveButton("De acuerdo", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    });
                                    AlertDialog msg = mensaje.create();
                                    msg.show();
                                    Log.d("response", "Película alquilada");
                                    //Muestra un Toast conforma la película ha sido alquilada
                                    Context context = getApplicationContext();
                                    Toast toast = Toast.makeText(context, "La película ha sido alquilada", Toast.LENGTH_SHORT);
                                    toast.show();
                                } else {
                                    Log.d("response", "Hubo un error al alquilar la película, código=" + response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<DetalleAlquiler> call, Throwable t) {
                                Log.d("response", "Error en la red: " + t.getMessage());
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
}