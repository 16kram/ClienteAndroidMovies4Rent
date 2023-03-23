package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import porqueras.ioc.proyectom13appmovil.modelos.UsuarioInfoResponse;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase para modificar el rol del usuario
 *
 * @author Esteban Porqueras Araque
 */
public class ModificarRolUsuario extends AppCompatActivity {
    private APIService apiService;
    private String id;//Identificador del usuario
    private Switch switchAdministrador;
    private TextView nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_rol_usuario);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Modificar el rol del usuario");

        //Añadimos el texto y el switch
        nombreUsuario=(TextView)findViewById(R.id.textViewRolUsuario);
        switchAdministrador = (Switch) findViewById(R.id.switchAdministrador);

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Recupera el id del usuario enviado desde la actividad anterior
        Bundle extras = getIntent().getExtras();
        id = extras.getString("id");
        Log.d("response", "id=" + id);

        //Pone el nombre del usuario seleccionado en la pantalla
        Call<UsuarioInfoResponse> callUsuarioInfoResponse=apiService.getUsuarioId(id,ApiUtils.TOKEN);
        callUsuarioInfoResponse.enqueue(new Callback<UsuarioInfoResponse>() {
            @Override
            public void onResponse(Call<UsuarioInfoResponse> call, Response<UsuarioInfoResponse> response) {
                if(response.isSuccessful()){
                    nombreUsuario.setText(response.body().getValue().getNombre()+
                            " "+response.body().getValue().getApellidos());
                }else{
                    Log.d("response","Ha ocurrido un error, código="+response.code());
                }
            }

            @Override
            public void onFailure(Call<UsuarioInfoResponse> call, Throwable t) {
                Log.d("response", "Error de red-->" + t.getMessage());
            }
        });

        //Acción de Swich Administrador
        switchAdministrador.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (switchAdministrador.isChecked()) {
                    //El usuario se convierte en administrador
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "El usuario pasa a ser administrador", Toast.LENGTH_SHORT);
                    toast.show();
                    modificaRolUsuario(true);
                } else {
                    //El usuario se convierte en usuario normal
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, "El usuario se convierte en un usuario normal", Toast.LENGTH_SHORT);
                    toast.show();
                    modificaRolUsuario(false);
                }
            }
        });

    }

    /**
     * Hace una llamada al servidor para modificar el rol del usuario
     *
     * @param admin si admin=true el usuario se convierte en administrador, false=el usuario deja de ser administrador
     */
    private void modificaRolUsuario(boolean admin) {
        //Se hace la llamada al servidor para modificar el estado del usuario, si es administrador o no
        Call<Void> callSetAdmin = apiService.setAdmin(id, "admin", admin, ApiUtils.TOKEN);
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

}
