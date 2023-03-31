package porqueras.ioc.proyectom13appmovil;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;


public class PeliculasListAdapter extends RecyclerView.Adapter<PeliculasListAdapter.WordViewHolder> {
    private final LinkedList<String> mWordList;
    private LayoutInflater mInflater;
    final private PasarIdListado pasarIdListado;

    //Constructor, obtiene el inflador del contexto y sus datos
    public PeliculasListAdapter(Context context, LinkedList<String> mWordList, PasarIdListado pasarIdListado) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = mWordList;
        this.pasarIdListado = pasarIdListado;
    }

    @NonNull
    @Override
    //Infla el diseño del elemento y devuelve un ViewHolder con el diseño y el adaptador
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.pelicula, parent, false);
        return new PeliculasListAdapter.WordViewHolder(mItemView, this);
    }

    //Asocia los datos con el ViewHolder para una posición dada en el RecyclerView
    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        holder.movie.setText(mCurrent);
    }

    //Retorna el número de elementos de datos disponibles para mostrar
    @Override
    public int getItemCount() {
        return mWordList.size();
    }


    //Clase interna
    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView movie;
        public PeliculasListAdapter mAdapter;

        public WordViewHolder(@NonNull View itemView, PeliculasListAdapter adadpter) {
            super(itemView);
            movie = itemView.findViewById(R.id.movie);
            this.mAdapter = adadpter;
            itemView.setOnClickListener(this);
        }

        //Si se pulsa un elemento se la lista
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
     * en el listado de películas al método de la actividad
     * que implementa la interface
     */
    public interface PasarIdListado {
        void pasarPosicionListado(int posicion);
    }
}