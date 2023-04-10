package porqueras.ioc.proyectom13appmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import porqueras.ioc.proyectom13appmovil.modelos.AlquilerPeliculasPorId;
import porqueras.ioc.proyectom13appmovil.modelos.DetalleAlquiler;
import porqueras.ioc.proyectom13appmovil.utilidades.ApiUtils;
import porqueras.ioc.proyectom13appmovil.utilidades.InstanciaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Pantalla para modificar el estado del alquiler de la película
 *
 * @Author Esteban Porqueras Araque
 */
public class EstadoAlquiler extends AppCompatActivity {
    private APIService apiService;
    private RadioGroup radioGroupEstadoAlquiler;
    private RadioButton enCurso, finalizado, cancelado;
    private String idAlquiler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_alquiler);

        //Mantiene la orientación de la pantalla en vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Añadimos el título de la Activity en la barra superior
        setTitle("Actualizar estado del alquiler");

        //Recuperamos el idAlquiler de la actividad anterior
        Bundle extras = getIntent().getExtras();
        idAlquiler = extras.getString("idAlquiler");
        Log.d("response", "idAlquiler=" + idAlquiler);

        //Añadimos el RadioGroup y los RadioButtons
        radioGroupEstadoAlquiler = (RadioGroup) findViewById(R.id.radioGroupEstadoAlquiler);
        enCurso = (RadioButton) findViewById(R.id.radioButtonEnCurso);
        finalizado = (RadioButton) findViewById(R.id.radioButtonFinalizado);
        cancelado = (RadioButton) findViewById(R.id.radioButtonCancelado);

        //Instanciomos la incerfaz de APIService mediante Retrofit
        apiService = InstanciaRetrofit.getApiService();

        //Acciones de los RadioButtons
        radioGroupEstadoAlquiler.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Acciones a realizar cuando una opción es seleccionada
                switch (checkedId) {
                    case R.id.radioButtonEnCurso:
                        Log.d("response", "En curso");
                        modificarEstadoAlquiler("EN_CURSO");
                        break;
                    case R.id.radioButtonFinalizado:
                        Log.d("response", "Finalizado");
                        modificarEstadoAlquiler("FINALIZADO");
                        break;
                    case R.id.radioButtonCancelado:
                        Log.d("response", "Cancelado");
                        modificarEstadoAlquiler("CANCELADO");
                        break;
                }
            }
        });

        //Mostramos la información del estado del alquiler en los RadioButtons
        Call<AlquilerPeliculasPorId> alquilerPeliculasPorIdCall = apiService.alquilerPeliculaPorId(idAlquiler, ApiUtils.TOKEN);
        alquilerPeliculasPorIdCall.enqueue(new Callback<AlquilerPeliculasPorId>() {
            @Override
            public void onResponse(Call<AlquilerPeliculasPorId> call, Response<AlquilerPeliculasPorId> response) {
                if (response.isSuccessful()) {
                    Log.d("response", "Estado del alquiler=" + response.body().getValue().getEstado());
                    String estado = response.body().getValue().getEstado();
                    switch (estado) {
                        case "EN_CURSO":
                            enCurso.setChecked(true);
                            break;
                        case "FINALIZADO":
                            finalizado.setChecked(true);
                            break;
                        case "CANCELADO":
                            cancelado.setChecked(true);
                            break;
                    }
                } else {
                    Log.d("response", "Ha ocurrido un error, código=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<AlquilerPeliculasPorId> call, Throwable t) {
                Log.d("response", "Ha ocurrido un error");
            }
        });


    }

    /**
     * Se modifica el estado del alquiler
     *
     * @param estadoAlquiler puede ser EN_CURSO, FINAZLIZADO o CANCELADO
     */
    private void modificarEstadoAlquiler(String estadoAlquiler) {
        Call<Void> modificarEstado = apiService.updateEstadoAlquiler(estadoAlquiler, idAlquiler, ApiUtils.TOKEN);
        modificarEstado.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("response", "Estado actualizado a " + estadoAlquiler);

                } else {
                    Log.d("response", "Ha ocurrido un error, código=" + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("response", "Ha ocurrido un error");
            }
        });
    }

}