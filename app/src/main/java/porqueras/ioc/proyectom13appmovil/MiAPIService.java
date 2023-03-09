package porqueras.ioc.proyectom13appmovil;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MiAPIService {
    @GET("posts/{id}")
    Call<ArticuloResponse> getArticulo(@Path("id")int id);
}
