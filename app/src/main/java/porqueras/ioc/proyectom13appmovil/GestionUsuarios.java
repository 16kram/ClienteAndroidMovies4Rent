package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GestionUsuarios extends AppCompatActivity {
    private Button listarUsuarios,ponerUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_usuarios);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Gestión de usuarios");

        //Añadimos los campos de texto y los botones
        listarUsuarios=(Button)findViewById(R.id.buttonListaUsuarios);
        ponerUsuarios=(Button) findViewById(R.id.buttonPonerUsuarios);

        //Acción del botón Listar Usuarios
        listarUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GestionUsuarios.this,ListadoUsuarios.class);
                startActivity(i);
            }
        });

        //Acción del botón Añadir Usuarios
        ponerUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GestionUsuarios.this,RegistroUsuario.class);
                startActivity(i);
            }
        });
    }
}