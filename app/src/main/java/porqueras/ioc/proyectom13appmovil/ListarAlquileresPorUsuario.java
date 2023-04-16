package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

import porqueras.ioc.proyectom13appmovil.modelos.AlquilerListaResponse;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaInfoResponse;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaListaResponse;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaListaResponseAlquilerPorId;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioInfoResponse;
import porqueras.ioc.proyectom13appmovil.secciones.peliculas.ListadoPeliculas;
import porqueras.ioc.proyectom13appmovil.utilidades.AlquilerListAdapter;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import porqueras.ioc.proyectom13appmovil.utilidades.PeliculasListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para listar las películas alquiladas por el usuario
 *
 * @Author Esteban Porqueras Araque
 */
public class ListarAlquileresPorUsuario extends AppCompatActivity implements PeliculasListAdapter.PasarIdListado {
    private LinkedList<Pelicula> peliculas = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private PeliculasListAdapter mAdapter;
    APIService apiService;
    HashMap hashMap = new HashMap();
    private String idUsuario;//Identificador del usuario
    private String id;//Identificador de la película

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_peliculas);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la accion a realizar en la barra superior de la activity
        setTitle("Movies4Rent");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Películas alquiladas");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_baseline_ondemand_video_24);

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Recuperamos el id del usuario
        Bundle extras = getIntent().getExtras();
        idUsuario = extras.getString("idUsuario");

        //Mostramos las peliculas alquiladas del usuario
        Call<PeliculaListaResponseAlquilerPorId> listadoPelicualasAlquiladas = apiService.getPeliculasAlquilerPorUsuario(idUsuario, ApiUtils.TOKEN);
        listadoPelicualasAlquiladas.enqueue(new Callback<PeliculaListaResponseAlquilerPorId>() {
            @Override
            public void onResponse(Call<PeliculaListaResponseAlquilerPorId> call, Response<PeliculaListaResponseAlquilerPorId> response) {
                if (response.isSuccessful()) {
                    for (int n = 0; n < response.body().getValue().size(); n++) {
                        //Obtiene las películas y las añade a la lista
                        Pelicula pelicula = new Pelicula();
                        pelicula.setTituloPelicula(response.body().getValue().get(n).getTitulo());
                        pelicula.setPrecioAlquiler(response.body().getValue().get(n).getPrecio());
                        peliculas.add(pelicula);
                    }

                    //Asociamos el id con el número de la posición de la lista
                    for (int n = 0; n < peliculas.size(); n++) {
                        hashMap.put(n, response.body().getValue().get(n).getId());
                        Log.d("respose", "response hasMap n=" + n + " id=" + hashMap.get(n) + " " +
                                response.body().getValue().get(n).getTitulo());
                    }

                    //Obtiene un identificador para la vista del RecyclerView
                    mRecyclerView = findViewById(R.id.recyclerviewPeliculas);
                    //Crea un adaptador para proporcionar los datos mostrados
                    mAdapter = new PeliculasListAdapter(ListarAlquileresPorUsuario.this, peliculas, ListarAlquileresPorUsuario.this);
                    //Conecta el adaptador con el RecyclerView
                    mRecyclerView.setAdapter(mAdapter);
                    //Da al RecyclerView un layout manager por defecto
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(ListarAlquileresPorUsuario.this));
                } else {
                    Log.d("response", "Ha ocurrido un error, código=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<PeliculaListaResponseAlquilerPorId> call, Throwable t) {
                Log.d("response", "Ha ocurrido un error");
            }
        });

        //Ocultamos el texto que indica el número de página,
        //y los botones de avanzar y atrasar ya que en esta pantalla no hay paginación
        Button botonAtrasar = (Button) findViewById(R.id.buttonAtrasarPaginaPelicula);
        botonAtrasar.setVisibility(View.GONE);
        Button botonAdelantar = (Button) findViewById(R.id.buttonAvanzarPaginaPelicula);
        botonAdelantar.setVisibility(View.GONE);
        TextView numPag = (TextView) findViewById(R.id.textViewNumPaginaPelicula);
        numPag.setVisibility(View.GONE);

    }

    @Override
    public void pasarPosicionListado(int posicion) {
        id = (String) hashMap.get(posicion);
        Intent i = new Intent(this, DetallePelicula.class);
        i.putExtra("idPelicula", id);//Enviamos el número de id de la película
        startActivity(i);
    }
}