package porqueras.ioc.proyectom13appmovil.secciones.usuarios;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
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
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioListaResponse;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import porqueras.ioc.proyectom13appmovil.utilidades.WordListAdadpter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para listar los usuarios
 *
 * @author Esteban Porqueras Araque
 */
public class ListadoUsuarios extends AppCompatActivity implements WordListAdadpter.PasarIdListado {
    private final LinkedList<Usuario> usuarios = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WordListAdadpter mAdapter;
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
        setContentView(R.layout.activity_listado_usuarios);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Recuperamos la acción a ejecutar de la actividad anterior
        Bundle extras = getIntent().getExtras();
        accion = extras.getString("accion");
        Log.d("response", "accion=" + accion);

        //Añadimos el título de la accion a realizar en la barra superior de la activity
        setTitle("Movies4Rent");
        switch (accion) {
            case "borrar":
                ActionBar actionBar = getSupportActionBar();
                actionBar.setSubtitle("¿Qué usuario desea borrar?");
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setIcon(R.drawable.ic_baseline_person_off_24);
                break;
            case "modificar":
                actionBar = getSupportActionBar();
                actionBar.setSubtitle("Cambiar ROL de usuario");
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setIcon(R.drawable.ic_baseline_person_pin_24);
                break;
            case "alquilar":
                actionBar = getSupportActionBar();
                actionBar.setSubtitle("Seleccione el usuario");
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setIcon(R.drawable.ic_baseline_person_pin_24);
                break;
            default:
                actionBar = getSupportActionBar();
                actionBar.setSubtitle("Listado de los usuarios");
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setIcon(R.drawable.ic_baseline_people_24);
        }

        //Añadimos los botones y los TextView
        botonPagAtras = (Button) findViewById(R.id.buttonAtrasarPagina);
        botonPagAdelante = (Button) findViewById(R.id.buttonAvanzarPagina);
        textoIndicadorNumPagina = (TextView) findViewById(R.id.textViewNumPagina);

