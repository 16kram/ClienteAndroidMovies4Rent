package porqueras.ioc.proyectom13appmovil;

import java.util.List;

import porqueras.ioc.proyectom13appmovil.modelos.AlquilerListaResponse;
import porqueras.ioc.proyectom13appmovil.modelos.LoginResponse;
import porqueras.ioc.proyectom13appmovil.modelos.LogoutResponse;
import porqueras.ioc.proyectom13appmovil.modelos.PasswordUpdate;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaInfoResponse;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaListaResponse;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioInfoResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioListaResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioResponse;
import porqueras.ioc.proyectom13appmovil.modelos.UsuarioUpdate;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface donde se definen los endpoints que utilizará Retrofit
 *
 * @author Esteban Porqueras Araque
 */
public interface APIService {
    @GET("users")
    Call<UsuarioListaResponse> getUsuario(@Query("token") String token);

    @PUT("users/changepassword")
    Call<PasswordUpdate> changePassword(@Query("token") String token, @Body PasswordUpdate passwordUpdate);

    @GET("users/info")
    Call<UsuarioInfoResponse> getValue(@Query("token") String token);

    @GET("users/{id}")
    Call<UsuarioInfoResponse> getUsuarioId(@Path("id") String id, @Query("token") String token);

    @POST("register")
    Call<UsuarioResponse> setUsuario(@Body UsuarioResponse usuarioResponse);

    @PUT("users/update")
    Call<UsuarioUpdate> updateUsuario(@Query("token") String token, @Body UsuarioUpdate usuarioUpdate);

    @PUT("users/update/{id}/{admin}")
    Call<Void> setAdmin(@Path("id") String userId, @Path("admin") boolean isAdminParam, @Query("token") String token);

    @DELETE("users/delete/{id}")
    Call<Void> deleteUsuario(@Path("id") String userId, @Query("token") String token);

    @POST("peliculas/add")
    Call<PeliculaResponse> setPelicula(@Query("token") String token, @Body PeliculaResponse peliculaResponse);

    @GET("peliculas")
    Call<PeliculaListaResponse> getPeliculas(@Query("token") String token);

    @GET("peliculas/{id}")
    Call<PeliculaInfoResponse> getPelicula(@Path("id") String id, @Query("token") String token);

    @PUT("peliculas/update/{id}")
    Call<PeliculaResponse> updatePelicula(@Path("id") String id, @Query("token") String token, @Body PeliculaResponse peliculaResponse);

    @DELETE("peliculas/delete/{id}")
    Call<Void> deletePelicula(@Path("id") String id, @Query("token") String token);

    @POST("peliculas/alquileres/nuevo")
    Call<Void> nuevoAlquiler(@Query("peliculaId") String peliculaId, @Query("usuariId") String usuariId, @Query("token") String token);

    @GET("peliculas/alquileres")
    Call<AlquilerListaResponse> getAlquileres(@Query("token") String token);

    @POST("login")
    Call<LoginResponse> getLogin(@Body LoginResponse loginResponse);

    @GET("logout")
    Call<LogoutResponse> getToken(@Query("token") String token);
}
