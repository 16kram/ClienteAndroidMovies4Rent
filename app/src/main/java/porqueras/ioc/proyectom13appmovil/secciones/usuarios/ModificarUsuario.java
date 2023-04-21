package porqueras.ioc.proyectom13appmovil.secciones.usuarios;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import porqueras.ioc.proyectom13appmovil.APIService;
import porqueras.ioc.proyectom13appmovil.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Movies4Rent");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Actualizar datos del usuario");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_baseline_person_24);


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
                //Comprueba que el campo del correo electrónico está bien formado
                boolean isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
                if (isValidEmail) {
                    // el correo electrónico es válido
                    Log.d("response", "correo electrónico valido=" + email.getText().toString());
                } else {
                    // el correo electrónico es inválido
                    Log.d("response", "correo electrónico no valido=" + email.getText().toString());
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Correo electrónico no válido", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                //Comprueba que el campo del número de teléfono está bien formado
                boolean isValidPhoneNumber = Patterns.PHONE.matcher(telefono.getText().toString()).matches();
                if (isValidPhoneNumber) {
                    // el número es válido
                    Log.d("response", "número de teléfono valido=" + telefono.getText().toString());
                } else {
                    // el número es inválido
                    Log.d("response", "número de teléfono no valido=" + telefono.getText().toString());
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Número de teléfono no válido", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                //Se actualizan los datos del usuario
                UsuarioUpdate user = new UsuarioUpdate(
                        nombre.getText().toString(),
                        apellidos.getText().toString(),
                        telefono.getText().toString(),
                        email.getText().toString(),
                        direccion.getText().toString()
                );
                Call<UsuarioUpdate> callUsuarioUpdate = apiService.updateUsuario(ApiUtils.TOKEN, user);
                callUsuarioUpdate.enqueue(new Callback<UsuarioUpdate>() {
                    @Override
                    public void onResponse(Call<UsuarioUpdate> call, Response<UsuarioUpdate> response) {
                        if (response.isSuccessful()) {
                            Log.d("response", "Usuario actualizado");
                            //Muestra un Toast indicando que el usuario ha sido actualizado
                            Toast toast = Toast.makeText(getBaseContext(), "El usuario ha sido actualizado", Toast.LENGTH_LONG);
                            toast.show();
                            finish();
                        } else {
                            Log.d("response", "Ha ocurrido un error , código=" + response.code());
                            //Muestra un Toast indicando que ha ocurrido un error
                            Toast toast = Toast.makeText(getBaseContext(), "Ha ocurrido un error, inténtelo de nuevo", Toast.LENGTH_LONG);
                            toast.show();
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