package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import porqueras.ioc.proyectom13appmovil.modelos.UsuarioInfoResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioUpdate;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModificarUsuario extends AppCompatActivity {
    APIService apiService;
    String id;//Identificador del usuario
    EditText nombre, apellidos, telefono, email, direccion;
    Button botonActualizar;

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

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Obtiene los datos del usuario que se ha seleccionado
        Call<UsuarioInfoResponse> callUsuario = apiService.getUsuarioId(id, ApiUtils.TOKEN);
        callUsuario.enqueue(new Callback<UsuarioInfoResponse>() {
            @Override
            public void onResponse(Call<UsuarioInfoResponse> call, Response<UsuarioInfoResponse> response) {
                if(response.isSuccessful()){
                    //Mostramos los datos del usuario en sus campos
                    Log.d("response","usuario="+response.body().getValue().getNombre());
                    nombre.setText(response.body().getValue().getNombre());
                    apellidos.setText(response.body().getValue().getApellidos());
                    telefono.setText(response.body().getValue().getTelefono());
                    email.setText(response.body().getValue().getEmail());
                    direccion.setText(response.body().getValue().getDireccion());
                }
            }

            @Override
            public void onFailure(Call<UsuarioInfoResponse> call, Throwable t) {

            }
        });

        //Acción del botón Actualizar
        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioUpdate user = new UsuarioUpdate(
                        email.getText().toString(),
                        nombre.getText().toString(),
                        apellidos.getText().toString(),
                        telefono.getText().toString(),
                        direccion.getText().toString()
                );
                Call<UsuarioUpdate> callUsuarioUpdate = apiService.updateUsuario(ApiUtils.TOKEN,user);
                callUsuarioUpdate.enqueue(new Callback<UsuarioUpdate>() {
                    @Override
                    public void onResponse(Call<UsuarioUpdate> call, Response<UsuarioUpdate> response) {
                        if(response.isSuccessful()){
                            Log.d("response","Usuario actualizado");
                        }else{
                            Log.d("response","Ha ocurrido un error , código="+response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<UsuarioUpdate> call, Throwable t) {

                    }
                });
            }
        });
    }
}