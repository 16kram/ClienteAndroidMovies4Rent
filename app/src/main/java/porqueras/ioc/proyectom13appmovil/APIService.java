package porqueras.ioc.proyectom13appmovil;

import java.util.List;

import porqueras.ioc.proyectom13appmovil.modelos.LoginResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioUpdate;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import  retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    @GET("usuaris")
    Call<List<UsuarioResponse>> getUsuario();

    @POST("addusuari")
    Call<UsuarioResponse> setUsuario(@Body UsuarioResponse usuarioResponse);

    @PUT("updateusuari/{id}")
    Call<UsuarioUpdate> updateUsuario(@Path("id") int id, @Body UsuarioUpdate usuarioUpdate);

    @DELETE("deleteusuari/{id}")
    Call<Void> deleteUsuario(@Path("id") int id);

    @POST("login")
    Call<LoginResponse> getLogin(@Body LoginResponse loginResponse);


}
