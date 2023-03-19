package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class ModificarUsuario extends AppCompatActivity {
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_usuario);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Actualizar datos del usuario");

        Bundle extras = getIntent().getExtras();
        id = extras.getString("id");
        Log.d("response","id="+id);
    }
}