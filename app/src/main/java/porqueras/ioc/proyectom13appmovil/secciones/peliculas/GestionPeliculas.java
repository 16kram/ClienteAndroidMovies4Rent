package porqueras.ioc.proyectom13appmovil.secciones.peliculas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import porqueras.ioc.proyectom13appmovil.R;

/**
 * Pantalla para la gestión de las películas
 *
 * @Autor Esteban Porqueras Araque
 */
public class GestionPeliculas extends AppCompatActivity {
    Button insertarPelicula, listarPeliculas, borrarPelicula, modificarPelicula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_peliculas);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Movies4Rent");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Gestión de películas");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_baseline_local_movies_24);

        //Añadimos los botones
        insertarPelicula = (Button) findViewById(R.id.buttonPonerPeliculas);
        listarPeliculas = (Button) findViewById(R.id.buttonListaPeliculas);
        borrarPelicula = (Button) findViewById(R.id.buttonBorrarPeliculas);
        modificarPelicula = (Button) findViewById(R.id.buttonModificarPelicula);

        //Acción del botón añadir película
        insertarPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionPeliculas.this, RegistroPelicula.class);
                startActivity(i);
            }
        });

        //Acción del botón listar películas
        listarPeliculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionPeliculas.this, ListadoPeliculas.class);
                i.putExtra("accion", "listar");
                startActivity(i);
            }
        });

        //Acción del botón borrar películas
        borrarPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionPeliculas.this, ListadoPeliculas.class);
                i.putExtra("accion", "borrar");
                startActivity(i);
            }
        });

        //Acción del botón modificar películas
        modificarPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionPeliculas.this, ListadoPeliculas.class);
                i.putExtra("accion", "modificar");
                startActivity(i);
                //startActivityForResult(i, 1234);
            }
        });

    }

}