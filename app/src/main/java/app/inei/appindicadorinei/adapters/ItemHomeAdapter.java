package app.inei.appindicadorinei.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.modelo.pojos.Indicador;

public class ItemHomeAdapter extends RecyclerView.Adapter<ItemHomeAdapter.ViewHolderItem> {
    private ArrayList<Indicador> indicadores;
    private ArrayList<Integer> iconos;
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public ItemHomeAdapter(ArrayList<Indicador> indicadores, OnItemClickListener onItemClickListener) {
        this.indicadores = indicadores;
        this.onItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_indicador_home,viewGroup,false);
        ViewHolderItem viewHolder = new ViewHolderItem(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem viewHolder, int position) {
        iconos = new ArrayList<>();
        iconos.add(R.drawable.ic1_people_outline);
        iconos.add(R.drawable.ic2_assignment);
        iconos.add(R.drawable.ic3_wc);
        iconos.add(R.drawable.ic4_school);
        iconos.add(R.drawable.ic5_local_hospital);
        iconos.add(R.drawable.ic6_location_city);
        iconos.add(R.drawable.ic7_lightbulb_outline);
        iconos.add(R.drawable.ic8_settings_input_antenna);
        iconos.add(R.drawable.ic9_terrain);
        iconos.add(R.drawable.ic10_domain);
        iconos.add(R.drawable.ic11_account_balance);
        iconos.add(R.drawable.ic12_security);
        iconos.add(R.drawable.ic13_restaurant);
        iconos.add(R.drawable.ic14_toys);
        iconos.add(R.drawable.ic15_widgets);
        iconos.add(R.drawable.ic16_attach_money);
        iconos.add(R.drawable.ic17_exit_to_app);
        iconos.add(R.drawable.ic18_local_atm);
        iconos.add(R.drawable.ic19_monetization_on);
        iconos.add(R.drawable.ic20_directions_bus);
        iconos.add(R.drawable.ic21_directions_run);

        final Indicador indicador = indicadores.get(position);
        viewHolder.txtItem.setText(indicador.getNombre_indicador());
        viewHolder.img1Item.setImageResource(iconos.get(position));
        viewHolder.img2Item.setImageResource(R.drawable.ic_more_vert_black_24dp);
        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,indicador.getId_indicador());
            }
        });

    }

    @Override
    public int getItemCount() {
        return indicadores.size();
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder{
        CardView cv;
        TextView txtItem;
        ImageView img1Item;
        ImageView img2Item;

        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.id_carview_home);
            txtItem = itemView.findViewById(R.id.id_textview_home);
            img1Item = itemView.findViewById(R.id.id_imageview1_home);
            img2Item = itemView.findViewById(R.id.id_imageview2_home);
        }
    }
}
