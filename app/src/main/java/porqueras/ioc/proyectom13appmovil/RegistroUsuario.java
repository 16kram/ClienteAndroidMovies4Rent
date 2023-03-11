package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegistroUsuario extends AppCompatActivity {
    EditText nombre,apellidos,telefono,email,address,username,password,confirmPassword;
    Button botonRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        setTitle("Registro de usuarios");

        //Añadimos los campos de texto y los botones
        nombre=(EditText)findViewById(R.id.editTextNombre);
        apellidos=(EditText) findViewById(R.id.editTextApellidos);
        telefono=(EditText) findViewById(R.id.editTextTelefono);
        email=(EditText) findViewById(R.id.editTextTelefono);
        address=(EditText) findViewById(R.id.editTextDireccion);
        username=(EditText) findViewById(R.id.editTextUserName);
        password=(EditText)findViewById(R.id.editTextPassword);
        confirmPassword=(EditText) findViewById(R.id.editTextConfirmPassword);


    }
}