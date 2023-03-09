package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    EditText usuario;
    EditText contrasena;
    Button buttonEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Oculta la ActionBar
        getSupportActionBar().hide();

        //Añadimos los campos de texto y el botón
        usuario = (EditText) findViewById(R.id.editTextTextUsuario);
        contrasena = (EditText) findViewById(R.id.editTextTextContrasena);
        buttonEntrar = (Button) findViewById(R.id.buttonEntrar);

        //Acción del botón
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        String baseUrl = "http://10.0.2.2:8080";

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-ddd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService service = retrofit.create(APIService.class);

        //Prueba USUARIOS
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


        //Prueba LOGIN
        Call<LoginResponse> callLogin = service.getLogin("string", "string");

        callLogin.enqueue((new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("response", "LoginCorrecto");
                } else {
                    Log.d("response", "Ocurrió un error en la petición LogIn-->" + response.code());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        }));


    }
}