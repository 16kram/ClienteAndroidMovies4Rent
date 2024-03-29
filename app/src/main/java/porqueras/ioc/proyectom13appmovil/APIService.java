package porqueras.ioc.proyectom13appmovil;

import androidx.annotation.Nullable;

import java.util.List;

import porqueras.ioc.proyectom13appmovil.modelos.AlquilerListaResponse;
import porqueras.ioc.proyectom13appmovil.modelos.AlquilerPeliculasPorId;
import porqueras.ioc.proyectom13appmovil.modelos.DetalleAlquiler;
import porqueras.ioc.proyectom13appmovil.modelos.LoginResponse;
import porqueras.ioc.proyectom13appmovil.modelos.LogoutResponse;
import porqueras.ioc.proyectom13appmovil.modelos.PasswordUpdate;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaInfoResponse;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaListaResponse;
import porqueras.ioc.proyectom13appmovil.modelos.PeliculaListaResponseAlquilerPorId;
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
    Call<UsuarioListaResponse> getUsuarios(@Query("page") int page, @Query("pageSize") int pageSize, @Query("token") String token);

    @GET("users")
    Call<UsuarioListaResponse> getUsuariosFiltros(@Query("page") int page, @Query("pageSize") int pageSize, @Query("token") String token, @Query("nombre") String nombre, @Query("apellidos") String apellidos, @Query("username") String username, @Query("orden") String orden);

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
    Call<PeliculaListaResponse> getPeliculasAñoVecesAlquilada(@Query("page") int page, @Query("pageSize") int pageSize, @Query("token") String token, @Query("titulo")String titulo,@Query("director") String director, @Query("genero") String genero, @Query("ano") int año, @Query("vecesAlquilada") int vecesAlquilada, @Query("orden") String orden);

    @GET("peliculas")
    Call<PeliculaListaResponse> getPeliculasAño(@Query("page") int page, @Query("pageSize") int pageSize, @Query("token") String token,  @Query("titulo")String titulo,@Query("director") String director, @Query("genero") String genero, @Query("ano") int año, @Query("orden") String orden);

    @GET("peliculas")
    Call<PeliculaListaResponse> getPeliculasVecesAlquilada(@Query("page") int page, @Query("pageSize") int pageSize, @Query("token") String token,  @Query("titulo")String titulo,@Query("director") String director, @Query("genero") String genero, @Query("vecesAlquilada") int vecesAlquilada, @Query("orden") String orden);

    @GET("peliculas")
    Call<PeliculaListaResponse> getPeliculasDirectorGenero(@Query("page") int page, @Query("pageSize") int pageSize, @Query("token") String token,  @Query("titulo")String titulo,@Query("director") String director, @Query("genero") String genero, @Query("orden") String orden);

    @GET("peliculas")
    Call<PeliculaListaResponse> getTodasLasPeliculas(@Query("page") int page, @Query("pageSize") int pageSize, @Query("token") String token);

    @GET("peliculas/{id}")
    Call<PeliculaInfoResponse> getPelicula(@Path("id") String id, @Query("token") String token);

    @PUT("peliculas/update/{id}")
    Call<PeliculaResponse> updatePelicula(@Path("id") String id, @Query("token") String token, @Body PeliculaResponse peliculaResponse);

    @DELETE("peliculas/delete/{id}")
    Call<Void> deletePelicula(@Path("id") String id, @Query("token") String token);

    @POST("peliculas/alquileres/nuevo")
    Call<DetalleAlquiler> newAlquiler(@Query("peliculaId") String peliculaId, @Query("usuariId") String usuariId, @Query("token") String token);

    @GET("/peliculas/alquileres")
    Call<AlquilerListaResponse> getAlquileres(@Query("page") int page, @Query("pageSize") int pageSize, @Query("token") String token);

    @GET("/peliculas/alquileres")
    Call<AlquilerListaResponse> getAlquileresFiltroPrecio(@Query("page") int page, @Query("pageSize") int pageSize, @Query("token") String token, @Query("peliculaId") String peliculaId, @Query("usuariId") String usuariId, @Query("fechaInicio") String fechaInicio, @Query("fechaFin") String fechaFin, @Query("precio") int precio, @Query("orden") String orden);

    @GET("/peliculas/alquileres")
    Call<AlquilerListaResponse> getAlquileresFiltros(@Query("page") int page, @Query("pageSize") int pageSize, @Query("token") String token, @Query("peliculaId") String peliculaId, @Query("usuariId") String usuariId, @Query("fechaInicio") String fechaInicio, @Query("fechaFin") String fechaFin, @Query("orden") String orden);

    @GET("/peliculas/alquileres/alquilerByUser/{usuarioId}")
    Call<PeliculaListaResponseAlquilerPorId> getPeliculasAlquilerPorUsuario(@Path("usuarioId") String usuarioId, @Query("token") String token);

    @GET("/peliculas/alquileres/{alquilerId}")
    Call<AlquilerPeliculasPorId> alquilerPeliculaPorId(@Path("alquilerId") String alquilerId, @Query("token") String token);

    @PUT("/peliculas/alquileres/updateStatus")
    Call<Void> updateEstadoAlquiler(@Query("estadoAlquiler") String estadoAlquiler, @Query("alquilerId") String AlquilerId, @Query("token") String token);

    @DELETE("/peliculas/alquileres/delete/{alquilerId}")
    Call<Void> deleteAlquiler(@Path("alquilerId") String idAlquiler, @Query("token") String token);

    @GET("/peliculas/ranking")
    Call<PeliculaListaResponse> getRanking(@Query("page") int page, @Query("pageSize") int pageSize, @Query("token") String token);

    @GET("/peliculas/ranking")
    Call<PeliculaListaResponse> getRankingFiltroAño(@Query("page") int page, @Query("pageSize") int pageSize, @Query("token") String token, @Query("titulo") String titulo, @Query("director") String director, @Query("genero") String genero, @Query("ano") int año);

    @GET("/peliculas/ranking")
    Call<PeliculaListaResponse> getRankingFiltros(@Query("page") int page, @Query("pageSize") int pageSize, @Query("token") String token, @Query("titulo") String titulo, @Query("director") String director, @Query("genero") String genero);

    @POST("login")
    Call<LoginResponse> getLogin(@Body LoginResponse loginResponse);

    @GET("logout")
    Call<LogoutResponse> getToken(@Query("token") String token);
}
