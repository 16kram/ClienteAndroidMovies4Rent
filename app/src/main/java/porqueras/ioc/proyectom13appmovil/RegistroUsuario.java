package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.NullConverterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroUsuario extends AppCompatActivity {
    EditText nombre,apellidos,telefono,email,address,username,password,confirmPassword;
    Button botonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        setTitle("Registro de usuarios");

        //Añadimos los campos de texto y los botones
        nombre=(EditText)findViewById(R.id.editTextNombre);
        apellidos=(EditText) findViewById(R.id.editTextApellidos);
        telefono=(EditText) findViewById(R.id.editTextTelefono);
        email=(EditText) findViewById(R.id.editTextTelefono);
        address=(EditText) findViewById(R.id.editTextDireccion);
        username=(EditText) findViewById(R.id.editTextUserName);
        password=(EditText)findViewById(R.id.editTextPassword);
        confirmPassword=(EditText) findViewById(R.id.editTextConfirmPassword);
        botonRegistro=(Button)findViewById(R.id.buttonRegistro);

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

        //Acción del botón Añadir
        botonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                    //Si las contraseñas no coinciden muestra un Toast
                    Context context=getApplicationContext();
                    Toast toast=Toast.makeText(context,"Las contraseñas no coinciden ",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    //Las contraseñas coinciden

                }
            }
        });
    }
}