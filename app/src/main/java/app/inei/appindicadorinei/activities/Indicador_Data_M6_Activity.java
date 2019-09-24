package app.inei.appindicadorinei.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.modelo.DAO.Data;
import app.inei.appindicadorinei.modelo.pojos.DataIndicador;
import app.inei.appindicadorinei.modelo.pojos.LeyendaSubIndicador;
import app.inei.appindicadorinei.modelo.pojos.SubIndicador;

public class Indicador_Data_M6_Activity extends AppCompatActivity {
    int id;
    TextView txt_titulo;
    TextView txt_descripcion;
    private BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_m6);
        Toolbar toolbar = findViewById(R.id.toolbarData);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        id = getIntent().getExtras().getInt("id");
        txt_titulo      = (TextView) findViewById(R.id.cardview1_data_title1);
        txt_descripcion = (TextView) findViewById(R.id.cardview1_data_descrip1);
        txt_titulo.setText(obtenerNombreSubIndicador(id).get(0));
        txt_descripcion.setText(obtenerNombreSubIndicador(id).get(1));

        /*INICIO GRAFICO BARRAS VERTICALES MULTIPLES*/

        chart = findViewById(R.id.chart_m6);
        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,1)));
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart.getAxisRight().setEnabled(false);
        chart.animateY(2500);
        generateMultiBarraData();
        /*FIN GRAFICO BARRAS VERTICALES MULTIPLES*/


        Log.i("Mensaje",""+id);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_datos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.id_item_information2) {
            onBackPressed();
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("Si desea sincronizar la información de este indicador, presione el boton SINCRONIZAR en el Menú Principal")
//                    //.setView(R.drawable.ic_3d_rotation)
//                    .setIcon(R.drawable.ic_info_outline_black_24dp)
//                    .setTitle("Información")
//                    .setCancelable(false)
//                    .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
//            AlertDialog alert = builder.create();
//            alert.show();
        }else
        if (id == R.id.id_item_descarga_imagen) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (ActivityCompat.checkSelfPermission(Indicador_Activity_Service.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        == PackageManager.PERMISSION_GRANTED){
//                    //dibujarCanvas(titulo);
//                    if(estado_anual.isChecked())
//                    {descargarGrafico(1,titulo);}
//                    else{
//                        if(estado_mensual.isChecked())
//                        {descargarGrafico(2,titulo);}
//                        else {
//                            Toast.makeText(Indicador_Activity_Service.this,"Indicador con Imagen NO disponible",Toast.LENGTH_LONG).show();}
//                    }
//                }
//                else
//                { ActivityCompat.requestPermissions(Indicador_Activity_Service.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);}
//            }
//            else
//            {
//                //dibujarCanvas(titulo);
//                if(estado_anual.isChecked())
//                {descargarGrafico(1,titulo);}
//                else{
//                    if(estado_mensual.isChecked())
//                    {descargarGrafico(2,titulo);}
//                    else {Toast.makeText(Indicador_Activity_Service.this,"Indicador con Imagen NO disponible",Toast.LENGTH_LONG).show();}
//                }
//            }

        }else
        if (id == R.id.id_item_descarga_excel) {
//            sqliteToExcel = new SQLiteToExcel(getApplicationContext(), SQLConstantes.DB_NAME, directory_path);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (ActivityCompat.checkSelfPermission(Indicador_Activity_Service.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        == PackageManager.PERMISSION_GRANTED){
//                    String tipo="1";
//                    descargarExcel(id1,titulo,"1");
////                    sqliteToExcel.exportAllTable(""+nombre_indicador1+".xls",id1,nombre_indicador1, new SQLiteToExcel.ExportListener() {
////                        @Override
////                        public void onStart() {
////
////                        }
////
////                        @Override
////                        public void onCompleted(String filePath) {
////                            Toast.makeText(Indicador_Activity_Service.this,"Excel Descargado Exitosamente",Toast.LENGTH_SHORT).show();
////                            //Utils.showSnackBar(view, "Successfully Exported");
////                        }
////
////                        @Override
////                        public void onError(Exception e) {
////                            Toast.makeText(Indicador_Activity_Service.this,"Error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
////                            //Utils.showSnackBar(view, e.getMessage());
////                        }
////                    });
//                }
//                else
//                { ActivityCompat.requestPermissions(Indicador_Activity_Service.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);}
//            }
//            else
//            {     descargarExcel(id1,titulo,"1");
////                sqliteToExcel.exportAllTable(""+nombre_indicador1+".xls",id1,nombre_indicador1, new SQLiteToExcel.ExportListener() {
////                @Override
////                public void onStart() {
////
////                }
////
////                @Override
////                public void onCompleted(String filePath) {
////                    Toast.makeText(Indicador_Activity_Service.this,"Excel Descargado Exitosamente",Toast.LENGTH_SHORT).show();
////                    //Utils.showSnackBar(view, "Successfully Exported");
////                }
////
////                @Override
////                public void onError(Exception e) {
////                    Toast.makeText(Indicador_Activity_Service.this,"Error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
////                    //Utils.showSnackBar(view, e.getMessage());
////                }
////            });
//            }

        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> obtenerNombreSubIndicador(int id)
    {   ArrayList<String> datos = new ArrayList<>();
        String nombresubindicador="";
        String descripcionsubindicador="";
        try {
            Data data = new Data(Indicador_Data_M6_Activity.this);
            data.open();
            SubIndicador subindicadorObj = data.getSubIndicador(id);
            nombresubindicador = subindicadorObj.getNombre_subindicador();
            descripcionsubindicador = subindicadorObj.getDescripcion_subindicador();
            Log.i("Valorx:",""+subindicadorObj.getNombre_subindicador());
            datos.add(nombresubindicador);
            datos.add(descripcionsubindicador);
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return datos;

    }

    public ArrayList<DataIndicador> obtenerDataSubIndicadoresXId(int id_subindicador,int nro_subindicador,int nro_grafico )
    {   ArrayList<DataIndicador> datossubindicador = new ArrayList<>();
        try {
            Data data = new Data(Indicador_Data_M6_Activity.this);
            data.open();
            datossubindicador = data.getDataSubIndicadorXId(id_subindicador,nro_subindicador,nro_grafico);
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  datossubindicador;

    }

    public ArrayList<String> obtenerDataEjeXSubIndicadoresXId(int id_subindicador,int nro_subindicador,int nro_grafico)
    {   ArrayList<DataIndicador> datossubindicador = new ArrayList<>();
        ArrayList<String> EjeX = new ArrayList<>();
        String ejex="";
        try {
            Data data = new Data(Indicador_Data_M6_Activity.this);
            data.open();
            datossubindicador = data.getDataSubIndicadorXId(id_subindicador,nro_subindicador,nro_grafico);

            for (int i=0;i<datossubindicador.size();i++)
            {
                ejex = datossubindicador.get(i).getEjex();
                EjeX.add(ejex);
            }
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  EjeX;

    }

    public String obtenerLeyendaSubIndicadoresXId(int id_subindicador, int nro_subindicador )
    {   String leyenda ="";
        try {
            Data data = new Data(Indicador_Data_M6_Activity.this);
            data.open();
            LeyendaSubIndicador leyendasubindicador = data.getLeyendaSubIndicador(id_subindicador,nro_subindicador);
            leyenda = leyendasubindicador.getNombre_nro_subindicador();
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  leyenda;

    }



    //METODO GENERAR DATA
    public void generateMultiBarraData(){
        float groupSpace = 0.1f;
        float barSpace = 0.05f; // x4 DataSet
        float barWidth = 0.25f; // x4 DataSet
        int startYear = 0;
        int endYear = obtenerDataSubIndicadoresXId(id,1,1).size();


        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();


        for (int i = 0; i < obtenerDataSubIndicadoresXId(id,1,1).size(); i++) {

            values1.add(new BarEntry(i,obtenerDataSubIndicadoresXId(id,1,1).get(i).getDato()));
            values2.add(new BarEntry(i,obtenerDataSubIndicadoresXId(id,2,1).get(i).getDato()));
            if(obtenerDataSubIndicadoresXId(id,3,1).size()>0) {
                values3.add(new BarEntry(i, obtenerDataSubIndicadoresXId(id, 3,1).get(i).getDato()));
            }
        }

        BarDataSet set1, set2, set3;
        set1 = new BarDataSet(values1, obtenerLeyendaSubIndicadoresXId(id,1));
        set1.setColor(Color.rgb(46, 204, 113));
        set2 = new BarDataSet(values2, obtenerLeyendaSubIndicadoresXId(id,2));
        set2.setColor(Color.rgb(241, 196, 15));
        if(obtenerDataSubIndicadoresXId(id,3,1).size()>0) {
        set3 = new BarDataSet(values3, obtenerLeyendaSubIndicadoresXId(id,3));
        set3.setColor(Color.rgb(231, 76, 60));}
        else{
            set3 = new BarDataSet(values3, "");
        }
        BarData data = new BarData(set1, set2, set3);
        data.setValueFormatter(new LargeValueFormatter());
        chart.setData(data);
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(startYear);
        chart.getXAxis().setAxisMaximum(startYear +chart.getBarData().getGroupWidth(groupSpace, barSpace) * endYear);
        chart.groupBars(startYear, groupSpace, barSpace);
        chart.invalidate();
    }




}
