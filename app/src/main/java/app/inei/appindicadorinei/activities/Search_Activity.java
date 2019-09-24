package app.inei.appindicadorinei.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

//@SuppressWarnings("ALL")
@SuppressWarnings("ALL")
public class Search_Activity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ArrayList<SubIndicador> subindicadores;
    ItemIndicadorAdapter itemIndicadorAdapter;
    RecyclerView recyclerView;
    TextView texto;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = (RecyclerView) findViewById(R.id.lista_item);
        //recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        itemIndicadorAdapter = new ItemIndicadorAdapter(obtenerAllSubIndicadores(),new ItemIndicadorAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                if(obtenerTotalGraficoSubIndicadorXId(position)==1)
                {   if(obtenerModeloSubIndicadorXId(position)==1){
                    Intent intent = new Intent(getApplicationContext(), Indicador_Data_M1_Activity.class);
                    int id = position;
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else
                if(obtenerModeloSubIndicadorXId(position)==2){
                    Intent intent = new Intent(getApplicationContext(), Indicador_Data_M2_Activity.class);
                    int id = position;
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else
                if(obtenerModeloSubIndicadorXId(position)==3){
                    Intent intent = new Intent(getApplicationContext(), Indicador_Data_M3_Activity.class);
                    int id = position;
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else
                if(obtenerModeloSubIndicadorXId(position)==5){
                    Intent intent = new Intent(getApplicationContext(), Indicador_Data_M5_Activity.class);
                    int id = position;
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else
                if(obtenerModeloSubIndicadorXId(position)==6){
                    Intent intent = new Intent(getApplicationContext(), Indicador_Data_M6_Activity.class);
                    int id = position;
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(),"MODELO DE GRAFICO NO CREADO",Toast.LENGTH_LONG).show();
                }
                else
                {  if(obtenerNroGraficoSubIndicadoresXId(position).size()==2)
                {  if(obtenerModeloGraficoSubIndicadoresXId(position).get(0)==3 && obtenerModeloGraficoSubIndicadoresXId(position).get(1)==9 ){
                    Intent intent = new Intent(getApplicationContext(), Indicador_Data_M3_M9_Activity.class);
                    int id = position;
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else
                if(obtenerModeloGraficoSubIndicadoresXId(position).get(0)==1 && obtenerModeloGraficoSubIndicadoresXId(position).get(1)==1 ){
                    Intent intent = new Intent(getApplicationContext(), Indicador_Data_M1_M1_Activity.class);
                    int id = position;
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else
                if(obtenerModeloGraficoSubIndicadoresXId(position).get(0)==2 && obtenerModeloGraficoSubIndicadoresXId(position).get(1)==4 ){
                    Intent intent = new Intent(getApplicationContext(), Indicador_Data_M2_M4_Activity.class);
                    int id = position;
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else
                if(obtenerModeloGraficoSubIndicadoresXId(position).get(0)==5 && obtenerModeloGraficoSubIndicadoresXId(position).get(1)==2 ){
                    Intent intent = new Intent(getApplicationContext(), Indicador_Data_M5_M2_Activity.class);
                    int id = position;
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else
                if(obtenerModeloGraficoSubIndicadoresXId(position).get(0)==6 && obtenerModeloGraficoSubIndicadoresXId(position).get(1)==2 ){
                    Intent intent = new Intent(getApplicationContext(), Indicador_Data_M6_M2_Activity.class);
                    int id = position;
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else
                if(obtenerModeloGraficoSubIndicadoresXId(position).get(0)==3 && obtenerModeloGraficoSubIndicadoresXId(position).get(1)==2 ){
                    Intent intent = new Intent(getApplicationContext(), Indicador_Data_M3_M2_Activity.class);
                    int id = position;
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else
                if(obtenerModeloGraficoSubIndicadoresXId(position).get(0)==7 && obtenerModeloGraficoSubIndicadoresXId(position).get(1)==2 ){
                    Intent intent = new Intent(getApplicationContext(), Indicador_Data_M7_M2_Activity.class);
                    int id = position;
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else {Toast.makeText(getApplicationContext(),"MODELO DE GRAFICO DOBLE NO CREADO",Toast.LENGTH_SHORT).show();}

                }
                else {Toast.makeText(getApplicationContext(),"NO SE ENCUENTRA MODELO",Toast.LENGTH_SHORT).show();}





                }


                //Toast.makeText(getApplicationContext(),"posilciòn:"+position,Toast.LENGTH_SHORT).show();

            }
        });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemIndicadorAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_buscar_indicador,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        //searchView.setQueryHint(Html.fromHtml("<font color =#ffffff>" + "Buscar..."+ "</font>"));
        searchView.setQueryHint("Buscar...");
        searchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                itemIndicadorAdapter.setFilter(obtenerAllSubIndicadores());
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
            ArrayList<SubIndicador> listaFiltrada= filter(obtenerAllSubIndicadores(),s);
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
        Log.i("return filtrada",""+listaFiltrada.get(i));}
        return listaFiltrada;
    }

    public ArrayList<SubIndicador> obtenerAllSubIndicadores()
    { ArrayList<SubIndicador> subindicadores = new ArrayList<>();
        try {
            Data data = new Data(Search_Activity.this);
            data.open();
            subindicadores = data.getAllSubIndicador();
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  subindicadores;

    }

    public ArrayList<SubIndicador> obtenerSubIndicadoresXId(int id)
    { ArrayList<SubIndicador> subindicadores = new ArrayList<>();
        try {
            Data data = new Data(Search_Activity.this);
            data.open();
            subindicadores = data.getSubIndicadorXId(id);
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  subindicadores;

    }

    public ArrayList<Integer> obtenerNroGraficoSubIndicadoresXId(int id)
    {   ArrayList<GraficoSubIndicador> nrosGraficos = new ArrayList<>();
        ArrayList<Integer> num_grafico = new ArrayList<>();
        try {
            Data data = new Data(Search_Activity.this);
            data.open();
            nrosGraficos = data.getGraficoSubIndicadorXId(id);

            for (int i=0;i<nrosGraficos.size();i++){

                int valor = nrosGraficos.get(i).getNum_grafico();
                num_grafico.add(valor);

            }
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  num_grafico;

    }

    public ArrayList<Integer> obtenerModeloGraficoSubIndicadoresXId(int id)
    {   ArrayList<GraficoSubIndicador> modelosGraficos = new ArrayList<>();
        ArrayList<Integer> modeloGrafico = new ArrayList<>();
        try {
            Data data = new Data(Search_Activity.this);
            data.open();
            modelosGraficos = data.getGraficoSubIndicadorXId(id);

            for (int i=0;i<modelosGraficos.size();i++){

                int valor = modelosGraficos.get(i).getModelo_grafico();
                modeloGrafico.add(valor);

            }
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  modeloGrafico;

    }

    public int obtenerModeloSubIndicadorXId(int id)
    { int modelo=0;
        try {
            Data data = new Data(Search_Activity.this);
            data.open();
            GraficoSubIndicador graficosubindicadorObj = data.getGraficoSubIndicador(id);
            modelo =  graficosubindicadorObj.getModelo_grafico();
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  modelo;

    }

//    public int obtenerTotalGraficoSubIndicadorXId(int id)
//    { int total=0;
//        try {
//            Data data = new Data(Indicador_Search_Activity.this);
//            data.open();
//            SubIndicador subindicadorObj = data.getSubIndicador(id);
//            total =  subindicadorObj.getTotal_graficos();
//            data.close();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//        return  total;
//    }

    public int obtenerTotalGraficoSubIndicadorXId(int id)
    {   ArrayList<GraficoSubIndicador> graficosubindicador = new ArrayList<>();
        int total=0;
        try {
            Data data = new Data(Search_Activity.this);
            data.open();
            graficosubindicador = data.getGraficoSubIndicadorXId(id);
            total = graficosubindicador.size();
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        return  total;
    }



    public String obtenerNombreIndicador(int id)
    { String nombreindicador="";
        try {
            Data data = new Data(Search_Activity.this);
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
}
