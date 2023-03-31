package porqueras.ioc.proyectom13appmovil.secciones.peliculas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import porqueras.ioc.proyectom13appmovil.APIService;
import porqueras.ioc.proyectom13appmovil.ListadoPeliculas;
import porqueras.ioc.proyectom13appmovil.ModificarPelicula;
import porqueras.ioc.proyectom13appmovil.R;
import porqueras.ioc.proyectom13appmovil.RegistroPelicula;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaResponse;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GestionPeliculas extends AppCompatActivity {
    Button insertarPelicula, listarPeliculas, borrarPelicula, modificarPelicula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_peliculas);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Gestión de películas");

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
                startActivityForResult(i, 1234);
            }
        });

    }

    /**
     * El recyclerView nos devuelve el número de id de la película
     * y lo enviamos a la actividad modificar película
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1234 & resultCode == RESULT_OK) {
            //Recogemos el id de la película enviado por la actividad ListadoPeliculas
            String idPelicula = data.getExtras().getString("idPelicula");
            Log.d("response", "idPelicula=" + idPelicula);
            //Llamamos a la actividad modificar película
            Intent i = new Intent(GestionPeliculas.this, ModificarPelicula.class);
            //Le enviamos a la actividad ModificarPelicula
            //el id de la película seleccionada para que pueda modificar los datos
            i.putExtra("idPelicula", idPelicula);
            startActivity(i);
        }
    }
}