package porqueras.ioc.proyectom13appmovil;

import retrofit2.http.GET;
import  retrofit2.Call;
import retrofit2.http.Path;

public interface APIService {
    @GET("usuaris")
    Call<UsuarioResponse> getUsuario();
}
