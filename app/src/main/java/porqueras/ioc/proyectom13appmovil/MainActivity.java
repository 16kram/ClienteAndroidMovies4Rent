package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

        //String baseUrl = "http://localhost:8080/";
        String baseUrl = "https://jsonplaceholder.typicode.com/";

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-ddd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //APIService service = retrofit.create(APIService.class);
        MiAPIService service=retrofit.create(MiAPIService.class);

        Call<ArticuloResponse> call=service.getArticulo(28);

        call.enqueue(new Callback<ArticuloResponse>() {
            @Override
            public void onResponse(Call<ArticuloResponse> call, Response<ArticuloResponse> response) {
                    if(response.isSuccessful()){
                        ArticuloResponse articulo=response.body();
                        Log.d("response", "response=" + articulo);
                    }else{
                        Log.d("response", "error" + response.code());
                    }
            }

            @Override
            public void onFailure(Call<ArticuloResponse> call, Throwable t) {
                Log.d("response", "error general-->" + t.getMessage());
            }
        });

        /*Call<UsuarioResponse> call = service.getUsuario();

        call.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful()) {
                    UsuarioResponse usuario = response.body();
                    Log.d("response", "response=" + usuario);
                } else {
                    Log.d("response", "error" + response.code());
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                Log.d("response", "error general-->" + t.getMessage());
            }
        });*/

    }
}