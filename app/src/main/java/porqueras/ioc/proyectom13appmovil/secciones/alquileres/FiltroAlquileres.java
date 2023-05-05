package porqueras.ioc.proyectom13appmovil.secciones.alquileres;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import porqueras.ioc.proyectom13appmovil.APIService;
import porqueras.ioc.proyectom13appmovil.R;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaInfoResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioInfoResponse;
import porqueras.ioc.proyectom13appmovil.secciones.peliculas.ListadoPeliculas;
import porqueras.ioc.proyectom13appmovil.secciones.usuarios.ListadoUsuarios;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para filtrar las alquileres
 *
 * @Author Esteban Porqueras Araque
 */
public class FiltroAlquileres extends AppCompatActivity {
    private APIService apiService;
    private Spinner ordenAlquiler;
    private Button botonUsuario, botonPelicula, botonFiltrarAlquiler;
    private TextView usuario, pelicula;
    private EditText fechaInicio, fechaFin, precio;
    private String idUsuario, idPelicula;
    private String accion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_alquileres);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Añadimos el título de la Activity en la barra superior
        setTitle("Movies4Rent");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Filtrar alquileres");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_baseline_ondemand_video_24);

        //Añadimos los campos de texto y los botones
        usuario = (TextView) findViewById(R.id.textViewUsuarioId);
        pelicula = (TextView) findViewById(R.id.textViewPeliculaId);
        fechaInicio = (EditText) findViewById(R.id.editTextFiltroAlquilerFechaInicio);
        fechaFin = (EditText) findViewById(R.id.editTextFiltroAlquilerFechaFin);
        precio = (EditText) findViewById(R.id.editTextFiltroAlquilerPrecio);
        botonUsuario = (Button) findViewById(R.id.buttonUsuarioId);
        botonPelicula = (Button) findViewById(R.id.buttonPeliculaId);
        botonFiltrarAlquiler = (Button) findViewById(R.id.buttonFiltrarAlquiler);
        ordenAlquiler = (Spinner) findViewById(R.id.spinnerOrdenAlquiler);

        //Recuperamos la acción a ejecutar de la actividad anterior
        Bundle extras = getIntent().getExtras();
        accion = extras.getString("accion");
        Log.d("response", "accion=" + accion);

        //Acción del botón Usuario
        botonUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Seleccionamos el usuario
                Intent i = new Intent(FiltroAlquileres.this, ListadoUsuarios.class);
                i.putExtra("accion", "alquilar");
                startActivityForResult(i, 4212);
            }
        });

        //Acción del botón película
        botonPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Seleccionamos la película
                Intent i = new Intent(FiltroAlquileres.this, ListadoPeliculas.class);
                i.putExtra("accion", "alquilar");
                startActivityForResult(i, 4213);
            }
        });

        //Acción del botón filtrar
        botonFiltrarAlquiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Filtramos por campos de texto y números
                    ApiUtils.precio = Integer.parseInt(precio.getText().toString());
                    ApiUtils.filtroAlquileres = ApiUtils.FILTRO_PRECIO;
                } catch (Exception e) {
                    //Filtramos sólo por campos de texto
                    ApiUtils.filtroAlquileres = ApiUtils.FILTRO_STRING;
                }

                //Si en los campos no hay texto las variables pasan a null
                if (idUsuario != null) {
                    ApiUtils.usuario = idUsuario;
                }
                if (idPelicula != null) {
                    ApiUtils.pelicula = idPelicula;
                }
                if (fechaInicio.getText().toString().equals("")) {
                    ApiUtils.fechaInicio = null;
                }
                if (fechaFin.getText().toString().equals("")) {
                    ApiUtils.fechaFin = null;
                }

                Log.d("response", "Número del filtro de alquiler=" + ApiUtils.filtroAlquileres);
                Log.d("response", "usuario=" + ApiUtils.usuario);
                Log.d("response", "pelicula=" + ApiUtils.pelicula);
                Log.d("response", "fechaInicio=" + ApiUtils.fechaInicio);
                Log.d("response", "fechaFin=" + ApiUtils.fechaFin);
                Log.d("response", "ordenar por=" + ApiUtils.ordenarAlquileresPor);

                Intent i = new Intent(FiltroAlquileres.this, ListadoAlquileres.class);
                i.putExtra("accion", accion);
                startActivity(i);
            }
        });

        //Acción del Spinner
        ordenAlquiler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("response", "Ha seleccionado " + parent.getItemAtPosition(position).toString());
                ApiUtils.ordenarAlquileresPor = parent.getItemAtPosition(position).toString();
                if (ApiUtils.ordenarAlquileresPor.equals("Ninguno")) {
                    ApiUtils.ordenarAlquileresPor = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * Desde el RecyclerView se nos devuelve el id del usuario y el id de la película
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Recibimos el id del usuario para poder filtrar el alquiler
        if (requestCode == 4212 & resultCode == RESULT_OK) {
            idUsuario = data.getExtras().getString("idUsuario");
            Log.d("response", "(FiltroAlquileres)idUsuario=" + idUsuario);
            Call<UsuarioInfoResponse> infoResponseCall = apiService.getUsuarioId(idUsuario, ApiUtils.TOKEN);
            infoResponseCall.enqueue(new Callback<UsuarioInfoResponse>() {
                @Override
                public void onResponse(Call<UsuarioInfoResponse> call, Response<UsuarioInfoResponse> response) {
                    if (response.isSuccessful()) {
                        usuario.setText(response.body().getValue().getNombre() + " " +
                                response.body().getValue().getApellidos());
                    } else {
                        Log.d("response", "Error buscando el usuario, código=" + response.code());
                    }
                }

                @Override
                public void onFailure(Call<UsuarioInfoResponse> call, Throwable t) {
                    Log.d("response", "Error de red-->" + t.getMessage());
                }
            });
        }
        //Recibimos el id de la película para poder filtrar el alquiler
        if (requestCode == 4213 & resultCode == RESULT_OK) {
            idPelicula = data.getExtras().getString("idPelicula");
            Log.d("response", "(FiltroAlquileres)idPelicula=" + idPelicula);
            Call<PeliculaInfoResponse> peliculaInfoResponseCall = apiService.getPelicula(idPelicula, ApiUtils.TOKEN);
            peliculaInfoResponseCall.enqueue(new Callback<PeliculaInfoResponse>() {
                @Override
                public void onResponse(Call<PeliculaInfoResponse> call, Response<PeliculaInfoResponse> response) {
                    if (response.isSuccessful()) {
                        pelicula.setText(response.body().getValue().getTitulo());
                    } else {
                        Log.d("response", "Error buscando la película, código=" + response.code());
                    }
                }

                @Override
                public void onFailure(Call<PeliculaInfoResponse> call, Throwable t) {
                    Log.d("response", "Error de red-->" + t.getMessage());
                }
            });
        }
    }


    /**
     * Si se sale de la actividad se pone el filtro para que en el RecyclerView
     * se puedan visualizar todos los alquileres
     */
    protected void onDestroy() {
        super.onDestroy();
        ApiUtils.filtroAlquileres = ApiUtils.TODO;
        ApiUtils.usuario = null;
        ApiUtils.pelicula = null;
    }

}
