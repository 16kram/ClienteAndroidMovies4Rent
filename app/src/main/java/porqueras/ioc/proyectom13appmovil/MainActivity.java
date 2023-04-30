package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import porqueras.ioc.proyectom13appmovil.modelos.LoginResponse;
import porqueras.ioc.proyectom13appmovil.secciones.usuarios.RegistroUsuario;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase principal de la aplicación
 * <p>
 * En está clase el usuario se logea y
 * se pueden crear nuevo usuarios
 *
 * @author Esteban Porqueras Araque
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {
    APIService apiService;
    EditText usuario;
    EditText contrasena;
    Button buttonEntrar;
    Button buttonNuevoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos el certificado dentro de la carpeta raw
        InputStream inputStream = getResources().openRawResource(R.raw.demo);
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certificateFactory.generateCertificate(inputStream);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Verifica que el certificado del servidor es válido y se corresponde con la clave pública
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        for (X509Certificate cert : chain) {
                            cert.checkValidity();
                            try {
                                cert.verify(cert.getPublicKey());
                            } catch (InvalidKeyException e) {
                                e.printStackTrace();
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            } catch (NoSuchProviderException e) {
                                e.printStackTrace();
                            } catch (SignatureException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };

        //Creamos el objeto SSlContext que usa el objeto de TrustManager
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, trustAllCerts, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        //Almacenamos los objetos dentro de ApiUtils para que Retrofit los pueda utilizar
        ApiUtils.sslContext = sslContext;
        ApiUtils.trustAllCerts = trustAllCerts;

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Oculta la ActionBar
        getSupportActionBar().hide();

        //Añadimos los campos de texto y los botones
        usuario = (EditText) findViewById(R.id.editTextTextUsuario);
        contrasena = (EditText) findViewById(R.id.editTextTextContrasena);
        buttonEntrar = (Button) findViewById(R.id.buttonEntrar);
        buttonNuevoUsuario = (Button) findViewById(R.id.buttonNuevoUsuario);

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Acción del botón Entrar
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginResponse login = new LoginResponse(usuario.getText().toString(), contrasena.getText().toString());
                Call<LoginResponse> callLogin = apiService.getLogin(login);
                //Se comprueba si el usuario está registrado y puede logarse
                callLogin.enqueue((new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d("response", "Login Correcto, código=" + response.code());
                            Log.d("response", "mensaje=" + response.body().getMessage());
                            Log.d("response", "token=" + response.body().getValue().getToken());
                            Log.d("response", "admin=" + response.body().getValue().isAdmin());
                            ApiUtils.TOKEN = response.body().getValue().getToken();
                            usuario.setText(""); //Borra los campos Usuario
                            contrasena.setText("");// y Contraseña
                            if (response.body().getValue().isAdmin()) {
                                //El usuario es administrador
                                ApiUtils.administrador = true;
                                Intent i = new Intent(MainActivity.this, PantallaAdministrador.class);
                                startActivity(i);
                            } else {
                                //El usuario no es administrador
                                ApiUtils.administrador = false;
                                Intent i = new Intent(MainActivity.this, PantallaUsuario.class);
                                startActivity(i);
                            }
                        } else {
                            Log.d("response", "Ocurrió un error en la petición Login, código=" + response.code());
                            //Si la contraseña no coincide con la introducida en la base de datos del servidor muestra un Toast
                            Context context = getApplicationContext();
                            Toast toast = Toast.makeText(context, "La contraseña o el usuario no son válidos", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("response", "Error de red-->" + t.getMessage());
                        //Si hay un error con la conexión hacia el servidor se muestra un Toast de error
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, "Error de red", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }));
            }
        });

        //Acción del botón Nuevo Usuario
        buttonNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistroUsuario.class);
                startActivity(i);
            }
        });

    }

}