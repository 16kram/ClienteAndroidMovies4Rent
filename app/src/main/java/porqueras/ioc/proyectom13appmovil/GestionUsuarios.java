package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Pantalla el administrador para gestionar usuarios
 *
 * @author Esteban Porqueras Araque
 */
public class GestionUsuarios extends AppCompatActivity {
    private Button listarUsuarios, ponerUsuarios, modificarRolUsuarios, borrarUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_usuarios);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Gestión de usuarios");

        //Añadimos los campos de texto y los botones
        listarUsuarios = (Button) findViewById(R.id.buttonListaUsuarios);
        ponerUsuarios = (Button) findViewById(R.id.buttonPonerUsuarios);
        modificarRolUsuarios = (Button) findViewById(R.id.buttonModificarRolUsuario);
        borrarUsuarios = (Button) findViewById(R.id.buttonBorrarUsuarios);

        //Acción del botón Listar Usuarios
        listarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionUsuarios.this, ListadoUsuarios.class);
                i.putExtra("accion", "listar");
                startActivity(i);
            }
        });

        //Acción del botón Añadir Usuarios
        ponerUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionUsuarios.this, RegistroUsuario.class);
                startActivity(i);
            }
        });

        //Acción del botón modificar rol usuarios
        modificarRolUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionUsuarios.this, ListadoUsuarios.class);
                i.putExtra("accion", "modificar");
                startActivity(i);
            }
        });

        //Acción del botón borrar
        borrarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionUsuarios.this, ListadoUsuarios.class);
                i.putExtra("accion", "borrar");
                startActivity(i);
            }
        });
    }

}