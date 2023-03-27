package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import porqueras.ioc.proyectom13appmovil.modelos.UsuarioResponse;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para dar de alta a los usuarios
 *
 * @author Esteban Porqueras Araque
 */
public class RegistroUsuario extends AppCompatActivity {
    EditText nombre, apellidos, telefono, email, direccion, username, password, confirmPassword;
    Button botonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Añadir usuario");

        //Añadimos los campos de texto y los botones
        nombre = (EditText) findViewById(R.id.editTextNombrer);
        apellidos = (EditText) findViewById(R.id.editTextApellidos);
        telefono = (EditText) findViewById(R.id.editTextTelefono);
        email = (EditText) findViewById(R.id.editTextEmail);
        direccion = (EditText) findViewById(R.id.editTextDireccion);
        username = (EditText) findViewById(R.id.editTextUserName);
        password = (EditText) findViewById(R.id.editTextPassword);
        confirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        botonRegistro = (Button) findViewById(R.id.buttonRegistro);

        //Instanciomos la incerfaz de APIService mediante Retrofit
        APIService apiService = InstanciaRetrofit.getApiService();

        //Acción del botón Añadir
        botonRegistro.setOnClickListener(new View.OnClickListener() {
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
                //Si el campo del usuario o la contraseña está vacío muestra un Toast
                if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "El usuario o la contraseña esta vacío", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                //Revisa si las contraseñas coinciden en ambos campos
                if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    //Si las contraseñas no coinciden muestra un Toast
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    //Si las contraseñas coinciden envía los datos de registro de usuario al servidor
                    UsuarioResponse user = new UsuarioResponse(
                            email.getText().toString(),
                            username.getText().toString(),
                            password.getText().toString(),
                            nombre.getText().toString(),
                            apellidos.getText().toString(),
                            telefono.getText().toString(),
                            direccion.getText().toString()
                    );
                    Call<UsuarioResponse> callUsuario = apiService.setUsuario(user);
                    //Se hace la petición al servidor para añadir al usuario
                    callUsuario.enqueue(new Callback<UsuarioResponse>() {
                        @Override
                        public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                            if (response.isSuccessful()) {
                                Log.d("response", "Response nuevo usuario añadido=" + response.code());
                                //Se muestra un Toast conforme el usuario se ha añadido correctamente
                                Context context = getApplicationContext();
                                Toast toast = Toast.makeText(context, "El usuario " + username.getText() + " se ha añadido correctamente", Toast.LENGTH_SHORT);
                                toast.show();
                                //Salimos de la actividad
                                finish();
                            } else {
                                Log.d("response", "Ocurrió un error en la petición de usuario-->" + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                            Log.d("response", "Error de red-->" + t.getMessage());
                        }
                    });
                }
            }
        });
    }
}