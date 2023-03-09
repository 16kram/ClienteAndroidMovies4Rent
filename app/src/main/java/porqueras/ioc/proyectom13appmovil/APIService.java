package porqueras.ioc.proyectom13appmovil;

import java.util.List;

import retrofit2.http.GET;
import  retrofit2.Call;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("usuaris")
    Call<List<UsuarioResponse>> getUsuario();

    @GET("login")
    Call<LoginResponse> getLogin(
            @Query("username") String username,
            @Query("password") String password
    );

}
