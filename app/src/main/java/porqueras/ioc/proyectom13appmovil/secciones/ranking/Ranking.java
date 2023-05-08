package porqueras.ioc.proyectom13appmovil.secciones.ranking;

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

import java.util.HashMap;
import java.util.LinkedList;

import porqueras.ioc.proyectom13appmovil.APIService;
import porqueras.ioc.proyectom13appmovil.R;
import porqueras.ioc.proyectom13appmovil.utilidades.RankingAdapter;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaListaResponse;
import porqueras.ioc.proyectom13appmovil.secciones.peliculas.DetallePelicula;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para mostrar el ranking de las películas
 *
 * @Author Esteban Porqueras Araque
 */
public class Ranking extends AppCompatActivity implements RankingAdapter.PasarIdListado {
    private final LinkedList<RankingPelicula> peliculas = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private RankingAdapter mAdapter;
    APIService apiService;
    HashMap hashMap = new HashMap();
    private String accion;
    private String id;
    private Button botonPagAtras, botonPagAdelante;
    private TextView textoIndicadorNumPagina;
    private int numPagina = 0;
    private int tamPagina = 4;
    private int paginasTotales;
    private final int NUM_MAX_PELICULAS = 1000;//Número máximo de películas para listar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Movies4Rent");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Ranking de películas");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_baseline_bar_chart_24);

        //Añadimos los botones y los TextView
        botonPagAtras = (Button) findViewById(R.id.buttonAtrasarPaginaRanking);
        botonPagAdelante = (Button) findViewById(R.id.buttonAvanzarPaginaRanking);
        textoIndicadorNumPagina = (TextView) findViewById(R.id.textViewNumPaginaRanking);

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

