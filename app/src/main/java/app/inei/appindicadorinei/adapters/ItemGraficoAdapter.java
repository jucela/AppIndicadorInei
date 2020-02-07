package app.inei.appindicadorinei.adapters;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
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
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.activities.Indicador_Data_Activity;
import app.inei.appindicadorinei.activities.Indicador_Search_Activity;
import app.inei.appindicadorinei.modelo.DAO.Data;
import app.inei.appindicadorinei.modelo.pojos.DataIndicador;
import app.inei.appindicadorinei.modelo.pojos.GraficoSubIndicador;
import app.inei.appindicadorinei.modelo.pojos.LeyendaSubIndicador;

public class ItemGraficoAdapter extends RecyclerView.Adapter<ItemGraficoAdapter.ViewHolderItem>{
    private ArrayList<GraficoSubIndicador> objGraficos;
    private int id;
    Context context;
    Context context1;
    LinearLayout linearLayout1;
    View mViewToImage;


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public ItemGraficoAdapter(ArrayList<GraficoSubIndicador> objGraficos,int id,Context context1) {
        this.objGraficos = objGraficos;
        this.id = id;
        this.context1=context1;
    }
    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grafico_data,viewGroup,false);
        ViewHolderItem viewHolder = new ViewHolderItem(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderItem viewHolder, int position) {
        final GraficoSubIndicador array_grafico = objGraficos.get(position);
        int modelo_grafico = array_grafico.getModelo_grafico();
        int nro_grafico = array_grafico.getNum_grafico();
        /*
          CombineChart	1
          HorizontalBarChart 2
          BarChart	3
          LineChart	4
          PieChart	5
        */

        if(modelo_grafico==1)
        {
            setearModelo1(viewHolder.chart1,nro_grafico);
            viewHolder.txt_titulo_grafico1.setText(obtenerTituloModeloSubIndicadorXId(id,nro_grafico));
            viewHolder.ly1.setVisibility(View.VISIBLE);
        }
        if(modelo_grafico==2)
        {
            setearModelo2(viewHolder.chart2,nro_grafico);
            viewHolder.txt_titulo_grafico2.setText(obtenerTituloModeloSubIndicadorXId(id,nro_grafico));
            viewHolder.ly2.setVisibility(View.VISIBLE);
        }
        if(modelo_grafico==3)
        {
            setearModelo3(viewHolder.chart3,nro_grafico);
            viewHolder.txt_titulo_grafico3.setText(obtenerTituloModeloSubIndicadorXId(id,nro_grafico));
            viewHolder.ly3.setVisibility(View.VISIBLE);
        }
        if(modelo_grafico==4)
        {
            setearModelo4(viewHolder.chart4,nro_grafico);
            viewHolder.txt_titulo_grafico4.setText(obtenerTituloModeloSubIndicadorXId(id,nro_grafico));
            viewHolder.ly4.setVisibility(View.VISIBLE);
        }
        if(modelo_grafico==5)
        {
            setearModelo5(viewHolder.chart4,nro_grafico);
            viewHolder.txt_titulo_grafico4.setText(obtenerTituloModeloSubIndicadorXId(id,nro_grafico));
            viewHolder.ly4.setVisibility(View.VISIBLE);
        }
        if(modelo_grafico==6)
        {
            setearModelo6(viewHolder.chart3,nro_grafico);
            viewHolder.txt_titulo_grafico3.setText(obtenerTituloModeloSubIndicadorXId(id,nro_grafico));
            viewHolder.ly3.setVisibility(View.VISIBLE);
        }
        if(modelo_grafico==7)
        {
            setearModelo7(viewHolder.chart3,nro_grafico);
            viewHolder.txt_titulo_grafico3.setText(obtenerTituloModeloSubIndicadorXId(id,nro_grafico));
            viewHolder.ly3.setVisibility(View.VISIBLE);
        }
        if(modelo_grafico==8)
        {
            setearModelo8(viewHolder.chart1,nro_grafico);
            viewHolder.txt_titulo_grafico1.setText(obtenerTituloModeloSubIndicadorXId(id,nro_grafico));
            viewHolder.ly1.setVisibility(View.VISIBLE);
        }
        if(modelo_grafico==9)
        {
            setearModelo9(viewHolder.chart3,nro_grafico);
            viewHolder.txt_titulo_grafico3.setText(obtenerTituloModeloSubIndicadorXId(id,nro_grafico));
            viewHolder.ly3.setVisibility(View.VISIBLE);
        }
        if(modelo_grafico==10)
        {
            setearModelo10(viewHolder.chart2,nro_grafico);
            viewHolder.txt_titulo_grafico2.setText(obtenerTituloModeloSubIndicadorXId(id,nro_grafico));
            viewHolder.ly2.setVisibility(View.VISIBLE);
        }
        if(modelo_grafico==11)
        {
            setearModelo11(viewHolder.chart5,nro_grafico);
            viewHolder.txt_titulo_grafico5.setText(obtenerTituloModeloSubIndicadorXId(id,nro_grafico));
            viewHolder.ly5.setVisibility(View.VISIBLE);
        }




        viewHolder.img_chart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("juxe","prueba");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(context1, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED){
                        saveToGallery(viewHolder.chart1,"Imagen");
                    }
                    else
                    {
                    //    ActivityCompat.requestPermissions(context1, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                }
                else
                {
                    saveToGallery(viewHolder.chart1,"Imagen");
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return objGraficos.size();
    }

    public static class ViewHolderItem extends RecyclerView.ViewHolder{

        LinearLayout ly1;
        LinearLayout ly2;
        LinearLayout ly3;
        LinearLayout ly4;
        LinearLayout ly5;



        TextView txt_titulo_grafico1;
        TextView txt_titulo_grafico2;
        TextView txt_titulo_grafico3;
        TextView txt_titulo_grafico4;
        TextView txt_titulo_grafico5;

        ImageView img_chart1;

        CombinedChart chart1;
        HorizontalBarChart chart2;
        BarChart chart3;
        LineChart chart4;
        PieChart chart5;

        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);

            ly1 = itemView.findViewById(R.id.layout_grafico_t1);
            ly2 = itemView.findViewById(R.id.layout_grafico_t2);
            ly3 = itemView.findViewById(R.id.layout_grafico_t3);
            ly4 = itemView.findViewById(R.id.layout_grafico_t4);
            ly5 = itemView.findViewById(R.id.layout_grafico_t5);

            txt_titulo_grafico1 = itemView.findViewById(R.id.txt_titulo_grafico1);
            txt_titulo_grafico2 = itemView.findViewById(R.id.txt_titulo_grafico2);
            txt_titulo_grafico3 = itemView.findViewById(R.id.txt_titulo_grafico3);
            txt_titulo_grafico4 = itemView.findViewById(R.id.txt_titulo_grafico4);
            txt_titulo_grafico5 = itemView.findViewById(R.id.txt_titulo_grafico5);

            chart1 = itemView.findViewById(R.id.chart_t1);
            chart2 = itemView.findViewById(R.id.chart_t2);
            chart3 = itemView.findViewById(R.id.chart_t3);
            chart4 = itemView.findViewById(R.id.chart_t4);
            chart5 = itemView.findViewById(R.id.chart_t5);

            img_chart1 = itemView.findViewById(R.id.id_img_chart1);

        }
    }

    //METODOS
    public ArrayList<DataIndicador> obtenerDataSubIndicadoresXId(int id_subindicador,int nro_subindicador,int nro_grafico )
    {   ArrayList<DataIndicador> datossubindicador = new ArrayList<>();
        try {
            Data data = new Data(context);
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
            Data data = new Data(context);
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

    public ArrayList<String> obtenerDataEjeYSubIndicadoresXId(int id_subindicador,int nro_subindicador,int nro_grafico)
    {   ArrayList<DataIndicador> datossubindicador = new ArrayList<>();
        ArrayList<String> EjeY = new ArrayList<>();
        String ejey="";
        try {
            Data data = new Data(context);
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

    public String obtenerTituloModeloSubIndicadorXId(int id,int nro_grafico)
    { String titulo="";
        try {
            Data data = new Data(context);
            data.open();
            GraficoSubIndicador graficosubindicadorObj = data.getTituloGraficoSubIndicador(id,nro_grafico);
            titulo =  graficosubindicadorObj.getTitulo_grafico();
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  titulo;

    }

    public String obtenerLeyendaSubIndicadoresXId(int id_subindicador, int nro_subindicador )
    {   String leyenda ="";
        try {
            Data data = new Data(context);
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

//    public void descargarGrafico(String titulo){
//        Date now = new Date();
//        View mViewToImage;
//        Bitmap bitmap;
//
//        try {
//                String mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/"+titulo+"_"+now+".jpg";
//                mViewToImage = findViewById(R.id.layout_grafico_t1);
//                mViewToImage.setDrawingCacheEnabled(true);
//                bitmap =Bitmap.createBitmap(mViewToImage.getDrawingCache());
//                mViewToImage.setDrawingCacheEnabled(false);
//                File imageFile = new File(mPath);
//                FileOutputStream outputStream = new FileOutputStream(imageFile);
//                int quality = 100;
//                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
//                outputStream.flush();
//                outputStream.close();
//                Toast.makeText(context,"Imagen Descargada Exitosamente!",Toast.LENGTH_SHORT).show();
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    //GRAFICO 1
    private BarData generateBarData(int nro_grafico) {
        ArrayList<BarEntry> entries1 = new ArrayList<>();

        int subindicadorsize=obtenerDataSubIndicadoresXId(id,1,nro_grafico).size();

        for (int i = 0; i < subindicadorsize; i++) {
            entries1.add(new BarEntry(i+0.25f, obtenerDataSubIndicadoresXId(id,1,nro_grafico).get(i).getDato()));
        }

        BarDataSet set1 = new BarDataSet(entries1, obtenerLeyendaSubIndicadoresXId(id,1));
        set1.setColor(Color.rgb(61, 165, 255));
        set1.setValueTextColor(Color.rgb(0, 0, 0));

        if(obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico).size()<15)
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
    private LineData generateLineData(int nro_grafico) {
        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < obtenerDataSubIndicadoresXId(id,2,nro_grafico).size(); i++)
        {    int subindicadorsize=obtenerDataSubIndicadoresXId(id,2,nro_grafico).size();
            if(i<subindicadorsize)
            {
                entries.add(new Entry(i + 0.75f, obtenerDataSubIndicadoresXId(id,2,nro_grafico).get(i).getDato()));
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
    public CombinedChart setearModelo1(CombinedChart grafico,int nro_grafico){
        CombinedChart chart = grafico;
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,CombinedChart.DrawOrder.LINE
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
        rightAxis.setAxisMinimum(obtenerMenorValor(id,2,nro_grafico)-1);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelCount(6);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico)));//false: formato predeterminado con setValueFormatter

        CombinedData data = new CombinedData();

        data.setData(generateLineData(nro_grafico));
        data.setData(generateBarData(nro_grafico));

        xAxis.setAxisMaximum(data.getXMax() + 0.5f);

        chart.setData(data);
        chart.invalidate();
        chart.animateXY(2000, 2000);
        return chart;
    }

    //GRAFICO 2
    public HorizontalBarChart setearModelo2(HorizontalBarChart grafico,int nro_grafico){
        HorizontalBarChart chart = grafico;
        float barWidth = 0.75f;
        float spaceForBar = 0.5f;
        ArrayList<BarEntry> values = new ArrayList<>();

        int subindicadorsize=obtenerDataSubIndicadoresXId(id,1,nro_grafico).size();

        for (int i = 0; i < subindicadorsize; i++) {
            float val = obtenerDataSubIndicadoresXId(id,1,nro_grafico).get(i).getDato();
            values.add(new BarEntry(i+spaceForBar, val));
        }

        BarDataSet set1;
        set1 = new BarDataSet(values, ""+obtenerTituloModeloSubIndicadorXId(id,nro_grafico));
        set1.setColor(Color.rgb(5, 168, 228));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        chart.setDrawBarShadow(false);
        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(60);
        chart.setPinchZoom(false);
        chart.setScaleYEnabled(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(9f);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(obtenerDataEjeYSubIndicadoresXId(id,1,nro_grafico).size());
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelCount(obtenerDataEjeYSubIndicadoresXId(id,1,nro_grafico).size());
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeYSubIndicadoresXId(id,1,nro_grafico)));
        YAxis yl = chart.getAxisLeft();
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setEnabled(false);
        yl.setAxisMinimum(0f);

        YAxis yr = chart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(true);
        yr.setEnabled(true);
        yr.setAxisMinimum(0f);

        chart.animateY(2500);

        Legend l = chart.getLegend();
        l.setEnabled(false);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(10f);
        l.setTextSize(12);
        l.setXEntrySpace(4000f);
        l.setYEntrySpace(400f);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(barWidth);
        chart.setData(data);
        chart.setFitBars(true);
        chart.invalidate();
        return chart;
    }

    //GRAFICO 3
    public BarChart setearModelo3(BarChart grafico,int nro_grafico){
        BarChart chart = grafico;
        ArrayList<BarEntry> values = new ArrayList<>();
        int subindicadorsize=obtenerDataSubIndicadoresXId(id,1,nro_grafico).size();

        for (int i = 0; i < + subindicadorsize; i++) {
            values.add(new BarEntry(i, obtenerDataSubIndicadoresXId(id,1,nro_grafico).get(i).getDato()));
        }

        BarDataSet set1;
        set1 = new BarDataSet(values, "");
        set1.setColor(Color.rgb(61, 165, 255));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        if(obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico).size()<13)
        {data.setValueTextSize(10f);}

        else{
            data.setValueTextSize(6.5f);
        }

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

        if(obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico).size()<13)
        {   xAxis.setLabelCount(6);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico)));
            xAxis.setLabelRotationAngle(0f);
        }

        else{xAxis.setLabelCount(6);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico)));
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
        chart.animateXY(2000, 2000);

        data.setBarWidth(0.9f);
        chart.setData(data);
        chart.setFitBars(true);
        chart.invalidate();

        return chart;
    }

    //GRAFICO 4
    public LineChart setearModelo4(LineChart grafico,int nro_grafico){
        LineChart chart = grafico;
        ArrayList<Entry> values = new ArrayList<>();
        int subindicadorsize=obtenerDataSubIndicadoresXId(id,1,nro_grafico).size();
        for (int i = 0; i < subindicadorsize; i++) {

            float val = obtenerDataSubIndicadoresXId(id,1,nro_grafico).get(i).getDato();
            values.add(new Entry(i, val));
        }

        LineDataSet set1;
        set1 = new LineDataSet(values, "Leyenda");

        set1.setDrawIcons(false);
        set1.setColor(Color.rgb(5, 168, 228));
        set1.setCircleColor(Color.rgb(5, 168, 228));
        set1.setLineWidth(2.5f);
        set1.setCircleRadius(4f);
        set1.setDrawCircleHole(false);
        set1.setFormLineWidth(1f);
        set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        set1.setFormSize(15.f);
        set1.setValueTextSize(9f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);


        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData data = new LineData(dataSets);

        chart.setData(data);
        chart.invalidate();

        chart.setBackgroundColor(Color.WHITE);
        chart.getDescription().setEnabled(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        XAxis xAxis2 = chart.getXAxis();

        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setGranularity(1f);
        xAxis2.setCenterAxisLabels(true);
        xAxis2.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico)));
        xAxis2.setLabelCount(6);
        xAxis2.setEnabled(true);
        xAxis2.setLabelRotationAngle(0f);
        xAxis2.setDrawGridLines(false);

        YAxis yAxis = chart.getAxisLeft();

        chart.getAxisRight().setEnabled(false);

        yAxis.setDrawAxisLine(false);
        yAxis.setAxisMaximum(obtenerMayorValor(id,1,nro_grafico)+1);
        yAxis.setAxisMinimum(obtenerMenorValor(id,1,nro_grafico)-1);

        chart.animateXY(2000,2000);

        Legend l2 = chart.getLegend();
        l2.setEnabled(false);

        // draw legend entries as lines
        l2.setForm(Legend.LegendForm.LINE);


        return chart;
    }

    //GRAFICO 5
    private final int[] colors = new int[] {
            ColorTemplate.MATERIAL_COLORS[0],
            ColorTemplate.MATERIAL_COLORS[1],
            ColorTemplate.MATERIAL_COLORS[2],
            ColorTemplate.MATERIAL_COLORS[3],
            Color.rgb(23, 197, 255)
            };

    public LineChart setearModelo5(LineChart grafico,int nro_grafico){
            LineChart chart = grafico;
            chart.resetTracking();
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();

            String titulo="";
            int subindicadorsize1=obtenerDataSubIndicadoresXId(id,1,nro_grafico).size();
            int subindicadorsize2=obtenerDataSubIndicadoresXId(id,2,nro_grafico).size();
            int subindicadorsize3=obtenerDataSubIndicadoresXId(id,3,nro_grafico).size();
            int subindicadorsize4=obtenerDataSubIndicadoresXId(id,4,nro_grafico).size();
            int subindicadorsize5=obtenerDataSubIndicadoresXId(id,5,nro_grafico).size();

            for (int z = 0; z < 5; z++) {

                ArrayList<Entry> values = new ArrayList<>();
                if(z==0){
                    for (int i = 0; i < subindicadorsize1; i++) {

                        float val = obtenerDataSubIndicadoresXId(id,1,nro_grafico).get(i).getDato();
                        values.add(new Entry(i, (float) val));
                    }

                    titulo = obtenerLeyendaSubIndicadoresXId(id,1);


                }
                if(z==1){
                    for (int i = 0; i < subindicadorsize2; i++) {

                        float val = obtenerDataSubIndicadoresXId(id,2,nro_grafico).get(i).getDato();
                        values.add(new Entry(i, (float) val));
                    }
                    titulo = obtenerLeyendaSubIndicadoresXId(id,2);

                }
                if(z==2){
                    for (int i = 0; i < subindicadorsize3; i++) {

                        float val = obtenerDataSubIndicadoresXId(id,3,nro_grafico).get(i).getDato();
                        values.add(new Entry(i, (float) val));
                    }
                    titulo = obtenerLeyendaSubIndicadoresXId(id,3);

                }
                if(z==3){
                    for (int i = 0; i < subindicadorsize4; i++) {

                        float val = obtenerDataSubIndicadoresXId(id,4,nro_grafico).get(i).getDato();
                        values.add(new Entry(i, (float) val));
                    }
                    titulo = obtenerLeyendaSubIndicadoresXId(id,4);
                }
                if(z==4){
                    for (int i = 0; i < subindicadorsize5; i++) {

                        float val = obtenerDataSubIndicadoresXId(id,5,nro_grafico).get(i).getDato();
                        values.add(new Entry(i, (float) val));
                    }
                    titulo = obtenerLeyendaSubIndicadoresXId(id,5);
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

        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);
        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        chart.setPinchZoom(false);
        chart.animateX(2000);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(false);
        xAxis.setLabelCount(6);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico)));
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false); // this replaces setStartAtZero(true)

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);


        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
        return chart;
    }

    //GRAFICO 6
    public BarChart setearModelo6(BarChart grafico,int nro_grafico){
        BarChart chart = grafico;
        float groupSpace = 0.1f;
        float barSpace = 0.05f; // x4 DataSet
        float barWidth = 0.25f; // x4 DataSet
        int startYear = 0;
        int endYear = obtenerDataSubIndicadoresXId(id,1,nro_grafico).size();


        ArrayList<BarEntry> values1 = new ArrayList<>();
        ArrayList<BarEntry> values2 = new ArrayList<>();
        ArrayList<BarEntry> values3 = new ArrayList<>();


        for (int i = 0; i < obtenerDataSubIndicadoresXId(id,1,nro_grafico).size(); i++) {

            values1.add(new BarEntry(i,obtenerDataSubIndicadoresXId(id,1,nro_grafico).get(i).getDato()));
            values2.add(new BarEntry(i,obtenerDataSubIndicadoresXId(id,2,nro_grafico).get(i).getDato()));
            if(obtenerDataSubIndicadoresXId(id,3,nro_grafico).size()>0) {
                values3.add(new BarEntry(i, obtenerDataSubIndicadoresXId(id, 3,nro_grafico).get(i).getDato()));
            }
        }

        BarDataSet set1, set2, set3;
        set1 = new BarDataSet(values1, obtenerLeyendaSubIndicadoresXId(id,1));
        set1.setColor(Color.rgb(46, 204, 113));
        set2 = new BarDataSet(values2, obtenerLeyendaSubIndicadoresXId(id,2));
        set2.setColor(Color.rgb(241, 196, 15));
        if(obtenerDataSubIndicadoresXId(id,3,nro_grafico).size()>0) {
            set3 = new BarDataSet(values3, obtenerLeyendaSubIndicadoresXId(id,3));
            set3.setColor(Color.rgb(231, 76, 60));}
        else{
            set3 = new BarDataSet(values3, "");
        }

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
        xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico)));
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        chart.getAxisRight().setEnabled(false);
        chart.animateY(2500);


        BarData data = new BarData(set1, set2, set3);
        data.setValueFormatter(new LargeValueFormatter());
        chart.setData(data);
        chart.getBarData().setBarWidth(barWidth);
        chart.getXAxis().setAxisMinimum(startYear);
        chart.getXAxis().setAxisMaximum(startYear +chart.getBarData().getGroupWidth(groupSpace, barSpace) * endYear);
        chart.groupBars(startYear, groupSpace, barSpace);
        chart.invalidate();

        return chart;
    }

    //GRAFICO 7
    public BarChart setearModelo7(BarChart grafico,int nro_grafico){
        BarChart chart = grafico;
        ArrayList<BarEntry> values = new ArrayList<>();
        int colores[] = {0,0,0};
        String leyenda[] = {"","",""};

        int subindicadorsize=obtenerDataSubIndicadoresXId(id,1,nro_grafico).size();

        for (int i = 0; i < + subindicadorsize; i++) {

            float val1 = obtenerDataSubIndicadoresXId(id,1,nro_grafico).get(i).getDato();
            float val2 = obtenerDataSubIndicadoresXId(id,2,nro_grafico).get(i).getDato();
            if(obtenerDataSubIndicadoresXId(id,3,nro_grafico).size()>0)
            {float val3 = obtenerDataSubIndicadoresXId(id,3,nro_grafico).get(i).getDato();
                values.add(new BarEntry(i, new float[]{val1,val2,val3}));
                int color1 =Color.rgb(231, 76, 60);
                int color2 =Color.rgb(61, 165, 255);
                int color3 =Color.rgb(23, 197, 255);
                String leyenda1 = obtenerLeyendaSubIndicadoresXId(id,1);
                String leyenda2 = obtenerLeyendaSubIndicadoresXId(id,2);
                String leyenda3 = obtenerLeyendaSubIndicadoresXId(id,3);
                colores = new int[]{color1, color2, color3};
                leyenda = new String[]{leyenda1,leyenda2,leyenda3};
            }
            else {

                int color1 =Color.rgb(231, 76, 60);
                int color2 =Color.rgb(61, 165, 255);
                String leyenda1 = obtenerLeyendaSubIndicadoresXId(id,1);
                String leyenda2 = obtenerLeyendaSubIndicadoresXId(id,2);
                colores = new int[]{color1, color2};
                leyenda = new String[]{leyenda1,leyenda2};
                values.add(new BarEntry(i, new float[]{val1,val2}));}
        }

        BarDataSet set1;
        set1 = new BarDataSet(values, "");
        set1.setStackLabels(leyenda);
        set1.setColors(colores);
        set1.setValueTextColor(Color.rgb(0, 0, 0));
        set1.setValueTextSize(8f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = 0.7f;

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setMaxVisibleValueCount(60);
        chart.setPinchZoom(false);
        chart.setScaleYEnabled(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico).size());
        xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico)));
        xAxis.setLabelRotationAngle(0f);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false); // this replaces setStartAtZero(true)

        Legend l = chart.getLegend();
        l.setEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        chart.animateXY(2000, 2000);

        BarData data = new BarData(dataSets);
        data.setValueTextSize(8f);
        data.setBarWidth(barWidth);
        data.getDataSetLabels();
        chart.setData(data);

        return chart;
    }

    //GRAFICO 8
    private BarData generateBarData2(int nro_grafico) {

        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();

        int subindicadorsize=obtenerDataSubIndicadoresXId(id,1,nro_grafico).size();

        for (int i = 0; i < subindicadorsize; i++) {
            entries1.add(new BarEntry(0, obtenerDataSubIndicadoresXId(id,1,nro_grafico).get(i).getDato()));
            entries2.add(new BarEntry(0, obtenerDataSubIndicadoresXId(id,2,nro_grafico).get(i).getDato()));
        }

        BarDataSet set1 = new BarDataSet(entries1, obtenerLeyendaSubIndicadoresXId(id,1));
        set1.setColor(Color.rgb(61, 165, 255));
        set1.setValueTextColor(Color.rgb(0, 0, 0));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, obtenerLeyendaSubIndicadoresXId(id,2));
        set2.setColor(Color.rgb(23, 197, 255));
        set2.setValueTextColor(Color.rgb(0, 0, 0));
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1, set2);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }
    private LineData generateLineData2(int nro_grafico) {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int i = 0; i < obtenerDataSubIndicadoresXId(id,3,nro_grafico).size(); i++)
        {
            entries.add(new Entry(i + 0.5f,obtenerDataSubIndicadoresXId(id,3,nro_grafico).get(i).getDato()));

        }

        LineDataSet set = new LineDataSet(entries, obtenerLeyendaSubIndicadoresXId(id,3));
        set.setColor(Color.rgb(255 , 102, 102));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(255 , 102, 102));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(255 , 102, 102));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(255 , 102, 102));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    public CombinedChart setearModelo8(CombinedChart grafico,int nro_grafico){
        CombinedChart chart = grafico;
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);
        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR,CombinedChart.DrawOrder.LINE
        });

        Legend l = chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);//false: formato predeterminado con setValueFormatter
        xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico)));

        CombinedData data = new CombinedData();

        data.setData(generateLineData2(nro_grafico));
        data.setData(generateBarData2(nro_grafico));

        xAxis.setAxisMaximum(data.getXMax() + 0.5f);

        chart.setData(data);
        chart.invalidate();
        chart.animateXY(2000, 2000);

        return chart;
    }

    //GRAFICO 9
    public BarChart setearModelo9(BarChart grafico,int nro_grafico){
        BarChart chart = grafico;
        ArrayList<BarEntry> values = new ArrayList<>();
        int subindicadorsize=obtenerDataSubIndicadoresXId(id,1,nro_grafico).size();
        List<Integer> colors = new ArrayList<>();

        int blue = Color.rgb(61, 165, 255);
        int red = Color.rgb(255 , 102, 102);

        for (int i = 0; i < subindicadorsize; i++) {
            BarEntry entry = new BarEntry(i+0.5f,obtenerDataSubIndicadoresXId(id,1,nro_grafico).get(i).getDato());
            values.add(entry);

            if (obtenerDataSubIndicadoresXId(id,1,nro_grafico).get(i).getDato() >= 0)
                colors.add(blue);
            else
                colors.add(red);
        }

        BarDataSet set;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(values, "Values");
            set.setColors(colors);
            set.setValueTextColor(Color.rgb(0,0,0));

            BarData data = new BarData(set);
            data.setValueTextSize(10f);
            data.setBarWidth(0.8f);

            chart.setData(data);
            chart.invalidate();
        }

        chart.setBackgroundColor(Color.WHITE);
        chart.setExtraBottomOffset(10f);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis2 = chart.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setAxisMinimum(0f);
        xAxis2.setDrawGridLines(false);
        xAxis2.setDrawAxisLine(false);
        xAxis2.setTextColor(Color.LTGRAY);//Color titulos eje x
        xAxis2.setTextSize(13f);
        xAxis2.setLabelCount(6);//muestra el numero de valores sin zoonn en el ejex
        xAxis2.setCenterAxisLabels(true);
        xAxis2.setGranularity(1f);

        if(obtenerDataEjeXSubIndicadoresXId(id,1,1).size()<7)
        {   xAxis2.setLabelCount(6);
            xAxis2.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico)));
            xAxis2.setLabelRotationAngle(0f);
        }

        else{xAxis2.setLabelCount(6);
            xAxis2.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico)));
            xAxis2.setLabelRotationAngle(300f);
            xAxis2.setTextColor(Color.BLACK);}

        YAxis left = chart.getAxisLeft();
        left.setDrawGridLines(false);
        left.setDrawAxisLine(true);
        left.setDrawGridLines(true);
        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);

        return chart;
    }

    //GRAFICO 10
    public HorizontalBarChart setearModelo10(HorizontalBarChart grafico,int nro_grafico){
        HorizontalBarChart chart = grafico;
        float barWidth = 0.75f;
        float spaceForBar = 0.5f;
        ArrayList<BarEntry> values = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        int subindicadorsize=obtenerDataSubIndicadoresXId(id,1,nro_grafico).size();

        int blue = Color.rgb(61, 165, 255);
        int red = Color.rgb(255 , 102, 102);

        for (int i = 0; i < subindicadorsize; i++) {

            BarEntry entry = new BarEntry(i+spaceForBar,obtenerDataSubIndicadoresXId(id,1,nro_grafico).get(i).getDato());
            values.add(entry);

            // specific colors
            if (obtenerDataSubIndicadoresXId(id,1,nro_grafico).get(i).getDato() >= 0)
                colors.add(blue);
            else
                colors.add(red);
        }


        BarDataSet set = new BarDataSet(values, "Age Distribution");
        set.setDrawIcons(false);
        set.setValueTextSize(7f);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set.setColors(colors);
        set.setValueTextColor(Color.rgb(0,0,0));
        BarData data = new BarData(set);
        data.setBarWidth(barWidth);

        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setAxisMaximum(obtenerMayorValor(id,1,nro_grafico)+1);
        chart.getAxisRight().setAxisMinimum(obtenerMenorValor(id,1,nro_grafico)-1);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setDrawZeroLine(true);
        chart.getAxisRight().setLabelCount(7, false);
        chart.getAxisRight().setTextSize(9f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextSize(9f);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(obtenerDataEjeYSubIndicadoresXId(id,1,nro_grafico).size());
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelCount(obtenerDataEjeYSubIndicadoresXId(id,1,nro_grafico).size());
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(obtenerDataEjeYSubIndicadoresXId(id,1,nro_grafico)));

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
        chart.setData(data);
        chart.invalidate();

        return chart;
    }

    //GRAFICO 11
    public PieChart setearModelo11(PieChart grafico,int nro_grafico){
        PieChart chart = grafico;
        ArrayList<PieEntry> entries = new ArrayList<>();
        int subindicadorsize=obtenerDataSubIndicadoresXId(id,1,nro_grafico).size();
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of the chart.
        for (int i = 0; i < subindicadorsize ; i++) {
            entries.add(new PieEntry(obtenerDataSubIndicadoresXId(id,1,nro_grafico).get(i).getDato(),obtenerDataEjeXSubIndicadoresXId(id,1,nro_grafico).get(i)+" ("+obtenerDataSubIndicadoresXId(id,1,nro_grafico).get(i).getDato()+" )"));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 0));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);


        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);
        chart.setDrawEntryLabels(false);
        chart.setDrawCenterText(false);

        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 5, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(40f);
        chart.setTransparentCircleRadius(43f);
        chart.setDrawCenterText(true);
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.animateY(1400, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(22.5f);

        // entry label styling
        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(12f);

        // undo all highlights
        chart.highlightValues(null);
        chart.invalidate();

        return chart;
    }

    protected void saveToGallery(Chart chart, String name) {
        if (chart.saveToGallery(name + "_" + System.currentTimeMillis(), 70))
            Toast.makeText(context1, "Saving SUCCESSFUL!",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context1, "Saving FAILED!", Toast.LENGTH_SHORT)
                    .show();
    }








}
