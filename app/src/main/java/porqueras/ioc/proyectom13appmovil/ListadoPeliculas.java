package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
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
    HashMap hashMap = new HashMap();
    private String accion;
    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_peliculas);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Recuperamos la acción a ejecutar de la actividad anterior
        Bundle extras = getIntent().getExtras();
        accion = extras.getString("accion");
        Log.d("response", "accion=" + accion);

        //Añadimos el título de la accion a realizar en la barra superior de la activity
        setTitle(accion.substring(0, 1).toUpperCase() + accion.substring(1) + " películas");


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

                    //Asociamos el id con el número de la posición de la lista
                    for (int n = 0; n < mWordList.size(); n++) {
                        hashMap.put(n, response.body().getValue().get(n).getId());
                        Log.d("respose", "response hasMap n=" + n + " id=" + hashMap.get(n) + " " +
                                response.body().getValue().get(n).getTitulo());
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

    /**
     * Recibe el número de posición que se ha seleccionado
     * en el listado de películas
     *
     * @param posicion
     */
    @Override
    public void pasarPosicionListado(int posicion) {
        Log.d("response", "response interface posición pulsada=" + posicion);
        id = (String) hashMap.get(posicion);

        switch (accion) {
            case "borrar":
                borrarPelicula(id);
                break;
            case "modificar":
                Intent i = new Intent();
                i.putExtra("idPelicula", id);//Devolvemos el número de id de la película
                setResult(RESULT_OK, i);
                finish();
                break;
        }
    }

    /**
     * Borra la película
     *
     * @param peliculaId se le pasa el identificador de la palícula la cual se va a borrar
     */
    private void borrarPelicula(String peliculaId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Estás seguro de que deseas borrar esta película?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Se ha elegido borrar la película
                Call<Void> peliculaBorrar = apiService.deletePelicula(peliculaId, ApiUtils.TOKEN);
                peliculaBorrar.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("response", "La película ha sido eliminada");
                            //Actualiza los datos del RecyclerView
                            mAdapter.notifyDataSetChanged();
                            Context context = getApplicationContext();
                            //Muestra un Toast conforme se ha eliminado el usuario
                            Toast toast = Toast.makeText(context, "La película ha sido eliminada", Toast.LENGTH_SHORT);
                            toast.show();
                            finish();
                        } else {
                            Log.d("response", "Ha ocurrido un error, código=" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("response", "Ha ocurrido un error");
                    }
                });
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Se ha cancelado la operación
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}