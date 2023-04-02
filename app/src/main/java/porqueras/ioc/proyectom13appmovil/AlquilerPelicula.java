package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlquilerPelicula extends AppCompatActivity {
    private String idUsuario, idPelicula;
    private Button alquilarPelicula;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alquiler_pelicula);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Alquiler de películas");

        //Recuperamos el idUsuario e idPelicula
        Bundle extras = getIntent().getExtras();
        idUsuario = extras.getString("idUsuario");
        idPelicula = extras.getString("idPelicula");

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Añadimos el texto y el botón
        alquilarPelicula = (Button) findViewById(R.id.buttonAlquilar);


        //Acción del botón Alquilar Película
        alquilarPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlquilerPelicula.this);
                builder.setTitle("Alquiler de películas");
                builder.setMessage("¿Estás seguro de que deseas alquilar esta película?");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<Void> callNuevoAlquiler = apiService.nuevoAlquiler(idPelicula, idUsuario, ApiUtils.TOKEN);
                        callNuevoAlquiler.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Log.d("response", "Película alquilada");
                                } else {
                                    Log.d("response", "Hubo un error al alquilar la película, código=" + response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.d("response", "Error en la red: " + t.getMessage());
                            }
                        });
                        finish();
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