package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import porqueras.ioc.proyectom13appmovil.modelos.PeliculaInfoResponse;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla que muestra el detalle de la película
 *
 * @Author Esteban Porqueras Araque
 */
public class DetallePelicula extends AppCompatActivity {
    private TextView tituloView, generoView, directorView, duracionView, añoView, precioView;
    private String idPelicula;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pelicula);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Detalle de la película");

        //Añadimos el texto
        tituloView = (TextView) findViewById(R.id.textViewDetalleTitulo);
        generoView = (TextView) findViewById(R.id.textViewDetalleGenero);
        directorView = (TextView) findViewById(R.id.textViewDetalleDirector);
        duracionView = (TextView) findViewById(R.id.textViewDetalleDuracion);
        añoView = (TextView) findViewById(R.id.textViewDetalleAño);
        precioView = (TextView) findViewById(R.id.textViewDetallePrecio);

        //Recogemos los datos de la película enviados por la clase ListadoPeliculas
        Bundle extras = getIntent().getExtras();
        idPelicula = extras.getString("idPelicula");

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        Call<PeliculaInfoResponse> callDetallePelicula = apiService.getPelicula(idPelicula, ApiUtils.TOKEN);
        callDetallePelicula.enqueue(new Callback<PeliculaInfoResponse>() {
            @Override
            public void onResponse(Call<PeliculaInfoResponse> call, Response<PeliculaInfoResponse> response) {
                if (response.isSuccessful()) {
                    tituloView.setText(response.body().getValue().getTitulo());
                    generoView.setText("Género: " + response.body().getValue().getGenero());
                    directorView.setText("Director: " + response.body().getValue().getDirector());
                    duracionView.setText("Duración: " + Integer.toString(response.body().getValue().getDuracion()));
                    añoView.setText("Año: " + Integer.toString(response.body().getValue().getAño()));
                    precioView.setText("Precio: " + Integer.toString(response.body().getValue().getPrecio()));
                }
            }

            @Override
            public void onFailure(Call<PeliculaInfoResponse> call, Throwable t) {

            }
        });

    }
}