        //Acción del botón adelantar página
        botonPagAdelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numPagina < paginasTotales) {
                    usuarios.clear();
                    //Incrementamos el número de pagina
                    numPagina++;
                    textoIndicadorNumPagina.setText("pag " + Integer.toString(numPagina) + " de " + paginasTotales);
                    listarUsuarios();
                }
            }
        });

        //Acción del botón página atrás
        botonPagAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarios.clear();
                //Decrementamos el número de pagina si es mayor que cero
                if (numPagina > 0) {
                    numPagina--;
                    textoIndicadorNumPagina.setText("pag " + Integer.toString(numPagina) + " de " + paginasTotales);
                    listarUsuarios();
                }
            }
        });

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Mostramos el número de página al inicializar la actividad
        numMaxPaginas();

        //Mostramos en el RecyclerView los usuarios
        listarUsuarios();

    }

    /**
     * Mostramos los usuarios en el RecyclerView
     */
    private void listarUsuarios() {
        Call<UsuarioListaResponse> callUsuarioListaResponse = apiService.getUsuarios(numPagina, tamPagina, ApiUtils.TOKEN);
        switch (ApiUtils.filtroUsuarios) {
            case ApiUtils.FILTROS:
                callUsuarioListaResponse = apiService.getUsuariosFiltros(numPagina, tamPagina, ApiUtils.TOKEN,
                        ApiUtils.nombre, ApiUtils.apellidos, ApiUtils.username, ApiUtils.ordenarUsuariosPor);
                break;
        }
        callUsuarioListaResponse.enqueue(new Callback<UsuarioListaResponse>() {
            @Override
            public void onResponse(Call<UsuarioListaResponse> call, Response<UsuarioListaResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getValue().getContent().size() == 0) {
                        //Muestra un Toast conforme no hay usuarios que mostrar
                        Toast toast = Toast.makeText(getBaseContext(), "No hay ningún usuario para mostrar", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    Log.d("response", "Pág totales=" + paginasTotales);
                    for (int n = 0; n < response.body().getValue().getContent().size(); n++) {
                        //Obtiene los usuarios y los añade a la lista
                        Usuario usuario = new Usuario();
                        usuario.setNombre(response.body().getValue().getContent().get(n).getNombre());
                        usuario.setApellidos(response.body().getValue().getContent().get(n).getApellidos());
                        usuario.setTelefono(response.body().getValue().getContent().get(n).getTelefono());
                        usuario.setEmail(response.body().getValue().getContent().get(n).getEmail());
                        usuario.setDireccion(response.body().getValue().getContent().get(n).getDireccion());
                        usuario.setAdmin(response.body().getValue().getContent().get(n).isAdmin());
                        usuarios.add(usuario);
                    }

                    //Asociamos el id con el número de la posición de la lista
                    for (int n = 0; n < usuarios.size(); n++) {
                        hashMap.put(n, response.body().getValue().getContent().get(n).getId());
                        Log.d("respose", "response hasMap n=" + n + " id=" + hashMap.get(n) + " " +
                                response.body().getValue().getContent().get(n).getNombre());
                    }

                    //Obtiene un identificador para la vista del RecyclerView
                    mRecyclerView = findViewById(R.id.recyclerview);
                    //Crea un adaptador para proporcionar los datos mostrados
                    mAdapter = new WordListAdadpter(ListadoUsuarios.this, usuarios, ListadoUsuarios.this);
                    //Conecta el adaptador con el RecyclerView
                    mRecyclerView.setAdapter(mAdapter);
                    //Da al RecyclerView un layout manager por defecto
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(ListadoUsuarios.this));
                } else {
                    Log.d("response", "error usuario " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UsuarioListaResponse> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        });
    }

    /**
     * Calcula el número máximo de páginas en función de los usuarios
     */
    private void numMaxPaginas() {
        Call<UsuarioListaResponse> callUsuarioListaResponse = apiService.getUsuarios(0, NUM_MAX_USUARIOS, ApiUtils.TOKEN);
        switch (ApiUtils.filtroUsuarios) {
            case ApiUtils.FILTROS:
                callUsuarioListaResponse = apiService.getUsuariosFiltros(numPagina, tamPagina, ApiUtils.TOKEN,
                        ApiUtils.nombre, ApiUtils.apellidos, ApiUtils.username, ApiUtils.ordenarUsuariosPor);
                break;
        }
        callUsuarioListaResponse.enqueue(new Callback<UsuarioListaResponse>() {
            @Override
            public void onResponse(Call<UsuarioListaResponse> call, Response<UsuarioListaResponse> response) {
                if (response.isSuccessful()) {
                    paginasTotales = response.body().getValue().getContent().size() / tamPagina;
                    textoIndicadorNumPagina.setText("pag " + Integer.toString(numPagina) + " de " + paginasTotales);
                } else {
                    Log.d("response", "No se puede mostrar el num máximo de páginas");
                }
            }

            @Override
            public void onFailure(Call<UsuarioListaResponse> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        });
    }

    /**
     * Recibe el número de posición que se ha clicado
     * en el listado de usuarios
     *
     * @param posicion
     */
    @Override
    public void pasarPosicionListado(int posicion) {
        Log.d("response", "response interface posición pulsada=" + posicion);
        id = (String) hashMap.get(posicion);

        switch (accion) {
            case "borrar":
                borrarUsuario(id);
                break;
            case "modificar":
                modificarRolUsuario(id);
                break;
            case "alquilar":
                Intent i = new Intent();
                i.putExtra("idUsuario", id);//Devolvemos el número de id del usuario
                setResult(RESULT_OK, i);
                finish();
                break;
        }

    }

    /**
     * Borrar el usuario
     *
     * @param userId se le pasa el identificador del usuario el cual se va a borrar
     */
    private void borrarUsuario(String userId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar usuario");
        builder.setMessage("¿Estás seguro de que deseas borrar este usuario?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Se ha elegido borrar el usuario
                Call<Void> usuarioBorrar = apiService.deleteUsuario(userId, ApiUtils.TOKEN);
                usuarioBorrar.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("response", "El usuario ha sido eliminado");
                            //Actualiza los datos del RecyclerView
                            mAdapter.notifyDataSetChanged();
                            Context context = getApplicationContext();
                            //Muestra un Toast conforme se ha eliminado el usuario
                            Toast toast = Toast.makeText(context, "EL usuario ha sido eliminado", Toast.LENGTH_SHORT);
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
     * Modifica los datos del usuario
     *
     * @param id se le pasa el identificador del usuario el cual se va a actualizar
     */
    private void modificarRolUsuario(String id) {
        Intent i = new Intent(ListadoUsuarios.this, ModificarRolUsuario.class);
        i.putExtra("id", id);
        startActivity(i);
        finish();
    }

    /**
     * Creamos un menú para indicar el número máximo de usuarios que se tienen que mostrar por página
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_listado_usuarios, menu);
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
            case R.id.usuariosPorPagina:
                preguntarNumUsuariosPorPag();
                break;
            case R.id.filtrosUsuarios:
                Intent i = new Intent(ListadoUsuarios.this, FiltroUsuarios.class);
                i.putExtra("accion", accion);
                startActivity(i);
                break;

        }
        return false;
    }

    /**
     * Pregunta el número máximo de usuarios que se han de mostrar por página
     */
    private void preguntarNumUsuariosPorPag() {
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
                        usuarios.clear();
                        numPagina = 0;
                        numMaxPaginas();
                        //Mostramos en el RecyclerView los usuarios
                        listarUsuarios();
                    } else {
                        //Muestra un Toast indicando que el número de usuarios por página no puede ser menor que 1
                        Toast toast = Toast.makeText(getBaseContext(), "El número de usuarios por página no puede ser menor que 1", Toast.LENGTH_LONG);
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