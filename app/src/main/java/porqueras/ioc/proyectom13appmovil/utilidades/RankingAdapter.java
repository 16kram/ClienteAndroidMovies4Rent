package porqueras.ioc.proyectom13appmovil.utilidades;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import porqueras.ioc.proyectom13appmovil.R;
import porqueras.ioc.proyectom13appmovil.secciones.peliculas.Pelicula;
import porqueras.ioc.proyectom13appmovil.secciones.ranking.RankingPelicula;

/**
 * Adapdador del RecyclerView para el ranking de las películas
 *
 * @Author Esteban Porqueras Araque
 */
public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.WordViewHolder> {
    private LinkedList<RankingPelicula> peliculas = new LinkedList<>();
    private LayoutInflater mInflater;
    final private PasarIdListado pasarIdListado;
    private Pelicula pelicula;

    //Constructor, obtiene el inflador del contexto y sus datos
    public RankingAdapter(Context context, LinkedList<RankingPelicula> peliculas, PasarIdListado pasarIdListado) {
        mInflater = LayoutInflater.from(context);
        this.peliculas = peliculas;
        this.pasarIdListado = pasarIdListado;
    }


    @NonNull
    @Override
    //Infla el diseño del elemento y devuelve un ViewHolder con el diseño y el adaptador
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.ranking, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    //Asocia los datos con el ViewHolder para una posición dada en el RecyclerView
    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        RankingPelicula peliculaActual = peliculas.get(position);
        holder.tituloPeliculaRanking.setText(peliculaActual.getTitulo());
        holder.directorPeliculaRanking.setText("Director: " + peliculaActual.getDirector());
    }

    //Retorna el número de elementos de datos disponibles para mostrar
    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    //Clase interna
    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tituloPeliculaRanking;
        public TextView directorPeliculaRanking;
        public RankingAdapter mAdapter;

        public WordViewHolder(@NonNull View itemView, RankingAdapter adadpter) {
            super(itemView);
            tituloPeliculaRanking = itemView.findViewById(R.id.titulo_pelicula_ranking);
            directorPeliculaRanking = itemView.findViewById(R.id.director_pelicula_ranking);
            this.mAdapter = adadpter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Coge la posición del elemento de la lista que fue pulsado
            int posicion = getLayoutPosition();
            Log.d("response", "Elemento pulsado=" + posicion);
            //Enviamos al interface la posición del elemento pulsado
            pasarIdListado.pasarPosicionListado(posicion);
        }
    }

    /**
     * Pasamos el número de posición que se ha clicado
     * en el listado de ranking de películas al método de la actividad
     * que implementa la interface
     */
    public interface PasarIdListado {
        void pasarPosicionListado(int posicion);
    }
}
