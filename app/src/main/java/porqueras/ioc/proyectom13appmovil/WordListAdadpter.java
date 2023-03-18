package porqueras.ioc.proyectom13appmovil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdadpter extends RecyclerView.Adapter<WordListAdadpter.WordViewHolder> {
    private final LinkedList<String> mWordList;
    private LayoutInflater mInflater;

    public WordListAdadpter(Context context, LinkedList<String> mWordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = mWordList;
    }

    @NonNull
    @Override
    //Infla el diseño del elemento y devuelve un ViewHolder con el diseño y el adaptador
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.usuario, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        holder.usuario.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    //Clase interna
    public class WordViewHolder extends RecyclerView.ViewHolder {
        public TextView usuario;
        public WordListAdadpter mAdapter;

        public WordViewHolder(@NonNull View itemView, WordListAdadpter adadpter) {
            super(itemView);
            usuario = itemView.findViewById(R.id.user);
            this.mAdapter = adadpter;
        }
    }
}
