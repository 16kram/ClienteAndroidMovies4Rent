package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import porqueras.ioc.proyectom13appmovil.modelos.LoginResponse;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.NullConverterFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText usuario;
    EditText contrasena;
    Button buttonEntrar;
    Button buttonNuevoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Oculta la ActionBar
        getSupportActionBar().hide();

        //Añadimos los campos de texto y los botones
        usuario = (EditText) findViewById(R.id.editTextTextUsuario);
        contrasena = (EditText) findViewById(R.id.editTextTextContrasena);
        buttonEntrar = (Button) findViewById(R.id.buttonEntrar);
        buttonNuevoUsuario=(Button)findViewById(R.id.buttonNuevoUsuario);


        //Configuración de la conexión de red
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

        //Creamos la instancia de Gson y Retrofit
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-ddd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUtils.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(new NullConverterFactory())//Si la cadena devuelta es nula o vacía evita el error-->End of input at line 1 column 1 path $
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService apiService = retrofit.create(APIService.class);


        //Acción del botón Entrar
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginResponse login = new LoginResponse(usuario.getText().toString(), contrasena.getText().toString());
                Call<LoginResponse> callLogin = apiService.getLogin(login);

                callLogin.enqueue((new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("response", "Login Correcto, código="+response.code());
                        } else {
                            Log.d("response", "Ocurrió un error en la petición Login, código=" + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("response", "Error de red-->" + t.getMessage());
                    }
                }));
            }
        });

        //Acción del botón Nuevo Usuario
        buttonNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,RegistroUsuario.class);
                startActivity(i);
            }
        });




/*
        //Prueba addUsuario
        UsuarioResponse user = new UsuarioResponse(1, "David", "Porqueras", "123456", "16k.ram", "direccion", "usuario", "123", "123", 1);
        Call<UsuarioResponse> callUsuario = service.setUsuario(user);

        callUsuario.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("response", "response=" + response.body());
                } else {
                    Log.d("response", "Ocurrió un error en la petición-->" + response.code());
                }

            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        });


        //Prueba upDate usuario
        UsuarioUpdate usuarioUpdate = new UsuarioUpdate("Nonamed", "Castaño", "carlos.castaño", "direccion1", "direccion2", "12346");
        Call<UsuarioUpdate> callUpdate = service.updateUsuario(1, usuarioUpdate);

        callUpdate.enqueue(new Callback<UsuarioUpdate>() {
            @Override
            public void onResponse(Call<UsuarioUpdate> call, Response<UsuarioUpdate> response) {

            }

            @Override
            public void onFailure(Call<UsuarioUpdate> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        });



 */

        /*
        //Prueba borrar
        Call<Void> usuarioBorrar = apiService.deleteUsuario(2);
        usuarioBorrar.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("response", "Borrar response=" + response.body());
                } else {
                    Log.d("response", "Borrar Ocurrió un error en la petición-->" + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        });


         */

/*
        //Prueba listar USUARIOS
        Call<List<UsuarioResponse>> call = service.getUsuario();
        call.enqueue(new Callback<List<UsuarioResponse>>() {
            @Override
            public void onResponse(Call<List<UsuarioResponse>> call, Response<List<UsuarioResponse>> response) {
                if (response.isSuccessful()) {
                    UsuarioResponse usuario = response.body().get(0);
                    Log.d("response", "Nombre=" + usuario.getNombre());
                    Log.d("response", "Apellidos=" + usuario.getApellidos());
                } else {
                    Log.d("response", "Ocurrió un error en la petición-->" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<UsuarioResponse>> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        });
*/

    }
}