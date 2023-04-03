package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;

import porqueras.ioc.proyectom13appmovil.modelos.AlquilerListaResponse;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaInfoResponse;
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
 * Pantalla para listar alquileres
 *
 * @Author Esteban Porqueras Araque
 */
public class ListadoAlquileres extends AppCompatActivity {
    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private AlquilerListAdapter mAdapter;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alquileres);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Gestión alquiler películas");


        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Hacemos una petición al servidor para que nos envíe una lista con los alquileres
        Call<AlquilerListaResponse> callListaAlquileres = apiService.getAlquileres(ApiUtils.TOKEN);
        callListaAlquileres.enqueue(new Callback<AlquilerListaResponse>() {
            @Override
            public void onResponse(Call<AlquilerListaResponse> call, Response<AlquilerListaResponse> response) {
                if (response.isSuccessful()) {
                    for (int n = 0; n < response.body().getValue().size(); n++) {
                        //Obtenemos el id del usuario
                        String idUsuario = response.body().getValue().get(n).getUsuariId();
                        //Obtenemos el id de la película
                        String idPelicula = response.body().getValue().get(n).getPeliculaId();
                        //Hacemos una petición al servidor para que nos envíe el nombre y apellidos del usuario
                        Call<UsuarioInfoResponse> callMostrarUsuario = apiService.getUsuarioId(idUsuario, ApiUtils.TOKEN);
                        callMostrarUsuario.enqueue(new Callback<UsuarioInfoResponse>() {
                            @Override
                            public void onResponse(Call<UsuarioInfoResponse> call, Response<UsuarioInfoResponse> response) {
                                if (response.isSuccessful()) {
                                    String usuario = response.body().getValue().getNombre() + " " +
                                            response.body().getValue().getApellidos();
                                    //Hacemos una petición al servidor para que nos muestre la película
                                    Call<PeliculaInfoResponse> callMostrarPelicula = apiService.getPelicula(idPelicula, ApiUtils.TOKEN);
                                    callMostrarPelicula.enqueue(new Callback<PeliculaInfoResponse>() {
                                        @Override
                                        public void onResponse(Call<PeliculaInfoResponse> call, Response<PeliculaInfoResponse> response) {
                                            if (response.isSuccessful()) {
                                                String pelicula = response.body().getValue().getTitulo();
                                                Log.d("response", "Usuario= " + usuario + "-->Película: " + pelicula);
                                                mWordList.add("Usuario: "+usuario+"\n\nPelícula:\n"+pelicula);

                                                //Obtiene un identificador para la vista del RecyclerView
                                                mRecyclerView = findViewById(R.id.recyclerviewAlquileres);
                                                //Crea un adaptador para proporcionar los datos mostrados
                                                mAdapter = new AlquilerListAdapter(ListadoAlquileres.this, mWordList);
                                                //Conecta el adaptador con el RecyclerView
                                                mRecyclerView.setAdapter(mAdapter);
                                                //Da al RecyclerView un layout manager por defecto
                                                mRecyclerView.setLayoutManager(new LinearLayoutManager(ListadoAlquileres.this));
                                            } else {
                                                Log.d("response", "Ha ocurrido un error obteniendo película, código=" + response.code());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<PeliculaInfoResponse> call, Throwable t) {
                                            Log.d("response", "Error de red: " + t.getMessage());
                                        }
                                    });
                                } else {
                                    Log.d("response", "Ha ocurrido un error obteniendo usuario, código=" + response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<UsuarioInfoResponse> call, Throwable t) {
                                Log.d("response", "Error de red: " + t.getMessage());
                            }
                        });

                    }
                } else {
                    Log.d("response", "Ocurrió un error al listar los alquileres, código=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<AlquilerListaResponse> call, Throwable t) {
                Log.d("response", "Error de red: " + t.getMessage());
            }
        });
    }
}