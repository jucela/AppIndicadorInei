package app.inei.appindicadorinei.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.IOException;
import java.util.ArrayList;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.modelo.DAO.Data;
import app.inei.appindicadorinei.modelo.pojos.DataIndicador;
import app.inei.appindicadorinei.modelo.pojos.LeyendaSubIndicador;
import app.inei.appindicadorinei.modelo.pojos.SubIndicador;

public class Indicador_Data_M4_2_Activity extends AppCompatActivity {
    int id;
    int id_indicador;
    TextView txt_titulo;
    TextView txt_descripcion;
    private LineChart chart;
    private HorizontalBarChart chart2;
    ArrayList<Float> datos1;
    ArrayList<Float> datos2;
    ArrayList<Float> datos3;
    ArrayList<Float> datos4;
    ArrayList<String> EjeX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_m4_2);
        Toolbar toolbar = findViewById(R.id.toolbarData);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            //onBackPressed();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic3_wc);
        }

        id = getIntent().getExtras().getInt("id");
        id_indicador = getIntent().getExtras().getInt("id_indicador");
        txt_titulo      = (TextView) findViewById(R.id.cardview1_data_title1);
        txt_descripcion = (TextView) findViewById(R.id.cardview1_data_descrip1);
        txt_titulo.setText(obtenerNombreSubIndicador(id).get(0));
        txt_descripcion.setText(obtenerNombreSubIndicador(id).get(1));

        /*INICIO GRAFICO 1*/
        datos1 = new ArrayList<>();
        datos2 = new ArrayList<>();
        datos3 = new ArrayList<>();
        datos4 = new ArrayList<>();
        EjeX = new ArrayList<>();

        chart = findViewById(R.id.chart_m4_2_1);

        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(false);
        xAxis.setLabelCount(6);
        //xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1)));
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false); // this replaces setStartAtZero(true)



        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        //seekBarX.setProgress(20);
        //seekBarY.setProgress(100);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);


        cargarData();
        chart.animateX(2000);

        /**/
        /*INICIO GRAFICO 2*/
        //dato = new ArrayList<>();
        EjeX = new ArrayList<String>();


        chart2 = findViewById(R.id.chart_m4_2_2);
        chart2.setDrawBarShadow(false);
        chart2.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart2, no values will be
        // drawn
        chart2.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        chart2.setPinchZoom(false);
        chart2.setScaleYEnabled(false);

        // draw shadows for each bar that show the maximum value
        // chart2.setDrawBarShadow(true);

        chart2.setDrawGridBackground(false);

        XAxis xAxis2 = chart2.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(9f);
        xAxis.setAxisMinimum(0f);
