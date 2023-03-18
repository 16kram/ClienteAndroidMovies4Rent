package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import porqueras.ioc.proyectom13appmovil.modelos.UsuarioInfoResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioListaResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioResponse;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoUsuarios extends AppCompatActivity {
    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WordListAdadpter mAdapter;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_usuarios);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Listado de usuarios");

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        Call<UsuarioListaResponse> callUsuarioInfoResponse = apiService.getUsuario(ApiUtils.TOKEN);
        callUsuarioInfoResponse.enqueue(new Callback<UsuarioListaResponse>() {
            @Override
            public void onResponse(Call<UsuarioListaResponse> call, Response<UsuarioListaResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("response", "usuario=" + response.body().getValue().get(0).getNombre());
                    for (int n = 0; n < response.body().getValue().size(); n++) {
                        //Obtiene los usuarios y los añade a la lista
                        mWordList.add("Usuario: " + response.body().getValue().get(n).getNombre() +
                                " " + response.body().getValue().get(n).getApellidos());

                        //Obtiene un identificador para la vista del RecyclerView
                        mRecyclerView = findViewById(R.id.recyclerview);
                        //Crea un adaptador para proporcionar los datos mostrados
                        mAdapter = new WordListAdadpter(ListadoUsuarios.this, mWordList);
                        //Conecta el adaptador con el RecyclerView
                        mRecyclerView.setAdapter(mAdapter);
                        //Da al RecyclerView un layout manager por defecto
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(ListadoUsuarios.this));
                    }
                } else {
                    Log.d("response", "error usuario " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UsuarioListaResponse> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        });

        /*
        //Obtiene un identificador para la vista del RecyclerView
        mRecyclerView = findViewById(R.id.recyclerview);
        //Crea un adaptador para proporcionar los datos mostrados
        mAdapter = new WordListAdadpter(this, mWordList);
        //Conecta el adaptador con el RecyclerView
        mRecyclerView.setAdapter(mAdapter);
        //Da al RecyclerView un layout manager por defecto
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));*/
    }
}