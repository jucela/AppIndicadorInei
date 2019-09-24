package app.inei.appindicadorinei.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.io.IOException;
import java.util.ArrayList;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.modelo.DAO.Data;
import app.inei.appindicadorinei.modelo.pojos.DataIndicador;
import app.inei.appindicadorinei.modelo.pojos.LeyendaSubIndicador;
import app.inei.appindicadorinei.modelo.pojos.SubIndicador;

public class Indicador_Data_M1_M1_Activity extends AppCompatActivity {
    int id;
    TextView txt_titulo;
    TextView txt_descripcion;
    private CombinedChart chart;
    private CombinedChart chart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_m1_m1);
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


        /*INICIO GRAFICO 1 BARRAS Y LINEA*/

        chart = findViewById(R.id.chart_m1_m1_1);
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });

        Legend l = chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(true);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(obtenerMenorValor(id,2,1)-1); // this replaces setStartAtZero(true)

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelCount(6);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,1)));//false: formato predeterminado con setValueFormatter

        CombinedData data = new CombinedData();

        data.setData(generateLineData());
        data.setData(generateBarData());

        xAxis.setAxisMaximum(data.getXMax() + 0.5f);

        chart.setData(data);
        chart.invalidate();
        chart.animateXY(2000, 2000);
        /*FIN GRAFICO 1 BARRAS Y LINEA*/

        /*INICIO GRAFICO 2 BARRAS Y LINEA*/

        chart2 = findViewById(R.id.chart_m1_m1_2);
        chart2.getDescription().setEnabled(false);
        chart2.setBackgroundColor(Color.WHITE);
        chart2.setDrawGridBackground(false);
        chart2.setDrawBarShadow(false);
        chart2.setHighlightFullBarEnabled(false);
        chart2.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });

        Legend l2 = chart2.getLegend();
        l2.setWordWrapEnabled(true);
        l2.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l2.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l2.setDrawInside(false);

        YAxis rightAxis2 = chart2.getAxisRight();
        rightAxis2.setEnabled(true);
        rightAxis2.setDrawGridLines(false);
        rightAxis2.setAxisMinimum(obtenerMenorValor(id,2,2)-1); // this replaces setStartAtZero(true)

        YAxis leftAxis2 = chart2.getAxisLeft();
        leftAxis2.setDrawGridLines(false);
        leftAxis2.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis2 = chart2.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setAxisMinimum(0f);
        xAxis2.setGranularity(1f);
        xAxis2.setCenterAxisLabels(true);
        xAxis2.setLabelCount(6);
        xAxis2.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,2)));//false: formato predeterminado con setValueFormatter

        CombinedData data2 = new CombinedData();

        data2.setData(generateLineData2());
        data2.setData(generateBarData2());

        xAxis2.setAxisMaximum(data.getXMax() + 0.5f);

        chart2.setData(data2);
        chart2.invalidate();
        chart2.animateXY(2000, 2000);
        /*FIN GRAFICO 2 BARRAS Y LINEA*/
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
            Data data = new Data(Indicador_Data_M1_M1_Activity.this);
            data.open();
            SubIndicador subindicadorObj = data.getSubIndicador(id);
            nombresubindicador = subindicadorObj.getNombre_subindicador();
            descripcionsubindicador = subindicadorObj.getDescripcion_subindicador();
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
            Data data = new Data(Indicador_Data_M1_M1_Activity.this);
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
            Data data = new Data(Indicador_Data_M1_M1_Activity.this);
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
            Data data = new Data(Indicador_Data_M1_M1_Activity.this);
            data.open();
            LeyendaSubIndicador leyendasubindicador = data.getLeyendaSubIndicador(id_subindicador,nro_subindicador);
            leyenda = leyendasubindicador.getNombre_nro_subindicador();
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

    //METODOS GENERAR DATA 1

    private BarData generateBarData() {

        ArrayList<BarEntry> entries1 = new ArrayList<>();

        int subindicadorsize=obtenerDataSubIndicadoresXId(id,1,1).size();

        for (int i = 0; i < subindicadorsize; i++) {
            entries1.add(new BarEntry(i+0.25f, obtenerDataSubIndicadoresXId(id,1,1).get(i).getDato()));
        }

        BarDataSet set1 = new BarDataSet(entries1, obtenerLeyendaSubIndicadoresXId(id,1));
        set1.setColor(Color.rgb(61, 165, 255));
        set1.setValueTextColor(Color.rgb(0, 0, 0));

        if(obtenerDataEjeXSubIndicadoresXId(id,1,1).size()<15)
        {set1.setValueTextSize(10f);}

        else{
            set1.setValueTextSize(6.5f);
        }

        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = 0.45f; // x2 dataset
        BarData d = new BarData(set1);
        d.setBarWidth(barWidth);
        return d;
    }

    private LineData generateLineData() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < obtenerDataSubIndicadoresXId(id,2,1).size(); i++)
        {    int subindicadorsize=obtenerDataSubIndicadoresXId(id,2,1).size();
            if(i<subindicadorsize)
            {
                entries.add(new Entry(i + 0.75f, obtenerDataSubIndicadoresXId(id,2,1).get(i).getDato()));
            }

        }

        LineDataSet set = new LineDataSet(entries, obtenerLeyendaSubIndicadoresXId(id,2));
        set.setColor(Color.rgb(255 , 102, 102));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(255 , 102, 102));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(255 , 102, 102));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(255 , 102, 102));

        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        d.addDataSet(set);

        return d;
    }

    //METODOS GENERAR DATA 2

    private BarData generateBarData2() {

        ArrayList<BarEntry> entries1 = new ArrayList<>();

        int subindicadorsize=obtenerDataSubIndicadoresXId(id,1,2).size();

        for (int i = 0; i < subindicadorsize; i++) {
            entries1.add(new BarEntry(i+0.25f, obtenerDataSubIndicadoresXId(id,1,2).get(i).getDato()));
        }

        BarDataSet set1 = new BarDataSet(entries1, obtenerLeyendaSubIndicadoresXId(id,1));
        set1.setColor(Color.rgb(61, 165, 255));
        set1.setValueTextColor(Color.rgb(0, 0, 0));

        if(obtenerDataEjeXSubIndicadoresXId(id,1,2).size()<15)
        {set1.setValueTextSize(10f);}

        else{
            set1.setValueTextSize(6.5f);
        }

        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = 0.45f; // x2 dataset
        BarData d = new BarData(set1);
        d.setBarWidth(barWidth);
        return d;
    }

    private LineData generateLineData2() {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < obtenerDataSubIndicadoresXId(id,2,2).size(); i++)
        {    int subindicadorsize=obtenerDataSubIndicadoresXId(id,2,2).size();
            if(i<subindicadorsize)
            {
                entries.add(new Entry(i + 0.75f, obtenerDataSubIndicadoresXId(id,2,2).get(i).getDato()));
            }

        }

        LineDataSet set = new LineDataSet(entries, obtenerLeyendaSubIndicadoresXId(id,2));
        set.setColor(Color.rgb(255 , 102, 102));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(255 , 102, 102));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(255 , 102, 102));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(255 , 102, 102));

        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        d.addDataSet(set);

        return d;
    }

}
