package app.inei.appindicadorinei.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.modelo.pojos.SubIndicador;

public class ItemIndicadorAdapter extends RecyclerView.Adapter<ItemIndicadorAdapter.ViewHolderItem> {
    private ArrayList<SubIndicador> subindicadores;
    OnItemClickListener onItemClickListener;
    Context context;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public ItemIndicadorAdapter(ArrayList<SubIndicador> subindicadores, OnItemClickListener onItemClickListener) {
        this.subindicadores = subindicadores;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_indicador_row,viewGroup,false);
        ViewHolderItem viewHolder = new ViewHolderItem(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem viewHolder, final int position) {
        final SubIndicador subindicador = subindicadores.get(position);
        viewHolder.txtItem.setText(subindicador.getNombre_subindicador());
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,subindicador.getId_subindicador());
            }
        });

    }

    @Override
    public int getItemCount() {
        return subindicadores.size();
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView txtItem;
        CardView cv;
        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cardview_item_layout);
            txtItem = itemView.findViewById(R.id.txt_item_layout);
        }
    }

    public void setFilter(ArrayList<SubIndicador> datos)
    {
        this.subindicadores = new ArrayList<>();
        this.subindicadores.addAll(datos);
        notifyDataSetChanged();

    }
}
