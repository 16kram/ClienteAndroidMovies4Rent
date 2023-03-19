package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import porqueras.ioc.proyectom13appmovil.modelos.UsuarioInfoResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioUpdate;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para actualizar los datos del usuario
 *
 * @author Esteban Porqueras Araque
 */
public class ModificarUsuario extends AppCompatActivity {
    APIService apiService;
    String id;//Identificador del usuario
    EditText nombre, apellidos, telefono, email, direccion;
    Button botonActualizar;
    Switch switchAdministrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Actualizar datos del usuario");

        //Recupera el id del usuario enviado desde la actividad anterior
        Bundle extras = getIntent().getExtras();
        id = extras.getString("id");

        //Añadimos los campos de texto y los botones
        nombre = (EditText) findViewById(R.id.editTextNombreModificar);
        apellidos = (EditText) findViewById(R.id.editTextApellidosModificar);
        telefono = (EditText) findViewById(R.id.editTextTelefonoModificar);
        email = (EditText) findViewById(R.id.editTextEmailModificar);
        direccion = (EditText) findViewById(R.id.editTextDireccionModificar);
        botonActualizar = (Button) findViewById(R.id.buttonActualizar);
        switchAdministrador = (Switch) findViewById(R.id.switchAdministrador);

        //Si el usuario es un administrador se habilita el switch de Administrador
        if (ApiUtils.administrador) {
            switchAdministrador.setVisibility(View.VISIBLE);
        } else {
            switchAdministrador.setVisibility(View.GONE);
        }

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Obtiene los datos del usuario que se ha seleccionado
        Call<UsuarioInfoResponse> callUsuario = apiService.getUsuarioId(id, ApiUtils.TOKEN);
        callUsuario.enqueue(new Callback<UsuarioInfoResponse>() {
            @Override
            public void onResponse(Call<UsuarioInfoResponse> call, Response<UsuarioInfoResponse> response) {
                if (response.isSuccessful()) {
                    //Mostramos los datos del usuario en sus campos
                    Log.d("response", "usuario=" + response.body().getValue().getNombre());
                    nombre.setText(response.body().getValue().getNombre());
                    apellidos.setText(response.body().getValue().getApellidos());
                    telefono.setText(response.body().getValue().getTelefono());
                    email.setText(response.body().getValue().getEmail());
                    direccion.setText(response.body().getValue().getDireccion());
                } else {
                    Log.d("response", "Ha ocurrido un error, código=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<UsuarioInfoResponse> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        });

        //Acción del botón Actualizar
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Se actualiza el dato de si el usuario es administrador o no
                //Sólo si el usuario actual es administrador
                boolean admin;
                if (switchAdministrador.isChecked()) {
                    admin = true;
                } else {
                    admin = false;
                }
                if (ApiUtils.administrador) {
                    //Se hace la llamada al servidor para modificar el estado del usuario, si es administrador o no
                    Call<Void> callSetAdmin = apiService.setAdmin(id,"admin",admin,ApiUtils.TOKEN);
                    callSetAdmin.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Log.d("response", "Rol de admin modificado a " + admin);
                            } else {
                                Log.d("response", "Ocurrió un error al modificar el rol de admin, código=" + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.d("response", "Error de red-->" + t.getMessage());
                        }
                    });
                }

                //Se actualizan los datos del usuario
                UsuarioUpdate user = new UsuarioUpdate(
                        email.getText().toString(),
                        nombre.getText().toString(),
                        apellidos.getText().toString(),
                        telefono.getText().toString(),
                        direccion.getText().toString()
                );
                Call<UsuarioUpdate> callUsuarioUpdate = apiService.updateUsuario(ApiUtils.TOKEN, user);
                callUsuarioUpdate.enqueue(new Callback<UsuarioUpdate>() {
                    @Override
                    public void onResponse(Call<UsuarioUpdate> call, Response<UsuarioUpdate> response) {
                        if (response.isSuccessful()) {
                            Log.d("response", "Usuario actualizado");
                        } else {
                            Log.d("response", "Ha ocurrido un error , código=" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<UsuarioUpdate> call, Throwable t) {
                        Log.d("response", "Error de red-->" + t.getMessage());
                    }
                });
            }
        });
    }
}