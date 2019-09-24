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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.modelo.DAO.Data;
import app.inei.appindicadorinei.modelo.pojos.DataIndicador;
import app.inei.appindicadorinei.modelo.pojos.SubIndicador;

public class Indicador_Data_M10_Activity extends AppCompatActivity {
    int id;
    TextView txt_titulo;
    TextView txt_descripcion;
    private HorizontalBarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_m10);
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

        /*INICIO GRAFICO BARRAS HORIZONTALES POSITIVO Y NEGATIVO*/
        chart = findViewById(R.id.chart_m10);
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setAxisMaximum(obtenerMayorValor(id,1,1)+1);
        chart.getAxisRight().setAxisMinimum(obtenerMenorValor(id,1,1)-1);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setDrawZeroLine(true);
        chart.getAxisRight().setLabelCount(7, false);
        chart.getAxisRight().setTextSize(9f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(9f);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(obtenerDataEjeYSubIndicadoresXId(id,1,1).size());
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelCount(obtenerDataEjeYSubIndicadoresXId(id,1,1).size());
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeYSubIndicadoresXId(id,1,1)));

        Legend l = chart.getLegend();
        l.setEnabled(false);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setFormToTextSpace(4f);
        l.setXEntrySpace(6f);
        chart.animateY(2500);
        // IMPORTANT: When using negative values in stacked bars, always make sure the negative values are in the array first
        generateBarHorizontalNegPosData();
        /* FIN GRAFICO BARRAS HORIZONTALES POSITIVO Y NEGATIVO*/
        Log.i("Mensaje:",""+id);

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
            Data data = new Data(Indicador_Data_M10_Activity.this);
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
            Data data = new Data(Indicador_Data_M10_Activity.this);
            data.open();
            datossubindicador = data.getDataSubIndicadorXId(id_subindicador,nro_subindicador,nro_grafico);
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  datossubindicador;

    }

    public ArrayList<String> obtenerDataEjeYSubIndicadoresXId(int id_subindicador,int nro_subindicador,int nro_grafico)
    {   ArrayList<DataIndicador> datossubindicador = new ArrayList<>();
        ArrayList<String> EjeY = new ArrayList<>();
        String ejey="";
        try {
            Data data = new Data(Indicador_Data_M10_Activity.this);
            data.open();
            datossubindicador = data.getDataSubIndicadorXId(id_subindicador,nro_subindicador,nro_grafico);

            for (int i=0;i<datossubindicador.size();i++)
            {
                ejey = datossubindicador.get(i).getEjey();
                EjeY.add(ejey);
            }
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  EjeY;

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

    //METODO GENERAR DATA
    private void generateBarHorizontalNegPosData() {


        float barWidth = 0.75f;
        float spaceForBar = 0.5f;
        ArrayList<BarEntry> values = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        int subindicadorsize=obtenerDataSubIndicadoresXId(id,1,1).size();

        int blue = Color.rgb(61, 165, 255);
        int red = Color.rgb(255 , 102, 102);

        for (int i = 0; i < subindicadorsize; i++) {

            BarEntry entry = new BarEntry(i+spaceForBar,obtenerDataSubIndicadoresXId(id,1,1).get(i).getDato());
            values.add(entry);

            // specific colors
            if (obtenerDataSubIndicadoresXId(id,1,1).get(i).getDato() >= 0)
                colors.add(blue);
            else
                colors.add(red);
        }


        BarDataSet set = new BarDataSet(values, "Age Distribution");
        set.setDrawIcons(false);
        set.setValueTextSize(7f);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        //set.setColors(Color.rgb(67,67,72), Color.rgb(124,181,236));
        set.setColors(colors);
        set.setValueTextColor(Color.rgb(0,0,0));
        //set.setStackLabels(new String[]{"Men","Women"});
        BarData data = new BarData(set);
        data.setBarWidth(barWidth);
        chart.setData(data);
        chart.invalidate();

        //chart.setFitBars(true);
        //chart.invalidate();
    }
}
