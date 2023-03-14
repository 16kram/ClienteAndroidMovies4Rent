package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import porqueras.ioc.proyectom13appmovil.utilidades.Logout;

public class PantallaAdministrador extends AppCompatActivity {
    Button botonLogoutAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_administrador);

        //Añadimos los campos de texto y los botones
        botonLogoutAdmin = (Button) findViewById(R.id.buttonLogoutAdmin);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Pantalla del administrador");

        //Acción del botón Logout
        botonLogoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Instanciomos la incerfaz de APIService mediante Retrofit
                APIService apiService = InstanciaRetrofit.getApiService();
                //Si el método Logout.sesion devuelve false es que se ha cerrado la sesión
                if (!Logout.sesion(apiService, getApplicationContext())) {
                    //Cerramos la actividad actual
                    finish();
                }
            }
        });
    }
}