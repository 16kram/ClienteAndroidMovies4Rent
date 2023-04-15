package porqueras.ioc.proyectom13appmovil.secciones.peliculas;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;

import porqueras.ioc.proyectom13appmovil.APIService;
import porqueras.ioc.proyectom13appmovil.DetallePelicula;
import porqueras.ioc.proyectom13appmovil.Pelicula;
import porqueras.ioc.proyectom13appmovil.Usuario;
import porqueras.ioc.proyectom13appmovil.utilidades.PeliculasListAdapter;
import porqueras.ioc.proyectom13appmovil.R;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaListaResponse;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para listar películas
 *
 * @Author Esteban Porqueras Araque
 */
public class ListadoPeliculas extends AppCompatActivity implements PeliculasListAdapter.PasarIdListado {
    private final LinkedList<Pelicula> peliculas = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private PeliculasListAdapter mAdapter;
    APIService apiService;
    HashMap hashMap = new HashMap();
    private String accion;
    private String id;
    private Button botonPagAtras, botonPagAdelante;
    private TextView textoIndicadorNumPagina;
    private int numPagina = 0;
    private int tamPagina = 3;
    private int paginasTotales;
    private final int NUM_MAX_USUARIOS = 1000;//Número máximo de usuarios para listar

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
        //setTitle(accion.substring(0, 1).toUpperCase() + accion.substring(1) + " películas");
        switch (accion) {
            case "borrar":
                setTitle("¿Qué película desea borrar?");
                break;
            case "modificar":
                setTitle("¿Qué película desea modificar?");
                break;
            case "alquilar":
                setTitle("¿Qué película desea alquilar?");
                break;
            default:
                setTitle("Listado de las películas");
        }

        //Añadimos los botones y los TextView
        botonPagAtras = (Button) findViewById(R.id.buttonAtrasarPaginaPelicula);
        botonPagAdelante = (Button) findViewById(R.id.buttonAvanzarPaginaPelicula);
        textoIndicadorNumPagina = (TextView) findViewById(R.id.textViewNumPaginaPelicula);

        //Acción del botón adelantar página
        botonPagAdelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numPagina < paginasTotales) {
                    peliculas.clear();
                    //Incrementamos el número de pagina
                    numPagina++;
                    textoIndicadorNumPagina.setText("pag " + Integer.toString(numPagina) + " de " + paginasTotales);
                    listarPeliculas();
                }
            }
        });

        //Acción del botón página atrás
        botonPagAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peliculas.clear();
                //Decrementamos el número de pagina si es mayor que cero
                if (numPagina > 0) {
                    numPagina--;
                    textoIndicadorNumPagina.setText("pag " + Integer.toString(numPagina) + " de " + paginasTotales);
                    listarPeliculas();
                }
            }
        });

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Mostramos el número de página al inicializar la actividad
        numMaxPaginas();

        //Mostramos en el RecyclerView los usuarios
        listarPeliculas();

    }

    /**
     * Mostramos las películas en el RecyclerView
     */
    private void listarPeliculas() {
        Call<PeliculaListaResponse> callPeliculaListaResponse = apiService.getPeliculas(ApiUtils.TOKEN);
        callPeliculaListaResponse.enqueue(new Callback<PeliculaListaResponse>() {
            @Override
            public void onResponse(Call<PeliculaListaResponse> call, Response<PeliculaListaResponse> response) {
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
                    mAdapter = new PeliculasListAdapter(ListadoPeliculas.this, peliculas, ListadoPeliculas.this);
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
     * Calcula el número máximo de páginas en función de las películas
     */
    private void numMaxPaginas() {
        Call<PeliculaListaResponse> peliculaListaResponseCall = apiService.getPeliculas(ApiUtils.TOKEN);
        peliculaListaResponseCall.enqueue(new Callback<PeliculaListaResponse>() {
            @Override
            public void onResponse(Call<PeliculaListaResponse> call, Response<PeliculaListaResponse> response) {
                if (response.isSuccessful()) {
                    paginasTotales = response.body().getValue().size() / tamPagina;
                    textoIndicadorNumPagina.setText("pag " + Integer.toString(numPagina) + " de " + paginasTotales);
                } else {
                    Log.d("response", "No se puede mostrar el num máximo de páginas");
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
            case "alquilar":
                i = new Intent();
                i.putExtra("idPelicula", id);//Devolvemos el número de id de la película
                setResult(RESULT_OK, i);
                finish();
                break;
            case "listar":
                i = new Intent(this, DetallePelicula.class);
                i.putExtra("idPelicula", id);//Enviamos el número de id de la película
                startActivity(i);
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
        builder.setTitle("Eliminar Película");
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

    /**
     * Creamos un menú para indicar el número máximo de películas que se tienen que mostrar por página
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_listado_peliculas, menu);
        return true;
    }

    /**
     * Acciones en función de lo que se pulse en el menú
     *
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.peliculasPorPagina:
                //preguntarNumUsuariosPorPag();
                break;
        }
        return false;
    }
}