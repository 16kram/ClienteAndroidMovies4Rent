package porqueras.ioc.proyectom13appmovil.utilidades;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

//Conversor para gestionar las respuestas del servidor con cuerpos nulos
public class NullConverterFactory extends Converter.Factory {
    /**
     * Crea una instancia de un convertidor para un tipo específico
     *
     * @param type        el tipo para el que se creará el convertidor
     * @param annotations anotaciones en la declaración del tipo
     * @param retrofit    una referencia a la instancia de retrofit
     * @return devuelve una instancia de un convertidor que maneja las respuestas nulas
     */
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody body) throws IOException {
                if (body.contentLength() == 0) return null;
                return delegate.convert(body);
            }
        };
    }
}
