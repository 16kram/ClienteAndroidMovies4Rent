package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import porqueras.ioc.proyectom13appmovil.modelos.AlquilerListaResponse;
import porqueras.ioc.proyectom13appmovil.modelos.AlquilerPeliculasPorId;
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
 * Pantalla para listar alquileres
 *
 * @Author Esteban Porqueras Araque
 */
public class ListadoAlquileres extends AppCompatActivity implements AlquilerListAdapter.PasarIdListado {
    //private final LinkedList<String> mWordList = new LinkedList<>();
    private final LinkedList<Alquiler> alquileres = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private AlquilerListAdapter mAdapter;
    private APIService apiService;
    private String accion;
    private String idUsuario;
    private String idPelicula;
    private String usuario;
    private HashMap hashMap = new HashMap();
    private Button botonPagAtras, botonPagAdelante;
    private TextView textoIndicadorNumPagina;
    private int numPagina = 0;
    private int tamPagina = 4;
    private int paginasTotales;
    private final int NUM_MAX_ALQUILERES = 1000;//Número máximo de alquileres para listar

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
        setTitle("Movies4Rent");
        switch (accion) {
            case "listar":
                ActionBar actionBar = getSupportActionBar();
                actionBar.setSubtitle("Listar películas alquiladas");
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setIcon(R.drawable.ic_baseline_format_list_bulleted_24);
                break;
            case "modificar":
                actionBar = getSupportActionBar();
                actionBar.setSubtitle("Modificar películas alquiladas");
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setIcon(R.drawable.ic_baseline_update_24);
                break;
            case "eliminar":
                actionBar = getSupportActionBar();
                actionBar.setSubtitle("Eliminar películas alquiladas");
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setIcon(R.drawable.ic_baseline_delete_24);
                break;
        }

        //Añadimos los botones y los TextView
        botonPagAtras = (Button) findViewById(R.id.buttonAtrasarPaginaAlquiler);
        botonPagAdelante = (Button) findViewById(R.id.buttonAvanzarPaginaAlquiler);
        textoIndicadorNumPagina = (TextView) findViewById(R.id.textViewNumPaginaAlquiler);

