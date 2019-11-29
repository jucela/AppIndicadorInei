package app.inei.appindicadorinei.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.modelo.pojos.SubIndicador;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class ItemListaM12Adapter extends RecyclerView.Adapter<ItemListaM12Adapter.ViewHolderItem> {
    private ArrayList<String> datosejex;
    OnItemClickListener onItemClickListener;
    Context context;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public ItemListaM12Adapter(ArrayList<String> datosejex, OnItemClickListener onItemClickListener) {
        this.datosejex = datosejex;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista_m12,viewGroup,false);
        ViewHolderItem viewHolder = new ViewHolderItem(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem viewHolder, final int position) {
        final String datoejex = datosejex.get(position);
        int tamanio= datoejex.length();

          viewHolder.txt_anio.setText(datoejex);
          viewHolder.cv2.setVisibility(View.VISIBLE);
          viewHolder.cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,position+1);
            }
        });



//        if(tamanio>4)
//        {  if(tamanio>20)
//           {
//               viewHolder.txt_anio.setTextSize(10);
//           }
//           else {
//            viewHolder.txt_anio.setTextSize(15);
//           }
//          viewHolder.txt_anio.setText(datoejex);
//          viewHolder.cv2.setVisibility(View.VISIBLE);
//          viewHolder.cv2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onItemClickListener.onItemClick(view,position+1);
//            }
//        });
//        }
//        else {
//            viewHolder.flotante.setImageBitmap(textAsBitmap(datoejex, 60, Color.WHITE));
//            viewHolder.cv1.setVisibility(View.VISIBLE);
//            viewHolder.cv1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //onItemClickListener.onItemClick(view,Integer.parseInt(datoejex));
//                    onItemClickListener.onItemClick(view,position+1);
//                }
//            });
//
//        }


    }

    @Override
    public int getItemCount() {
        return datosejex.size();
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView txt_anio;
        FloatingActionButton flotante;
        CardView cv1,cv2;
        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            cv1 = itemView.findViewById(R.id.cardview_item_lista_m12_1);
            cv2 = itemView.findViewById(R.id.cardview_item_lista_m12_2);
            flotante = itemView.findViewById(R.id.fab_anio);
            txt_anio = itemView.findViewById(R.id.txt_anio);
        }
    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor)
    { Paint paint = new Paint(ANTI_ALIAS_FLAG);
      paint.setTextSize(textSize);
      paint.setColor(textColor);
      paint.setTextAlign(Paint.Align.LEFT);
      float baseline = -paint.ascent();
      //ascent() is negative
       int width = (int) (paint.measureText(text) + 0.0f);
       //round
       int height = (int) (baseline + paint.descent() + 0.0f);
       Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
       Canvas canvas = new Canvas(image); canvas.drawText(text, 0, baseline, paint);
        return image; }


}
