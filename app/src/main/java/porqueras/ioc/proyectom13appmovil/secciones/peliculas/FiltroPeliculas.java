package porqueras.ioc.proyectom13appmovil.secciones.peliculas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import porqueras.ioc.proyectom13appmovil.R;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;

/**
 * Pantalla para filtrar las películas
 *
 * @Author Esteban Porqueras Araque
 */
public class FiltroPeliculas extends AppCompatActivity {
    private Button botonFiltrarPelicula;
    private EditText filtroPeliculasDirector, filtroPeliculasGenero, filtroPeliculasAño, filtroPeliculasVecesAlquilada;
    private String accion;
    private Spinner spinnerPelicula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_peliculas);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Movies4Rent");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Filtrar películas");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_baseline_local_movies_24);

        //Añadimos los campos de texto y los botones
        filtroPeliculasDirector = (EditText) findViewById(R.id.editTextFiltroPeliculasDirector);
        filtroPeliculasGenero = (EditText) findViewById(R.id.editTextFiltroPeliculasGenero);
        filtroPeliculasAño = (EditText) findViewById(R.id.editTextFiltroPeliculaAño);
        filtroPeliculasVecesAlquilada = (EditText) findViewById(R.id.editTextFiltroPeliculasVecesAlquilada);
        botonFiltrarPelicula = (Button) findViewById(R.id.buttonFiltrarPelicula);
        spinnerPelicula = (Spinner) findViewById(R.id.spinnerPelicula);

        //Recuperamos la acción a ejecutar de la actividad anterior
        Bundle extras = getIntent().getExtras();
        accion = extras.getString("accion");
        Log.d("response", "accion=" + accion);

        //Acción del botón filtrar película
        botonFiltrarPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.filtroPeliculas = ApiUtils.TODAS;
                ApiUtils.director = filtroPeliculasDirector.getText().toString();
                ApiUtils.genero = filtroPeliculasGenero.getText().toString();
                try {
                    ApiUtils.año = Integer.valueOf(filtroPeliculasAño.getText().toString());
                    ApiUtils.filtroPeliculas += ApiUtils.AÑO;
                } catch (Exception e) {

                }
                try {
                    ApiUtils.vecesAlquilada = Integer.valueOf(filtroPeliculasVecesAlquilada.getText().toString());
                    ApiUtils.filtroPeliculas += ApiUtils.VECESALQUILADA;
                } catch (Exception e) {

                }
                if ((!filtroPeliculasDirector.getText().toString().equals("") || !filtroPeliculasGenero.getText().toString().equals(""))
                        && filtroPeliculasAño.getText().toString().equals("")
                        && filtroPeliculasVecesAlquilada.getText().toString().equals("")) {
                    ApiUtils.filtroPeliculas = ApiUtils.DIRECTORGENERO;
                }
                if (!ApiUtils.ordenarPeliculasPor.equals("Ninguno")) {
                    ApiUtils.filtroPeliculas = ApiUtils.ASCDESC;
                }

                Log.d("response", "Número de filtro para las películas=" + ApiUtils.filtroPeliculas);

                Intent i = new Intent(FiltroPeliculas.this, ListadoPeliculas.class);
                i.putExtra("accion", accion);
                startActivity(i);
            }
        });

        //Acción del Spinner
        spinnerPelicula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("response", "Ha seleccionado " + parent.getItemAtPosition(position).toString());
                ApiUtils.ordenarPeliculasPor = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * Si se sale de la actividad se pone el filtro para que en el RecyclerView
     * se puedan visualizar todas las películas
     */
    protected void onDestroy() {
        super.onDestroy();
        ApiUtils.filtroPeliculas = ApiUtils.TODAS;
    }

}