package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Switch;

public class ModificarRolUsuario extends AppCompatActivity {
    APIService apiService;
    String id;//Identificador del usuario
    Switch switchAdministrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_rol_usuario);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Modificar el rol del usuario");

        //Recupera el id del usuario enviado desde la actividad anterior
        Bundle extras = getIntent().getExtras();
        id = extras.getString("id");
    }
}

/*
    //Se actualiza el dato de si el usuario es administrador o no
    //Sólo si el usuario actual es administrador
    boolean admin;
                if (switchAdministrador.isChecked()) {
                        admin = true;
                        } else {
                        admin = false;
                        }
                        Log.d("response", "Id usuario=" + id);
                        if (ApiUtils.administrador) {
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
        }*/