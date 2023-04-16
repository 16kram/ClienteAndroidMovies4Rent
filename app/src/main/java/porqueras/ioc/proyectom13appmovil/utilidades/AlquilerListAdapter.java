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

import porqueras.ioc.proyectom13appmovil.Alquiler;
import porqueras.ioc.proyectom13appmovil.Pelicula;
import porqueras.ioc.proyectom13appmovil.R;

/**
 * Adaptador para el listado de alquileres
 *
 * @Author Esteban Porqueras Araque
 */
public class AlquilerListAdapter extends RecyclerView.Adapter<AlquilerListAdapter.WordViewHolder> {
    //private final LinkedList<String> mWordList;
    private LinkedList<Alquiler> alquileres = new LinkedList<>();
    private LayoutInflater mInflater;
    final private AlquilerListAdapter.PasarIdListado pasarIdListado;
    private Alquiler alquiler;

    //Constructor, obtiene el inflador del contexto y sus datos
    public AlquilerListAdapter(Context context, LinkedList<Alquiler> alquileres,PasarIdListado pasarIdListado) {
        mInflater = LayoutInflater.from(context);
        this.alquileres = alquileres;
        this.pasarIdListado = pasarIdListado;
    }

    @NonNull
    @Override
    //Infla el diseño del elemento y devuelve un ViewHolder con el diseño y el adaptador
    public AlquilerListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.alquiler, parent, false);
        return new AlquilerListAdapter.WordViewHolder(mItemView, this);
    }

    @Override
    //Asocia los datos con el ViewHolder para una posición dada en el RecyclerView
    public void onBindViewHolder(@NonNull AlquilerListAdapter.WordViewHolder holder, int position) {
        Alquiler alquilerActual=alquileres.get(position);
        holder.tituloPelicula.setText(alquilerActual.getTituloPelicula());
        holder.usuario.setText("Usuario: "+alquilerActual.getUsuario());
    }

    @Override
    public int getItemCount() {
        return alquileres.size();
    }

    //Clase interna
    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tituloPelicula;
        public TextView usuario;
        public AlquilerListAdapter mAdapter;

        public WordViewHolder(@NonNull View itemView, AlquilerListAdapter adadpter) {
            super(itemView);
            tituloPelicula = itemView.findViewById(R.id.listar_peliculas_alquiladas_titulo);
            usuario=itemView.findViewById(R.id.listar_peliculas_alquiladas_usuario);
            this.mAdapter = adadpter;
            itemView.setOnClickListener(this);
        }

        //Si se pulsa un elemento de la lista
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
