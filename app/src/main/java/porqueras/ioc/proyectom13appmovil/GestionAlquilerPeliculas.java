package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import porqueras.ioc.proyectom13appmovil.secciones.peliculas.GestionPeliculas;
import porqueras.ioc.proyectom13appmovil.secciones.peliculas.ListadoPeliculas;
import porqueras.ioc.proyectom13appmovil.secciones.peliculas.ModificarPelicula;
import porqueras.ioc.proyectom13appmovil.secciones.usuarios.ListadoUsuarios;
import porqueras.ioc.proyectom13appmovil.utilidades.PeliculasListAdapter;

/**
 * Pantalla para la gestión del alquiler de las películas
 *
 * @Autor Esteban Porqueras Araque
 */
public class GestionAlquilerPeliculas extends AppCompatActivity {
    Button crearAlquiler, botónListarAlquiler, botonEliminarAlquiler, botonActualizarEstadoAlquiler;
    String idAlquiler, idPelicula, idUsuario; //Identificadores de alquiler, película y usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_alquiler_peliculas);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Gestión alquiler películas");

        //Añadimos los botones
        crearAlquiler = (Button) findViewById(R.id.buttonCrearAlquiler);
        botónListarAlquiler = (Button) findViewById(R.id.buttonListarAlquiler);
        botonEliminarAlquiler = (Button) findViewById(R.id.buttonEliminarAlquiler);
        botonActualizarEstadoAlquiler=(Button)findViewById(R.id.buttonActualizarEstadoAlquiler);

        //Acción del botón Crear Alquiler
        crearAlquiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Seleccionamos el usuario
                Intent i = new Intent(GestionAlquilerPeliculas.this, ListadoUsuarios.class);
                i.putExtra("accion", "alquilar");
                startActivityForResult(i, 8877);
            }
        });

        //Acción del botón Listar Alquileres
        botónListarAlquiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionAlquilerPeliculas.this, ListadoAlquileres.class);
                i.putExtra("accion","listar");
                startActivity(i);
            }
        });

        //Acción del botón Eliminar Alquiler
        botonEliminarAlquiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionAlquilerPeliculas.this, ListadoAlquileres.class);
                i.putExtra("accion","eliminar");
                startActivity(i);
            }
        });

        //Acción del botón Actualizar Estado Alquiler
        botonActualizarEstadoAlquiler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionAlquilerPeliculas.this, ListadoAlquileres.class);
                i.putExtra("accion","modificar");
                startActivityForResult(i,8888);
            }
        });
    }

    /**
     * El recyclerView nos devuelve el número de id
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Recibimos el id de la película para poder hacer el alquiler
        if (requestCode == 3311 & resultCode == RESULT_OK) {
            idPelicula = data.getExtras().getString("idPelicula");
            Log.d("response", "idPelicula=" + idPelicula);
            //Mostramos la pantalla de alquiler de películas
            Intent i = new Intent(GestionAlquilerPeliculas.this, AlquilerPelicula.class);
            i.putExtra("idUsuario", idUsuario);
            i.putExtra("idPelicula", idPelicula);
            startActivity(i);
        }
        //Recibimos el id del usuario para poder hacer el alquiler
        if (requestCode == 8877 & resultCode == RESULT_OK) {
            idUsuario = data.getExtras().getString("idUsuario");
            Log.d("response", "idUsuario=" + idUsuario);
            Intent intent = new Intent(GestionAlquilerPeliculas.this, ListadoPeliculas.class);
            intent.putExtra("accion", "alquilar");
            startActivityForResult(intent, 3311);
        }

        //Recibimos el id del alquiler de la película
        if(requestCode==8888 & resultCode==RESULT_OK){
            idAlquiler=data.getExtras().getString("idAlquiler");
            Log.d("response", "idAlquiler=" + idAlquiler);
            Intent intent = new Intent(GestionAlquilerPeliculas.this, EstadoAlquiler.class);
            intent.putExtra("idAlquiler",idAlquiler);
            startActivity(intent);
        }
    }
}