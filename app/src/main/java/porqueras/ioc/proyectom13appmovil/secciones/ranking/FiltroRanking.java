package porqueras.ioc.proyectom13appmovil.secciones.ranking;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import porqueras.ioc.proyectom13appmovil.R;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;

/**
 * Pantalla para filtrar los rankings de las películas
 *
 * @Author Esteban Porqueras Araque
 */
public class FiltroRanking extends AppCompatActivity {
    private EditText titulo, director, genero, año;
    private Button botonFiltrarPelicula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_ranking);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Movies4Rent");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Filtro de ranking de películas");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_baseline_bar_chart_24);

        //Añadimos los campos de texto y los botones
        titulo = (EditText) findViewById(R.id.editTextFiltroRankingTitulo);
        director = (EditText) findViewById(R.id.editTextFiltroRankingDirector);
        genero = (EditText) findViewById(R.id.editTextFiltroRankingGenero);
        año = (EditText) findViewById(R.id.editTextFiltroRankingAño);
        botonFiltrarPelicula = (Button) findViewById(R.id.buttonFiltrarRankingPelicula);

        //Acción del botón filtrar ranking película
        botonFiltrarPelicula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si hay un número en el campo año se activa el filtro de años
                try {
                    ApiUtils.añoRanking = Integer.valueOf(año.getText().toString());
                    ApiUtils.filtroRanking = ApiUtils.SOLO_FILTRO_AÑO;
                } catch (Exception e) {
                    //En caso contrario se activa el filtro para los campos de texto
                    ApiUtils.filtroRanking = ApiUtils.SOLO_FILTRO_STRING;
                }

                //Añadimos los campos de texto a las variables
                ApiUtils.tituloRanking = titulo.getText().toString();
                ApiUtils.directorRanking = director.getText().toString();
                ApiUtils.generoRanking = genero.getText().toString();

                Log.d("response", "Número de filtro para el ranking=" + ApiUtils.filtroRanking);

                Intent i = new Intent(FiltroRanking.this, Ranking.class);
                startActivity(i);
            }
        });

    }
}