package app.inei.appindicadorinei.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.activities.Home_Activity;
import app.inei.appindicadorinei.activities.Indicador_Search_Activity;
import app.inei.appindicadorinei.activities.Search_Activity;
import app.inei.appindicadorinei.adapters.ItemHomeAdapter;
import app.inei.appindicadorinei.modelo.DAO.Data;
import app.inei.appindicadorinei.modelo.pojos.Indicador;


public class Home_Fragment extends Fragment {
    ItemHomeAdapter itemHomeAdapter;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    Context context;


    public Home_Fragment() {
        // Required empty public constructor
    }
    @SuppressLint("ValidFragment")
    public Home_Fragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home_main, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.lista_item_home);
         fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        return rootView;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        itemHomeAdapter = new ItemHomeAdapter(obtenerAllIndicador(),new ItemHomeAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getApplicationContext(),"posiciòn:"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Indicador_Search_Activity.class);
                int id = position;
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemHomeAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Search_Activity.class);
                startActivity(intent);

            }
        });
    }

    public ArrayList<Indicador> obtenerAllIndicador()
    { ArrayList<Indicador> indicadores = new ArrayList<>();
        try {
            Data data = new Data(context);
            data.open();
            indicadores = data.getAllIndicador();
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  indicadores;
    }





}
