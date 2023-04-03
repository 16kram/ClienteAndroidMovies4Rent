package porqueras.ioc.proyectom13appmovil.utilidades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import porqueras.ioc.proyectom13appmovil.R;

/**
 * Adaptador para el listado de alquileres
 *
 * @Author Esteban Porqueras Araque
 */
public class AlquilerListAdapter extends RecyclerView.Adapter<AlquilerListAdapter.WordViewHolder> {
    private final LinkedList<String> mWordList;
    private LayoutInflater mInflater;

    //Constructor, obtiene el inflador del contexto y sus datos
    public AlquilerListAdapter(Context context, LinkedList<String> mWordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = mWordList;
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
        String mCurrent = mWordList.get(position);
        holder.alquiler.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    //Clase interna
    public class WordViewHolder extends RecyclerView.ViewHolder {
        public TextView alquiler;
        public AlquilerListAdapter mAdapter;

        public WordViewHolder(@NonNull View itemView, AlquilerListAdapter adadpter) {
            super(itemView);
            alquiler = itemView.findViewById(R.id.alquiler);
            this.mAdapter = adadpter;
        }
    }
}
