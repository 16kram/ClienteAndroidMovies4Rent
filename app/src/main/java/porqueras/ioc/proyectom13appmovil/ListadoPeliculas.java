package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;

import porqueras.ioc.proyectom13appmovil.modelos.PeliculaListaResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioListaResponse;
import porqueras.ioc.proyectom13appmovil.secciones.usuarios.ListadoUsuarios;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import porqueras.ioc.proyectom13appmovil.utilidades.WordListAdadpter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoPeliculas extends AppCompatActivity implements PeliculasListAdapter.PasarIdListado {
    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private PeliculasListAdapter mAdapter;
    APIService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_peliculas);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Listado de películas");

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        Call<PeliculaListaResponse> callPeliculaListaResponse = apiService.getPeliculas(ApiUtils.TOKEN);
        callPeliculaListaResponse.enqueue(new Callback<PeliculaListaResponse>() {
            @Override
            public void onResponse(Call<PeliculaListaResponse> call, Response<PeliculaListaResponse> response) {
                if (response.isSuccessful()) {
                    for (int n = 0; n < response.body().getValue().size(); n++) {
                        //Obtiene las películas y las añade a la lista
                        mWordList.add("Título: " + response.body().getValue().get(n).getTitulo() +
                                "\nGénero: " + response.body().getValue().get(n).getGenero() +
                                "\nDirector: " + response.body().getValue().get(n).getDirector() +
                                "\nDuración: " + response.body().getValue().get(n).getDuracion() +
                                "\nAño: " + response.body().getValue().get(n).getAño() +
                                "\nPrecio: " + response.body().getValue().get(n).getPrecio());
                    }

                    //Obtiene un identificador para la vista del RecyclerView
                    mRecyclerView = findViewById(R.id.recyclerviewPeliculas);
                    //Crea un adaptador para proporcionar los datos mostrados
                    mAdapter = new PeliculasListAdapter(ListadoPeliculas.this, mWordList, ListadoPeliculas.this);
                    //Conecta el adaptador con el RecyclerView
                    mRecyclerView.setAdapter(mAdapter);
                    //Da al RecyclerView un layout manager por defecto
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(ListadoPeliculas.this));

                } else {
                    Log.d("response", "error listando películas, código=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<PeliculaListaResponse> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        });


    }

    @Override
    public void pasarPosicionListado(int posicion) {

    }
}