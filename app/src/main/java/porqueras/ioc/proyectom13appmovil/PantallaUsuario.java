package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PantallaUsuario extends AppCompatActivity {
    Button botonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_usuario);
        setTitle("Pantalla provisional de usuario");

        //Añadimos los campos de texto y los botones
        botonLogout=(Button)findViewById(R.id.buttonLogout);

        //Acción del botón Logout
        botonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}