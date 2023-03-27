package porqueras.ioc.proyectom13appmovil.secciones.peliculas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import porqueras.ioc.proyectom13appmovil.R;

public class GestionPeliculas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_peliculas);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


    }
}