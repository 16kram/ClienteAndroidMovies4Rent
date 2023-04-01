package porqueras.ioc.proyectom13appmovil.secciones.usuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import porqueras.ioc.proyectom13appmovil.APIService;
import porqueras.ioc.proyectom13appmovil.R;
import porqueras.ioc.proyectom13appmovil.modelos.PasswordUpdate;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para cambiar el password del usuario
 *
 * @author Esteban Porqueras Araque
 */
public class CambiarPassword extends AppCompatActivity {
    APIService apiService;
    EditText cambiarContraseña, repetirContraseña;
    Button botonModificarContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_password);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Moficar la contraseña");

        //Añadimos los campos de texto y los botones
        cambiarContraseña = (EditText) findViewById(R.id.editTextModificarPassword);
        repetirContraseña = (EditText) findViewById(R.id.editTextRepitePassword);
        botonModificarContraseña = (Button) findViewById(R.id.buttonCambiarPassword);

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Acción del botón Modificar Contraseña
        botonModificarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cambiarContraseña.getText().toString().equals(repetirContraseña.getText().toString())) {
                    //Si la los dos campos de la contraseña coinciden se modifica la contraseña
                    Log.d("response", "Las contraseñas coinciden");
                    PasswordUpdate passwordUpdate = new PasswordUpdate(
                            cambiarContraseña.getText().toString(), repetirContraseña.getText().toString());
                    Call<PasswordUpdate> callModificarPassword = apiService.changePassword(ApiUtils.TOKEN, passwordUpdate);
                    callModificarPassword.enqueue(new Callback<PasswordUpdate>() {
                        @Override
                        public void onResponse(Call<PasswordUpdate> call, Response<PasswordUpdate> response) {
                            if (response.isSuccessful()) {
                                Log.d("response", "Contraseña modificada");
                                //Muestra un Toast conforme se ha modificado la contraseña
                                Toast toast = Toast.makeText(getBaseContext(), "Contraseña actualizada", Toast.LENGTH_LONG);
                                toast.show();
                                finish();
                            } else {
                                Log.d("response", "No se ha podido mofificar la contraseña");
                                Toast toast = Toast.makeText(getBaseContext(), "No se ha podido mofificar la contraseña"
                                        + "\nCódigo: " + response.code(), Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<PasswordUpdate> call, Throwable t) {
                            Log.d("response", "Error de red: " + t.getMessage());
                        }
                    });
                } else {
                    //Muestra un Toast para indicar que las contraseñas introducidas son diferentes
                    Toast toast = Toast.makeText(getBaseContext(), "Las contraseñas introducidas no coinciden", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
}