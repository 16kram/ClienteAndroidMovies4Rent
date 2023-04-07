package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;

import porqueras.ioc.proyectom13appmovil.modelos.AlquilerListaResponse;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaInfoResponse;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaListaResponse;
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
    private final LinkedList<String> mWordList = new LinkedList<>();
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
        setTitle("Peliculas alquiladas");

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Recuperamos el id del usuario
        Bundle extras = getIntent().getExtras();
        idUsuario = extras.getString("idUsuario");

        //Mostramos las peliculas alquiladas del usuario
        Call<PeliculaListaResponse> listadoPelicualasAlquiladas = apiService.getPeliculasAlquilerPorUsuario(idUsuario, ApiUtils.TOKEN);
        listadoPelicualasAlquiladas.enqueue(new Callback<PeliculaListaResponse>() {
            @Override
            public void onResponse(Call<PeliculaListaResponse> call, Response<PeliculaListaResponse> response) {
                if (response.isSuccessful()) {
                    for (int n = 0; n < response.body().getValue().size(); n++) {
                        //Obtiene las películas y las añade a la lista
                        mWordList.add("Película:\n" + response.body().getValue().get(n).getTitulo() +
                                "\n\nPrecio del alquiler: " + response.body().getValue().get(n).getPrecio() + " euros");
                    }

                    //Asociamos el id con el número de la posición de la lista
                    for (int n = 0; n < mWordList.size(); n++) {
                        hashMap.put(n, response.body().getValue().get(n).getId());
                        Log.d("respose", "response hasMap n=" + n + " id=" + hashMap.get(n) + " " +
                                response.body().getValue().get(n).getTitulo());
                    }

                    //Obtiene un identificador para la vista del RecyclerView
                    mRecyclerView = findViewById(R.id.recyclerviewPeliculas);
                    //Crea un adaptador para proporcionar los datos mostrados
                    mAdapter = new PeliculasListAdapter(ListarAlquileresPorUsuario.this, mWordList, ListarAlquileresPorUsuario.this);
                    //Conecta el adaptador con el RecyclerView
                    mRecyclerView.setAdapter(mAdapter);
                    //Da al RecyclerView un layout manager por defecto
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(ListarAlquileresPorUsuario.this));
                } else {
                    Log.d("response", "Ha ocurrido un error, código=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<PeliculaListaResponse> call, Throwable t) {
                Log.d("response", "Ha ocurrido un error");
            }
        });

    }

    @Override
    public void pasarPosicionListado(int posicion) {
        id = (String) hashMap.get(posicion);
        Intent i = new Intent(this, DetallePelicula.class);
        i.putExtra("idPelicula", id);//Enviamos el número de id de la película
        startActivity(i);
    }
}