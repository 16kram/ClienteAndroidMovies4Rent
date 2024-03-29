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

import porqueras.ioc.proyectom13appmovil.PantallaUsuario;
import porqueras.ioc.proyectom13appmovil.R;
import porqueras.ioc.proyectom13appmovil.secciones.alquileres.AlquilerPelicula;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;

/**
 * Pantalla para filtrar las películas
 *
 * @Author Esteban Porqueras Araque
 */
public class FiltroPeliculas extends AppCompatActivity {
    private Button botonFiltrarPelicula;
    private EditText filtroPeliculasTitulo,filtroPeliculasDirector, filtroPeliculasGenero, filtroPeliculasAño, filtroPeliculasVecesAlquilada;
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
        filtroPeliculasTitulo=(EditText)findViewById(R.id.editTextFiltroPeliculaTitulo);
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
                //Si no hay ningún campo rellenado, no se activa ningún filtro, y se visualizan todas las películas
                ApiUtils.filtroPeliculas = ApiUtils.TODAS;
                ApiUtils.titulo=filtroPeliculasTitulo.getText().toString();
                ApiUtils.director = filtroPeliculasDirector.getText().toString();
                ApiUtils.genero = filtroPeliculasGenero.getText().toString();
                //Si hay un número en el campo año se activa el filtro de años
                try {
                    ApiUtils.año = Integer.valueOf(filtroPeliculasAño.getText().toString());
                    ApiUtils.filtroPeliculas += ApiUtils.AÑO;
                } catch (Exception e) {

                }
                //Si hay algún número en el campo vecesAlquilada se activa el filtro de veces alquilada
                //Si hay números en los dos campos, año y veces alquiladas, se activa el filtro para años y veces alquilada
                try {
                    ApiUtils.vecesAlquilada = Integer.valueOf(filtroPeliculasVecesAlquilada.getText().toString());
                    ApiUtils.filtroPeliculas += ApiUtils.VECESALQUILADA;
                } catch (Exception e) {

                }
                //Si hay texto en algún campo de texto se aplican los filtros
                if ((!filtroPeliculasDirector.getText().toString().equals("") ||
                        !filtroPeliculasGenero.getText().toString().equals("") ||
                        !filtroPeliculasTitulo.getText().toString().equals("") ||
                        ApiUtils.ordenarPeliculasPor != null)
                        && filtroPeliculasAño.getText().toString().equals("")
                        && filtroPeliculasVecesAlquilada.getText().toString().equals("")) {
                    ApiUtils.filtroPeliculas = ApiUtils.DIRECTORGENERO;
                }

                Log.d("response", "Número de filtro para las películas=" + ApiUtils.filtroPeliculas);
                Log.d("response", "título=" + ApiUtils.titulo);
                Log.d("response", "director=" + ApiUtils.director);
                Log.d("response", "genero=" + ApiUtils.genero);
                Log.d("response", "año=" + ApiUtils.año);
                Log.d("response", "vecesAlquilada=" + ApiUtils.vecesAlquilada);
                Log.d("response", "ordenar por...=" + ApiUtils.ordenarPeliculasPor);

                //Lista las películas
                Intent i = new Intent(FiltroPeliculas.this, ListadoPeliculas.class);
                if (accion.equals("alquilar")) {
                    Log.d("response", "FiltroPeliculas-->Alquilar");
                    //Si la acción es modificar se espera el retorno del id del alquiler
                    i.putExtra("accion", "alquilar");
                    startActivityForResult(i, 9876);
                } else {
                    //Si la acción es distinta a modificar no se espera ningún retorno
                    i.putExtra("accion", accion);
                    startActivity(i);
                }
            }
        });

        //Acción del Spinner
        spinnerPelicula.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("response", "Ha seleccionado " + parent.getItemAtPosition(position).toString());
                ApiUtils.ordenarPeliculasPor = parent.getItemAtPosition(position).toString();
                if (ApiUtils.ordenarPeliculasPor.equals("Ninguno")) {
                    ApiUtils.ordenarPeliculasPor = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Deshabilitamos la opción del menú para filtrar elementos
        ApiUtils.menuFiltrarPeliculas = false;

    }


    /**
     * Desde el RecyclerView se nos devuelve el id del usuario y el id de la película
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9876 & resultCode == RESULT_OK) {
            String idPelicula = data.getExtras().getString("idPelicula");
            Log.d("response", "idPelícula=" + idPelicula);
            //Enviamos los datos de idUsuario e idPelícula a la actividad de AlquilerPelicula
            Intent i = new Intent(this, AlquilerPelicula.class);
            i.putExtra("idUsuario", ApiUtils.idUsuario);
            i.putExtra("idPelicula", idPelicula);
            startActivity(i);
            finish();
        }
    }


    /**
     * Si se sale de la actividad se pone el filtro para que en el RecyclerView
     * se puedan visualizar todas las películas
     */
    protected void onDestroy() {
        super.onDestroy();
        ApiUtils.filtroPeliculas = ApiUtils.TODAS;
        ApiUtils.menuFiltrarPeliculas = true;
    }

}