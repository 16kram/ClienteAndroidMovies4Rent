package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;

import porqueras.ioc.proyectom13appmovil.modelos.AlquilerListaResponse;
import porqueras.ioc.proyectom13appmovil.modelos.AlquilerPeliculasPorId;
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
public class ListadoAlquileres extends AppCompatActivity implements AlquilerListAdapter.PasarIdListado {
    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private AlquilerListAdapter mAdapter;
    private APIService apiService;
    private String accion;
    private String idUsuario;
    private String idPelicula;
    HashMap hashMap = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alquileres);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Recuperamos la acción a ejecutar de la actividad anterior
        Bundle extras = getIntent().getExtras();
        accion = extras.getString("accion");
        Log.d("response", "accion=" + accion);

        //Añadimos el título de la Activity en la barra superior
        switch (accion) {
            case "listar":
                setTitle("Listar películas alquiladas");
                break;
            case "eliminar":
                setTitle("Eliminar película alquilada");
                break;
        }


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
                        idUsuario = response.body().getValue().get(n).getUsuariId();
                        //Obtenemos el id de la película
                        idPelicula = response.body().getValue().get(n).getPeliculaId();
                        //Asociamos el id con el número de la posición de la lista
                        hashMap.put(n, response.body().getValue().get(n).getId());
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
                                                mWordList.add("Usuario: " + usuario + "\n\nPelícula:\n" + pelicula);

                                                //Obtiene un identificador para la vista del RecyclerView
                                                mRecyclerView = findViewById(R.id.recyclerviewAlquileres);
                                                //Crea un adaptador para proporcionar los datos mostrados
                                                mAdapter = new AlquilerListAdapter(ListadoAlquileres.this, mWordList, ListadoAlquileres.this);
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

    /**
     * Recibe el número de posición que se ha seleccionado
     * en el listado de alquileres
     *
     * @param posicion
     */
    @Override
    public void pasarPosicionListado(int posicion) {
        String id = (String) hashMap.get(posicion);
        Log.d("response", "id alquiler=" + id);

        switch (accion) {
            case "listar":
                detallePelicula(id);
                break;
            case "eliminar":
                borrarAlquiler(id);
                break;
        }
    }

    /**
     * Borra el alquiler
     *
     * @param idAlquiler se pasa el identificador del alquiler que se va a borrar
     */
    private void borrarAlquiler(String idAlquiler) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Alquiler");
        builder.setMessage("¿Estás seguro de que deseas borrar este alquiler?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Se ha elegido borrar el alquiler
                Call<Void> eliminarAlquiler = apiService.deleteAlquiler(idAlquiler, ApiUtils.TOKEN);
                eliminarAlquiler.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("response", "Alquiler eliminado");
                            //Muestra un Toast conforme se ha eliminado el usuario
                            Toast toast = Toast.makeText(getBaseContext(), "El alquiler ha sido eliminado", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            Log.d("response", "Ha ocurrido un error, código=" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("response", "Ha ocurrido un error");
                    }
                });
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Se ha cancelado la operación
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Muestra la información de la película cuando se pulsa en el listado del alquiler
     *
     * @param idAlquiler se le pasa el identificador del alquiler para vaeriguar la película
     */
    private void detallePelicula(String idAlquiler) {
        Call<AlquilerPeliculasPorId> alquilerPeliculasPorIdCall = apiService.alquilerPeliculaPorId(idAlquiler, ApiUtils.TOKEN);
        alquilerPeliculasPorIdCall.enqueue(new Callback<AlquilerPeliculasPorId>() {
            @Override
            public void onResponse(Call<AlquilerPeliculasPorId> call, Response<AlquilerPeliculasPorId> response) {
                if (response.isSuccessful()) {
                    String idPelicula = response.body().getValue().getPeliculaId();
                    Intent i = new Intent(ListadoAlquileres.this, DetallePelicula.class);
                    i.putExtra("idPelicula", idPelicula);
                    startActivity(i);
                } else {
                    Log.d("response", "Ha ocurrido un error, código=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<AlquilerPeliculasPorId> call, Throwable t) {
                Log.d("response", "Ha ocurrido un error");
            }
        });
    }
}