//        xAxis.setAxisMaximum(filtrarEjeX(obtenerDataSubIndicador(id)).size());
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setLabelCount(filtrarEjeX(obtenerDataSubIndicador(id)).size());
//        xAxis.setGranularity(1f);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(filtrarEjeX(obtenerDataSubIndicador(id))));
        YAxis yl = chart2.getAxisLeft();
        //yl.setTypeface(tfLight);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setEnabled(false);
        yl.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);

        YAxis yr = chart2.getAxisRight();
        //yr.setTypeface(tfLight);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(true);
        yr.setEnabled(true);
        yr.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//        yr.setInverted(true);

        //chart2.setFitBars(true);
        chart2.animateY(2500);

        // setting data
        //seekBarY.setProgress(50);
        //seekBarX.setProgress(12);

        Legend l2 = chart2.getLegend();
        l2.setEnabled(false);
        l2.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l2.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l2.setDrawInside(false);
        l2.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l2.setFormSize(8f);
        l2.setXEntrySpace(400f);
        l2.setYEntrySpace(400f);

        cargarData2();

        /*FIN GRAFICO 2*/



        Log.i("Indicador-Subindicador:",""+id_indicador+"-"+id);
        Log.i("Metodo:",""+obtenerLeyendaSubIndicadoresXId(id,1));
    }

    private final int[] colors = new int[] {
            ColorTemplate.MATERIAL_COLORS[0],
            ColorTemplate.MATERIAL_COLORS[1],
            ColorTemplate.MATERIAL_COLORS[2],
            ColorTemplate.MATERIAL_COLORS[3]
            //ColorTemplate.MATERIAL_COLORS[4]
    };






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

        }else
        if (id == R.id.id_item_mostrar_data) {
            for (IDataSet set : chart.getData().getDataSets()) {
                if (set instanceof BarDataSet)
                    set.setDrawValues(!set.isDrawValuesEnabled());
            }

            chart.invalidate();
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> obtenerNombreSubIndicador(int id)
    {   ArrayList<String> datos = new ArrayList<>();
        String nombresubindicador="";
        String descripcionsubindicador="";
        try {
            Data data = new Data(Indicador_Data_M4_2_Activity.this);
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
            Data data = new Data(Indicador_Data_M4_2_Activity.this);
            data.open();
            datossubindicador = data.getDataSubIndicadorXId(id_subindicador,nro_subindicador,nro_grafico);
            //Log.i("Datos:",""+datossubindicador.get(1).getAnio());
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  datossubindicador;

    }

//    public ArrayList<String> obtenerDataEjeXSubIndicadoresXId(int id_subindicador,int nro_subindicador )
////    {   ArrayList<DataIndicador> datossubindicador = new ArrayList<>();
////        EjeX = new ArrayList<>();
////        String ejex="";
////        try {
////            Data data = new Data(Indicador_Data_M4_2_Activity.this);
////            data.open();
////            datossubindicador = data.getDataSubIndicadorXId(id_subindicador,nro_subindicador,1);
////
////            for (int i=0;i<datossubindicador.size();i++)
////            {
////                ejex = datossubindicador.get(i).ejex;
////                EjeX.add(ejex);
////            }
////            //Log.i("Datos:",""+EjeX);
////            data.close();
////        }
////        catch (IOException e){
////            e.printStackTrace();
////        }
////        return  EjeX;
////
////    }
    public ArrayList<String> obtenerDataEjeXSubIndicadoresXId(int id_subindicador,int nro_subindicador,int nro_grafico )
    {   ArrayList<DataIndicador> datossubindicador = new ArrayList<>();
        EjeX = new ArrayList<>();
        String ejex="";
        try {
        Data data = new Data(Indicador_Data_M4_2_Activity.this);
        data.open();
        datossubindicador = data.getDataSubIndicadorXId(id_subindicador,nro_subindicador,nro_grafico);

        for (int i=0;i<datossubindicador.size();i++)
        {
            ejex = datossubindicador.get(i).getEjey();
            EjeX.add(ejex);
        }
        //Log.i("Datos:",""+EjeX);
        data.close();
    }
    catch (IOException e){
        e.printStackTrace();
    }
    return  EjeX;

}
    public float obtenerMayorValor(int id_subindicador,int nro_subindicador,int nro_grafico)
    {  float mayor;
        if(obtenerDataSubIndicadoresXId(id_subindicador,nro_subindicador,nro_grafico).size()>0)
        { mayor = obtenerDataSubIndicadoresXId(id_subindicador,nro_subindicador,nro_grafico).get(0).getDato();
            for(DataIndicador objeto: obtenerDataSubIndicadoresXId(id_subindicador,nro_subindicador,nro_grafico))
            {float numero = objeto.getDato();
                if (numero>mayor)
                {mayor =numero;}
            }
        }
        else{mayor=0;}

        return mayor;
    }

    public float obtenerMenorValor(int id_subindicador,int nro_subindicador,int nro_grafico)
    {   float menor;
        if(obtenerDataSubIndicadoresXId(id_subindicador,nro_subindicador,nro_grafico).size()>0)
        { menor = obtenerDataSubIndicadoresXId(id_subindicador,nro_subindicador,nro_grafico).get(0).getDato();
            for(DataIndicador objeto: obtenerDataSubIndicadoresXId(id_subindicador,nro_subindicador,nro_grafico))
            {float numero = objeto.getDato();
                if (numero<menor)
                {menor =numero;}
            }
        }
        else{menor=0;}

        return menor;
    }

    public String obtenerLeyendaSubIndicadoresXId(int id_subindicador, int nro_subindicador )
    {   String leyenda ="";
        try {
            Data data = new Data(Indicador_Data_M4_2_Activity.this);
            data.open();
            LeyendaSubIndicador leyendasubindicador = data.getLeyendaSubIndicador(id_subindicador,nro_subindicador);
            leyenda = leyendasubindicador.getNombre_nro_subindicador();
            if(leyenda!=null)
            Log.i("Datos:",""+leyenda);
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (NullPointerException e){
            e.getCause();
        }
        return  leyenda;

    }

    //Metodos Graficos 1
    public void cargarData(){
        chart.resetTracking();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        String titulo="";
        int subindicadorsize1=obtenerDataSubIndicadoresXId(id,1,1).size();
        int subindicadorsize2=obtenerDataSubIndicadoresXId(id,2,1).size();
        int subindicadorsize3=obtenerDataSubIndicadoresXId(id,3,1).size();
        int subindicadorsize4=obtenerDataSubIndicadoresXId(id,4,1).size();

        for (int z = 0; z < 4; z++) {

            ArrayList<Entry> values = new ArrayList<>();
            if(z==0){
                for (int i = 0; i < subindicadorsize1; i++) {

                    float val = obtenerDataSubIndicadoresXId(id,1,1).get(i).getDato();
                    values.add(new Entry(i, (float) val));
                }

                titulo = obtenerLeyendaSubIndicadoresXId(id,1);


            }
            if(z==1){
                for (int i = 0; i < subindicadorsize2; i++) {

                    float val = obtenerDataSubIndicadoresXId(id,2,1).get(i).getDato();
                    values.add(new Entry(i, (float) val));
                }
                titulo = obtenerLeyendaSubIndicadoresXId(id,2);

            }
            if(z==2){
                for (int i = 0; i < subindicadorsize3; i++) {

                    float val = obtenerDataSubIndicadoresXId(id,3,1).get(i).getDato();
                    values.add(new Entry(i, (float) val));
                }
                titulo = obtenerLeyendaSubIndicadoresXId(id,3);

            }
            if(z==3){
                for (int i = 0; i < subindicadorsize4; i++) {

                    float val = obtenerDataSubIndicadoresXId(id,4,1).get(i).getDato();
                    values.add(new Entry(i, (float) val));
                }
                titulo = obtenerLeyendaSubIndicadoresXId(id,4);
            }



            LineDataSet d = new LineDataSet(values, titulo);
            d.setLineWidth(2.5f);
            d.setCircleRadius(4f);
            d.setDrawCircleHole(false);


            int color = colors[z % colors.length];
            d.setColor(color);
            d.setCircleColor(color);
            dataSets.add(d);
        }


        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
    }

    //metodos Grafico 2
    private void setData() {

//        float barWidth = 8.5f;
//        float spaceForBar = 10f;
        float barWidth = 0.75f;
        float spaceForBar = 0.5f;
        ArrayList<BarEntry> values = new ArrayList<>();

        int subindicadorsize=obtenerDataSubIndicadoresXId(id,1,2).size();

        for (int i = 0; i < subindicadorsize; i++) {
            float val = obtenerDataSubIndicadoresXId(id,1,2).get(i).getDato();
            values.add(new BarEntry(i+spaceForBar, val));
        }

        BarDataSet set1;
        set1 = new BarDataSet(values, "Leyenda");
        set1.setColor(Color.rgb(5, 168, 228));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        //data.setValueTypeface(tfLight);
        data.setBarWidth(barWidth);
        chart2.setData(data);
        //}
    }
    public void cargarData2(){
        setData();
        chart2.setFitBars(true);
        chart2.invalidate();
    }
}
