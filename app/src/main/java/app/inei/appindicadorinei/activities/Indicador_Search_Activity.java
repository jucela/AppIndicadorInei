package app.inei.appindicadorinei.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.adapters.ItemIndicadorAdapter;
import app.inei.appindicadorinei.modelo.DAO.Data;
import app.inei.appindicadorinei.modelo.pojos.GraficoSubIndicador;
import app.inei.appindicadorinei.modelo.pojos.Indicador;
import app.inei.appindicadorinei.modelo.pojos.SubIndicador;


public class Indicador_Search_Activity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ItemIndicadorAdapter itemIndicadorAdapter;
    RecyclerView recyclerView;

    int id_indicador;
    TextView textview_search;
    ImageView imageview_search;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicador_search);
        Toolbar toolbar = findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        id_indicador = getIntent().getExtras().getInt("id",0);
        textview_search = (TextView) findViewById(R.id.id_textview_search);
        imageview_search = (ImageView) findViewById(R.id.id_imageview_search);
        textview_search.setText(obtenerNombreIndicador(id_indicador));
        imageview_search.setImageResource(obtenerImagenIndicador(id_indicador-1));

        recyclerView = (RecyclerView) findViewById(R.id.lista_item);
        //recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        itemIndicadorAdapter = new ItemIndicadorAdapter(obtenerSubIndicadoresXId(id_indicador),new ItemIndicadorAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {

                if(validaciontabular(position))
                {
                    if(obtenerNroTabular(position).size()==1)
                    {
                        Intent intent = new Intent(getApplicationContext(), Indicador_Data_Tabular_Activity.class);
                        int id = position;
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",id);
                        bundle.putInt("nro_grafico",obtenerNroModeloSubIndicadorXId(id));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(getApplicationContext(), Indicador_Data_Tabular2_Activity.class);
                        int id = position;
                        Bundle bundle = new Bundle();
                        bundle.putInt("id",id);
                        bundle.putInt("nro_grafico",2);
                        bundle.putInt("nro_grafico2",3);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), Indicador_Data_Activity.class);
                    int id = position;
                    intent.putExtra("id",id);
                    startActivity(intent);
                    //Toast.makeText(getApplicationContext(), "NO HAY MODELO", Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemIndicadorAdapter);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_buscar_indicador,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Buscar...");
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                itemIndicadorAdapter.setFilter(obtenerSubIndicadoresXId(id_indicador));
                return true;
            }
        });
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        try {
            ArrayList<SubIndicador> listaFiltrada= filter(obtenerSubIndicadoresXId(id_indicador),s);
            itemIndicadorAdapter.setFilter(listaFiltrada);
        }catch (Exception e)
        {e.printStackTrace();}
        return false;
    }

    private ArrayList<SubIndicador> filter(ArrayList<SubIndicador> notas, String texto){
        ArrayList<SubIndicador> listaFiltrada = new ArrayList<>();
        try {
            texto = texto.toLowerCase();
            for(SubIndicador nota: notas){
                Log.i("Datos",""+nota);
                String nota2= nota.getNombre_subindicador().toLowerCase();
                if(nota2.contains(texto)){
                    listaFiltrada.add(new SubIndicador(nota.getId_subindicador(),nota.getId_indicador(),nota.getNombre_subindicador(),nota.getDescripcion_subindicador(),nota.getFuente(),nota.getTotal_graficos()));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        for (int i=0;i<listaFiltrada.size();i++){
        Log.i("return filtrada",""+listaFiltrada.get(i).getNombre_subindicador());}
        return listaFiltrada;
    }

    public ArrayList<SubIndicador> obtenerSubIndicadoresXId(int id)
    { ArrayList<SubIndicador> subindicadores = new ArrayList<>();
        try {
            Data data = new Data(Indicador_Search_Activity.this);
            data.open();
            subindicadores = data.getSubIndicadorXId(id);
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  subindicadores;

    }

    public int obtenerNroModeloSubIndicadorXId(int id)
    { int modelo=0;
        try {
            Data data = new Data(Indicador_Search_Activity.this);
            data.open();
            GraficoSubIndicador graficosubindicadorObj = data.getNroGraficoSubIndicador(id,12);
            modelo =  graficosubindicadorObj.getNum_grafico();
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  modelo;

    }

    public String obtenerNombreIndicador(int id)
    { String nombreindicador="";
        try {
            Data data = new Data(Indicador_Search_Activity.this);
            data.open();
            Indicador indicadorObj = data.getIndicador(id);
            nombreindicador = indicadorObj.getNombre_indicador();
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  nombreindicador;

    }

    public int obtenerImagenIndicador(int id)
    { int iconoindicador=0;
        ArrayList<Integer> iconos = new ArrayList<>();
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
        iconoindicador = iconos.get(id);
        return  iconoindicador;

    }

    public boolean validaciontabular(int id)
    {   ArrayList<GraficoSubIndicador> modelosGraficos = new ArrayList<>();
        boolean respuesta = false;
        ArrayList<Integer> modeloGrafico = new ArrayList<>();
        try {
            Data data = new Data(Indicador_Search_Activity.this);
            data.open();
            modelosGraficos = data.getGraficoSubIndicadorXId(id);

            for (int i=0;i<modelosGraficos.size();i++){

                int valor = modelosGraficos.get(i).getModelo_grafico();
                modeloGrafico.add(valor);

            }
            data.close();
            if (modeloGrafico.size() > 0) {
                boolean estado = modeloGrafico.contains(12);
                if (estado)
                { respuesta=true;}
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  respuesta;

    }

    public ArrayList<Integer> obtenerNroTabular(int id)
    {   ArrayList<GraficoSubIndicador> modelosGraficos = new ArrayList<>();
        ArrayList<Integer> modeloGrafico = new ArrayList<>();
        try {
            Data data = new Data(Indicador_Search_Activity.this);
            data.open();
            modelosGraficos = data.getGraficoSubIndicadorXId(id);

            for (int i=0;i<modelosGraficos.size();i++) {
                if (modelosGraficos.get(i).getModelo_grafico() == 12) {
                    int valor = modelosGraficos.get(i).getModelo_grafico();
                    modeloGrafico.add(valor);
                }
                data.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  modeloGrafico;

    }




}
