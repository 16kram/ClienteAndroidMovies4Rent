package porqueras.ioc.proyectom13appmovil;

import java.util.List;

import porqueras.ioc.proyectom13appmovil.modelos.LoginResponse;
import porqueras.ioc.proyectom13appmovil.modelos.LogoutResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioInfoResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioListaResponse;
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
import retrofit2.http.Query;

public interface APIService {
    @GET("users")
    Call<UsuarioListaResponse> getUsuario(@Query("token") String token);

    @GET("users/info")
    Call<UsuarioInfoResponse> getValue(@Query("token") String token);

    @POST("register")
    Call<UsuarioResponse> setUsuario(@Body UsuarioResponse usuarioResponse);

    @PUT("updateusuari/{id}")
    Call<UsuarioUpdate> updateUsuario(@Path("id") int id, @Body UsuarioUpdate usuarioUpdate);

    @DELETE("users/delete/{id}")
    Call<Void> deleteUsuario(@Path("id") String userId,@Query("token")String token);

    @POST("login")
    Call<LoginResponse> getLogin(@Body LoginResponse loginResponse);

    @GET("logout")
    Call<LogoutResponse> getToken(@Query("token") String token);
}
