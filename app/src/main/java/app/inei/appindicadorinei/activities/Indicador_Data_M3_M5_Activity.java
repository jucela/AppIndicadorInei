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

public class Indicador_Data_M3_M5_Activity extends AppCompatActivity {
    int id;
    TextView txt_titulo;
    TextView txt_descripcion;
    private BarChart chart;
    private LineChart chart2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_m3_m5);
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

        /*INICIO GRAFICO BARRAS VERTICALES*/

        chart = findViewById(R.id.chart_m3_m5_1);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(60);
        chart.setPinchZoom(false);
        chart.setScaleYEnabled(false);
        chart.setDrawGridBackground(false);XAxis xAxis = chart.getXAxis();

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);

        if(obtenerDataEjeXSubIndicadoresXId(id,1,1).size()<13)
        {   xAxis.setLabelCount(6);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,1)));
            xAxis.setLabelRotationAngle(0f);
        }

        else{xAxis.setLabelCount(6);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,1)));
            xAxis.setLabelRotationAngle(300f);}


        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        Legend l = chart.getLegend();
        l.setEnabled(false);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        generateBarVerticalData();
        chart.animateXY(2000, 2000);
        /*INICIO GRAFICO LINEAS MULTIPLES*/

        chart2 = findViewById(R.id.chart_m3_m5_2);
        chart2.setDrawGridBackground(false);
        chart2.getDescription().setEnabled(false);
        chart2.setDrawBorders(false);
        chart2.setTouchEnabled(true);
        chart2.setDragEnabled(true);
        chart2.setScaleEnabled(true);
        chart2.setPinchZoom(false);
        chart2.animateX(2000);

        XAxis xAxis2 = chart2.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setAxisMinimum(0f);
        xAxis2.setGranularity(1f);
        xAxis2.setCenterAxisLabels(false);
        xAxis2.setLabelCount(6);
        xAxis2.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,2)));
        xAxis2.setDrawGridLines(false);

        YAxis leftAxis2 = chart2.getAxisLeft();
        leftAxis2.setEnabled(true);
        leftAxis2.setDrawAxisLine(false);

        YAxis rightAxis2 = chart2.getAxisRight();
        rightAxis2.setEnabled(false); // this replaces setStartAtZero(true)

        Legend l2 = chart2.getLegend();
        l2.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l2.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l2.setOrientation(Legend.LegendOrientation.VERTICAL);
        l2.setDrawInside(false);

        generateMultiLineData();
        /*FIN GRAFICO LINEAS MULTIPLES*/



        /* FIN GRAFICO LINEAS MULTIPLES*/
        Log.i("Mensaje:","-"+id);

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
            Data data = new Data(Indicador_Data_M3_M5_Activity.this);
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
            Data data = new Data(Indicador_Data_M3_M5_Activity.this);
            data.open();
            datossubindicador = data.getDataSubIndicadorXId(id_subindicador,nro_subindicador,nro_grafico);
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  datossubindicador;

    }

    public ArrayList<String> obtenerDataEjeXSubIndicadoresXId(int id_subindicador,int nro_subindicador,int nro_grafico )
    {   ArrayList<DataIndicador> datossubindicador = new ArrayList<>();
        ArrayList<String> EjeX = new ArrayList<>();
        String ejex="";
        try {
            Data data = new Data(Indicador_Data_M3_M5_Activity.this);
            data.open();
            datossubindicador = data.getDataSubIndicadorXId(id_subindicador,nro_subindicador,1);

            for (int i=0;i<datossubindicador.size();i++)
            {
                ejex = datossubindicador.get(i).getEjex();
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

    public String obtenerLeyendaSubIndicadoresXId(int id_subindicador, int nro_subindicador )
    {   String leyenda ="";
        try {
            Data data = new Data(Indicador_Data_M3_M5_Activity.this);
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

    //METODO GENERAR DATA 1
    private void generateBarVerticalData() {

        ArrayList<BarEntry> values = new ArrayList<>();
        int subindicadorsize=obtenerDataSubIndicadoresXId(id,1,1).size();

        for (int i = 0; i < + subindicadorsize; i++) {
            values.add(new BarEntry(i, obtenerDataSubIndicadoresXId(id,1,1).get(i).getDato()));
        }

        BarDataSet set1;
        set1 = new BarDataSet(values, "");
        set1.setColor(Color.rgb(61, 165, 255));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        if(obtenerDataEjeXSubIndicadoresXId(id,1,1).size()<13)
        {data.setValueTextSize(10f);}

        else{
         data.setValueTextSize(6.5f);
        }

        data.setBarWidth(0.9f);
        chart.setData(data);
        chart.setFitBars(true);
        chart.invalidate();
    }

    //METODO GENERAR DATA 2
    public void generateMultiLineData(){
        chart.resetTracking();
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        String titulo="";
        int subindicadorsize1=obtenerDataSubIndicadoresXId(id,1,2).size();
        int subindicadorsize2=obtenerDataSubIndicadoresXId(id,2,2).size();
        int subindicadorsize3=obtenerDataSubIndicadoresXId(id,3,2).size();
        int subindicadorsize4=obtenerDataSubIndicadoresXId(id,4,2).size();

        for (int z = 0; z < 4; z++) {

            ArrayList<Entry> values = new ArrayList<>();
            if(z==0){
                for (int i = 0; i < subindicadorsize1; i++) {

                    float val = obtenerDataSubIndicadoresXId(id,1,2).get(i).getDato();
                    values.add(new Entry(i, (float) val));
                }

                titulo = obtenerLeyendaSubIndicadoresXId(id,1);


            }
            if(z==1){
                for (int i = 0; i < subindicadorsize2; i++) {

                    float val = obtenerDataSubIndicadoresXId(id,2,2).get(i).getDato();
                    values.add(new Entry(i, (float) val));
                }
                titulo = obtenerLeyendaSubIndicadoresXId(id,2);

            }
            if(z==2){
                for (int i = 0; i < subindicadorsize3; i++) {

                    float val = obtenerDataSubIndicadoresXId(id,3,2).get(i).getDato();
                    values.add(new Entry(i, (float) val));
                }
                titulo = obtenerLeyendaSubIndicadoresXId(id,3);

            }
            if(z==3){
                for (int i = 0; i < subindicadorsize4; i++) {

                    float val = obtenerDataSubIndicadoresXId(id,4,2).get(i).getDato();
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
        chart2.setData(data);
        chart2.invalidate();
    }


}
