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
import app.inei.appindicadorinei.modelo.pojos.DataIndicador;
import app.inei.appindicadorinei.modelo.pojos.SubIndicador;

public class ItemContenidoM12Adapter extends RecyclerView.Adapter<ItemContenidoM12Adapter.ViewHolderItem> {
    private ArrayList<DataIndicador> subindicadores;
    OnItemClickListener onItemClickListener;
    Context context;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public ItemContenidoM12Adapter(ArrayList<DataIndicador> subindicadores) {
        this.subindicadores = subindicadores;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_contenido_m12,viewGroup,false);
        ViewHolderItem viewHolder = new ViewHolderItem(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem viewHolder, final int position) {
        final DataIndicador subindicador = subindicadores.get(position);
        viewHolder.txtItem1.setText(subindicador.getEjey());
        viewHolder.txtItem2.setText(Float.toString(subindicador.getDato()));
//        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onItemClickListener.onItemClick(view,subindicador.id_subindicador);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return subindicadores.size();
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView txtItem1;
        TextView txtItem2;
        CardView cv;
        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cardview_item_contenido_m12);
            txtItem1 = itemView.findViewById(R.id.txt_item_contenido1);
            txtItem2 = itemView.findViewById(R.id.txt_item_contenido2);
        }
    }


}
