package porqueras.ioc.proyectom13appmovil.secciones.usuarios;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import porqueras.ioc.proyectom13appmovil.R;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;

/**
 * Pantalla para filtrar los usuarios
 *
 * @Author Esteban Porqueras Araque
 */
public class FiltroUsuarios extends AppCompatActivity {
    private Button botonFiltrarUsuario;
    private EditText filtroUsuariosNombre, filtroUsuariosApellidos, filtroUsuariosUsername;
    private String accion;
    private Spinner spinnerUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_usuarios);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Movies4Rent");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setSubtitle("Filtrar usuarios");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_baseline_supervised_user_circle_24);

        //Añadimos los campos de texto y los botones
        filtroUsuariosNombre = (EditText) findViewById(R.id.editTextFiltroUsuariosNombre);
        filtroUsuariosApellidos = (EditText) findViewById(R.id.editTextFiltroUsuariosApellidos);
        filtroUsuariosUsername = (EditText) findViewById(R.id.editTextFiltroUsuariosUsername);
        botonFiltrarUsuario = (Button) findViewById(R.id.buttonFiltrarUsuario);
        spinnerUsuario = (Spinner) findViewById(R.id.spinnerUsuario);

        //Recuperamos la acción a ejecutar de la actividad anterior
        Bundle extras = getIntent().getExtras();
        accion = extras.getString("accion");
        Log.d("response", "accion=" + accion);

        //Acción del botón filtrar usuarios
        botonFiltrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.filtroUsuarios = ApiUtils.TODOS;
                ApiUtils.nombre = filtroUsuariosNombre.getText().toString();
                ApiUtils.apellidos = filtroUsuariosApellidos.getText().toString();
                ApiUtils.username = filtroUsuariosUsername.getText().toString();

                if (!filtroUsuariosNombre.getText().toString().equals("") ||
                        !filtroUsuariosApellidos.getText().toString().equals("") ||
                        !filtroUsuariosUsername.getText().toString().equals("") ||
                        !ApiUtils.ordenarUsuariosPor.equals("Ninguno")) {
                    ApiUtils.filtroUsuarios = ApiUtils.FILTROS;
                }

                if (filtroUsuariosNombre.getText().toString().equals("")) {
                    ApiUtils.nombre = null;
                }
                if (filtroUsuariosApellidos.getText().toString().equals("")) {
                    ApiUtils.apellidos = null;
                }
                if (filtroUsuariosUsername.getText().toString().equals("")) {
                    ApiUtils.username = null;
                }
                if (ApiUtils.ordenarUsuariosPor == null || ApiUtils.ordenarUsuariosPor.equals("Ninguno")) {
                    ApiUtils.ordenarUsuariosPor = null;
                }

                Log.d("response", "Número de filtro para los usuarios=" + ApiUtils.filtroUsuarios);

                Intent i = new Intent(FiltroUsuarios.this, ListadoUsuarios.class);
                i.putExtra("accion", accion);
                startActivity(i);

            }
        });

        //Acción del Spinner
        spinnerUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("response", "Ha seleccionado " + parent.getItemAtPosition(position).toString());
                ApiUtils.ordenarUsuariosPor = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * Si se sale de la actividad se pone el filtro para que en el RecyclerView
     * se puedan visualizar todas los usuarios
     */
    protected void onDestroy() {
        super.onDestroy();
        ApiUtils.filtroUsuarios = ApiUtils.TODOS;
    }

}