        //Acción del botón adelantar página
        botonPagAdelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numPagina < paginasTotales) {
                    alquileres.clear();
                    //Incrementamos el número de pagina
                    numPagina++;
                    textoIndicadorNumPagina.setText("pag " + Integer.toString(numPagina) + " de " + paginasTotales);
                    listarAlquileres();
                }
            }
        });

        //Acción del botón página atrás
        botonPagAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alquileres.clear();
                //Decrementamos el número de pagina si es mayor que cero
                if (numPagina > 0) {
                    numPagina--;
                    textoIndicadorNumPagina.setText("pag " + Integer.toString(numPagina) + " de " + paginasTotales);
                    listarAlquileres();
                }
            }
        });

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Mostramos el número de página al inicializar la actividad
        numMaxPaginas();

        //Mostramos en el RecyclerView los usuarios
        listarAlquileres();
    }

    /**
     * Mostramos los alquileres en el RecyclerView
     */
    private void listarAlquileres() {
        //Hacemos una petición al servidor para que nos envíe una lista con los alquileres
        Call<AlquilerListaResponse> callListaAlquileres = apiService.getAlquileres(numPagina, tamPagina, ApiUtils.TOKEN);
        callListaAlquileres.enqueue(new Callback<AlquilerListaResponse>() {
            @Override
            public void onResponse(Call<AlquilerListaResponse> call, Response<AlquilerListaResponse> response) {
                if (response.isSuccessful()) {
                    for (int n = 0; n < response.body().getValue().getContent().size(); n++) {
                        //Obtenemos el id del usuario
                        idUsuario = response.body().getValue().getContent().get(n).getUsuari();
                        //Obtenemos el id de la película
                        idPelicula = response.body().getValue().getContent().get(n).getPelicula();
                        //Asociamos el id con el número de la posición de la lista
                        hashMap.put(n, response.body().getValue().getContent().get(n).getId());
                        Log.d("response", "idUsuario=" + idUsuario);
                        Log.d("response", "idPelicula=" + idPelicula);

                        //Hacemos una petición de búsqueda de un usuario y esperamos un tiempo
                        //para que finalice el hilo
                        buscarUsuario(idUsuario);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        //Hacemos una petición de búsqueda de una película y esperamos un tiempo
                        //para que finalice el hilo
                        buscarPelicula(idPelicula);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<AlquilerListaResponse> call, Throwable t) {
                Log.d("response", "Error de lista de alquiler " + t.getMessage());
            }
        });

    }

    /**
     * Calcula el número máximo de páginas en función de los alquileres
     */
    private void numMaxPaginas() {
        Call<AlquilerListaResponse> alquilerListaResponseCall = apiService.getAlquileres(0, NUM_MAX_ALQUILERES, ApiUtils.TOKEN);
        alquilerListaResponseCall.enqueue(new Callback<AlquilerListaResponse>() {
            @Override
            public void onResponse(Call<AlquilerListaResponse> call, Response<AlquilerListaResponse> response) {
                if (response.isSuccessful()) {
                    paginasTotales = response.body().getValue().getContent().size() / tamPagina;
                    Log.d("response", "pag totales=" + paginasTotales + " ,tam pag=" + tamPagina);
                    textoIndicadorNumPagina.setText("pag " + Integer.toString(numPagina) + " de " + paginasTotales);
                } else {
                    Log.d("response", "No se puede mostrar el num máximo de páginas");
                }
            }

            @Override
            public void onFailure(Call<AlquilerListaResponse> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        });

    }

    /**
     * Realiza la búsqueda de un usuario mediante el id de usuario y se añade en la variable usuario
     * el nombre del usuario
     *
     * @param idUsuario el identificador del usuario
     */
    private void buscarUsuario(String idUsuario) {
        //Hacemos una petición al servidor para que nos envíe el nombre y apellidos del usuario
        Call<UsuarioInfoResponse> callMostrarUsuario = apiService.getUsuarioId(idUsuario, ApiUtils.TOKEN);

        callMostrarUsuario.enqueue(new Callback<UsuarioInfoResponse>() {
            @Override
            public void onResponse(Call<UsuarioInfoResponse> call, Response<UsuarioInfoResponse> response) {
                if (response.isSuccessful()) {
                    usuario = response.body().getValue().getNombre() + " " +
                            response.body().getValue().getApellidos();
                } else {
                    Log.d("response", "No se puede mostrar el usuario, código=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<UsuarioInfoResponse> call, Throwable t) {
                Log.d("response", "Error de usuario " + t.getMessage());
            }
        });
    }


    /**
     * Realiza la búsqueda de una película mediante el id de la película y se añade en la variable película
     * el nombre de la película
     *
     * @param idPelicula el identificador de la película
     */
    private void buscarPelicula(String idPelicula) {

        //Hacemos una petición al servidor para que nos muestre la película
        Call<PeliculaInfoResponse> callMostrarPelicula = apiService.getPelicula(idPelicula, ApiUtils.TOKEN);
        callMostrarPelicula.enqueue(new Callback<PeliculaInfoResponse>() {
            @Override
            public void onResponse(Call<PeliculaInfoResponse> call, Response<PeliculaInfoResponse> response) {
                if (response.isSuccessful()) {
                    String tituloPelicula = response.body().getValue().getTitulo();
                    Log.d("response", "Usuario= " + usuario + "-->Película: " + tituloPelicula + " " + idPelicula);

                    //Obtiene los alquileres y los añade a la lista
                    Alquiler alquiler = new Alquiler();
                    alquiler.setTituloPelicula(tituloPelicula);
                    alquiler.setUsuario(usuario);
                    alquileres.add(alquiler);

                    //Obtiene un identificador para la vista del RecyclerView
                    mRecyclerView = findViewById(R.id.recyclerviewAlquileres);
                    //Crea un adaptador para proporcionar los datos mostrados
                    mAdapter = new AlquilerListAdapter(ListadoAlquileres.this, alquileres, ListadoAlquileres.this);
                    //Conecta el adaptador con el RecyclerView
                    mRecyclerView.setAdapter(mAdapter);
                    //Da al RecyclerView un layout manager por defecto
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(ListadoAlquileres.this));
                } else {
                    Log.d("response", "No se puede mostrar la película, código=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<PeliculaInfoResponse> call, Throwable t) {
                Log.d("response", "Error de película " + t.getMessage());
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
            case "modificar":
                Intent i = new Intent();
                i.putExtra("idAlquiler", id);//Devolvemos el número de id del alquiler de la película
                setResult(RESULT_OK, i);
                finish();
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

    /**
     * Creamos un menú para indicar el número máximo de alquileres que se tienen que mostrar por página
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_listado_alquileres, menu);
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
            case R.id.alquilerPorPagina:
                preguntarNumAlquileresPorPag();
                break;
        }
        return false;
    }

    /**
     * Pregunta el número máximo de alquileres que se han de mostrar por página
     */
    private void preguntarNumAlquileresPorPag() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Número máximo de usuarios por página?");
        final EditText numMaxUsuarios = new EditText(this);
        numMaxUsuarios.setInputType(InputType.TYPE_CLASS_NUMBER);//Seleccionamos el teclado numérico
        builder.setView(numMaxUsuarios);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    tamPagina = Integer.parseInt(numMaxUsuarios.getText().toString());
                    Log.d("response", "Tam página=" + tamPagina);
                    if (tamPagina > 0) {
                        alquileres.clear();
                        numPagina = 0;
                        numMaxPaginas();
                        //Mostramos en el RecyclerView las películas
                        listarAlquileres();
                    } else {
                        //Muestra un Toast indicando que el número de películas por página no puede ser menor que 1
                        Toast toast = Toast.makeText(getBaseContext(), "El número de películas por página no puede ser menor que 1", Toast.LENGTH_LONG);
                        toast.show();
                    }
                } catch (Exception e) {
                    //Muestra un Toast indicando que ha ocurrido un error
                    Toast toast = Toast.makeText(getBaseContext(), "Ha ocurrido un error, inténtelo de nuevo", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}