    private void listarPeliculas() {
        Call<PeliculaListaResponse> listaResponseCall = apiService.getRanking(numPagina, tamPagina, ApiUtils.TOKEN);
        switch (ApiUtils.filtroRanking) {
            case ApiUtils.SOLO_FILTRO_AÑO:
                listaResponseCall = apiService.getRankingFiltroAño(numPagina, tamPagina, ApiUtils.TOKEN, ApiUtils.tituloRanking, ApiUtils.directorRanking, ApiUtils.generoRanking, ApiUtils.añoRanking);
                break;
            case ApiUtils.SOLO_FILTRO_STRING:
                listaResponseCall = apiService.getRankingFiltros(numPagina, tamPagina, ApiUtils.TOKEN, ApiUtils.tituloRanking, ApiUtils.directorRanking, ApiUtils.generoRanking);
                break;
        }
        listaResponseCall.enqueue(new Callback<PeliculaListaResponse>() {
            @Override
            public void onResponse(Call<PeliculaListaResponse> call, Response<PeliculaListaResponse> response) {
                if (response.isSuccessful()) {
                    for (int n = 0; n < response.body().getValue().getContent().size(); n++) {
                        //Obtiene las películas y las añade a la lista
                        RankingPelicula rankingPelicula = new RankingPelicula();
                        rankingPelicula.setTitulo(response.body().getValue().getContent().get(n).getTitulo());
                        rankingPelicula.setDirector(response.body().getValue().getContent().get(n).getDirector());
                        peliculas.add(rankingPelicula);
                    }

                    //Asociamos el id con el número de la posición de la lista
                    for (int n = 0; n < peliculas.size(); n++) {
                        hashMap.put(n, response.body().getValue().getContent().get(n).getId());
                        Log.d("respose", "response hasMap n=" + n + " id=" + hashMap.get(n) + " " +
                                response.body().getValue().getContent().get(n).getTitulo());
                    }

                    //Obtiene un identificador para la vista del RecyclerView
                    mRecyclerView = findViewById(R.id.recyclerviewRanking);
                    //Crea un adaptador para proporcionar los datos mostrados
                    mAdapter = new RankingAdapter(Ranking.this, peliculas, Ranking.this);
                    //Conecta el adaptador con el RecyclerView
                    mRecyclerView.setAdapter(mAdapter);
                    //Da al RecyclerView un layout manager por defecto
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(Ranking.this));

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
        Call<PeliculaListaResponse> listaResponseCall = apiService.getRanking(0, NUM_MAX_PELICULAS, ApiUtils.TOKEN);
        switch (ApiUtils.filtroRanking) {
            case ApiUtils.SOLO_FILTRO_AÑO:
                listaResponseCall = apiService.getRankingFiltroAño(0, NUM_MAX_PELICULAS, ApiUtils.TOKEN, ApiUtils.tituloRanking, ApiUtils.directorRanking, ApiUtils.generoRanking, ApiUtils.añoRanking);
                break;
            case ApiUtils.SOLO_FILTRO_STRING:
                listaResponseCall = apiService.getRankingFiltros(0, NUM_MAX_PELICULAS, ApiUtils.TOKEN, ApiUtils.tituloRanking, ApiUtils.directorRanking, ApiUtils.generoRanking);
                break;
        }
        listaResponseCall.enqueue(new Callback<PeliculaListaResponse>() {
            @Override
            public void onResponse(Call<PeliculaListaResponse> call, Response<PeliculaListaResponse> response) {
                if (response.isSuccessful()) {
                    paginasTotales = response.body().getValue().getContent().size() / tamPagina;
                    textoIndicadorNumPagina.setText("pag " + Integer.toString(numPagina) + " de " + paginasTotales);
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
     * en el ranking del listado de películas
     *
     * @param posicion
     */
    @Override
    public void pasarPosicionListado(int posicion) {
        Log.d("response", "response interface posición pulsada=" + posicion);
        id = (String) hashMap.get(posicion);

        //Muestra el detalle de la película
        Intent i = new Intent(this, DetallePelicula.class);
        i.putExtra("idPelicula", id);//Enviamos el número de id de la película
        startActivity(i);

    }

    /**
     * Creamos un menú para indicar el número máximo de ranking de películas que se tienen que mostrar por página
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_listado_ranking, menu);
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
            case R.id.rankingPorPagina:
                preguntarNumPeliculasPorPag();
                break;
            case R.id.filtrosRanking:
                if(ApiUtils.menuFiltrarRanking){
                Intent i = new Intent(this, FiltroRanking.class);
                startActivity(i);}else{
                    //Muestra un Toast conforme no se pueden filtrar el ranking de películas en esta pantalla
                    Toast toast = Toast.makeText(getBaseContext(), "Esta opción no está disponible" +
                            " en esta pantalla," +
                            " regrese atrás...", Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
        }
        return false;
    }

    /**
     * Pregunta el número máximo de películas que se han de mostrar por página
     */
    private void preguntarNumPeliculasPorPag() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Número máximo de películas por página?");
        final EditText numMaxPeliculas = new EditText(this);
        numMaxPeliculas.setInputType(InputType.TYPE_CLASS_NUMBER);//Seleccionamos el teclado numérico
        builder.setView(numMaxPeliculas);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    tamPagina = Integer.parseInt(numMaxPeliculas.getText().toString());
                    Log.d("response", "Tam página=" + tamPagina);
                    if (tamPagina > 0) {
                        peliculas.clear();
                        numPagina = 0;
                        numMaxPaginas();
                        //Mostramos en el RecyclerView las películas
                        listarPeliculas();
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

    /**
     * Si se sale de la actividad se pone el filtro para que en el RecyclerView
     * se puedan visualizar todos los rankings de las películas
     */
    protected void onDestroy() {
        super.onDestroy();
        ApiUtils.filtroRanking = ApiUtils.TODOS_LOS_FILTROS;
        ApiUtils.tituloRanking = null;
        ApiUtils.directorRanking = null;
        ApiUtils.generoRanking = null;
    }
}