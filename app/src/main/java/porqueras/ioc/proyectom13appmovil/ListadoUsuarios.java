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

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

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
    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WordListAdadpter mAdapter;
    APIService apiService;
    HashMap hashMap = new HashMap();
    private String accion;
    private String id;

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
        setTitle(accion.substring(0, 1).toUpperCase() + accion.substring(1) + " usuarios");

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        Call<UsuarioListaResponse> callUsuarioListaResponse = apiService.getUsuario(ApiUtils.TOKEN);
        callUsuarioListaResponse.enqueue(new Callback<UsuarioListaResponse>() {
            @Override
            public void onResponse(Call<UsuarioListaResponse> call, Response<UsuarioListaResponse> response) {
                if (response.isSuccessful()) {
                    for (int n = 0; n < response.body().getValue().size(); n++) {
                        //Obtiene los usuarios y los añade a la lista
                        mWordList.add(response.body().getValue().get(n).getNombre() +
                                " " + response.body().getValue().get(n).getApellidos() +
                                "\nTeléfono: " + response.body().getValue().get(n).getTelefono()
                                + "\ne-mail: " + response.body().getValue().get(n).getEmail()
                                + "\nDirección: " + response.body().getValue().get(n).getDireccion());
                    }

                    //Asociamos el id con el número de la posición de la lista
                    for (int n = 0; n < mWordList.size(); n++) {
                        hashMap.put(n, response.body().getValue().get(n).getId());
                        Log.d("respose", "response hasMap n=" + n + " id=" + hashMap.get(n) + " " +
                                response.body().getValue().get(n).getNombre());
                    }

                    //Obtiene un identificador para la vista del RecyclerView
                    mRecyclerView = findViewById(R.id.recyclerview);
                    //Crea un adaptador para proporcionar los datos mostrados
                    mAdapter = new WordListAdadpter(ListadoUsuarios.this, mWordList, ListadoUsuarios.this);
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
        }

    }

    /**
     * Borrar el usuario
     *
     * @param userId se le pasa el identificador del usuario el cual se va a borrar
     */
    private void borrarUsuario(String userId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
    }
}