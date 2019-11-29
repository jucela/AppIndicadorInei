package app.inei.appindicadorinei.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.github.mikephil.charting.charts.Chart;
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
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.adapters.ItemGraficoAdapter;
import app.inei.appindicadorinei.modelo.DAO.Data;
import app.inei.appindicadorinei.modelo.DAO.SQLConstantes;
import app.inei.appindicadorinei.modelo.pojos.DataIndicador;
import app.inei.appindicadorinei.modelo.pojos.GraficoSubIndicador;
import app.inei.appindicadorinei.modelo.pojos.LeyendaSubIndicador;
import app.inei.appindicadorinei.modelo.pojos.SubIndicador;

public class Indicador_Data_Activity extends AppCompatActivity {
    int id;
    int id_indicador;
    String title;
    TextView txt_titulo;
    TextView txt_descripcion;
    ItemGraficoAdapter itemGraficoAdapter;
    RecyclerView recyclerView;
    Context context;
    SQLiteToExcel sqliteToExcel;
    String directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();//Solo se ve por dispositivo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicador_data);
        Toolbar toolbar = findViewById(R.id.toolbarData);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic3_wc);
        }

        id              = getIntent().getExtras().getInt("id");
        id_indicador    = getIntent().getExtras().getInt("id");
        title           = (obtenerNombreSubIndicador(id).get(0)).replaceAll(" ", "_");
        txt_titulo      = (TextView) findViewById(R.id.cardview1_data_title1);
        txt_descripcion = (TextView) findViewById(R.id.cardview1_data_descrip1);
        txt_titulo.setText(obtenerNombreSubIndicador(id).get(0));
        txt_descripcion.setText(obtenerNombreSubIndicador(id).get(1));

        recyclerView = (RecyclerView) findViewById(R.id.lista_graficos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        itemGraficoAdapter = new ItemGraficoAdapter(obtenerModeloGraficoSubIndicadoresXId(id),id,this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(itemGraficoAdapter);

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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Si desea sincronizar la información de este indicador, presione el boton SINCRONIZAR en el Menú Principal")
                    .setIcon(R.drawable.ic_info_outline_black_24dp)
                    .setTitle("Información")
                    .setCancelable(false)
                    .setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }else
        if (id == R.id.id_item_descarga_imagen) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(Indicador_Data_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {descargarGrafico(title);}
                else
                { ActivityCompat.requestPermissions(Indicador_Data_Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);}
            }
            else
            {descargarGrafico(title);}

        }else
        if (id == R.id.id_item_descarga_excel) {
            sqliteToExcel = new SQLiteToExcel(getApplicationContext(), SQLConstantes.DB_NAME, directory_path);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(Indicador_Data_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {descargarExcel(String.valueOf(id_indicador),title);}
                else
                { ActivityCompat.requestPermissions(Indicador_Data_Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);}
            }
            else
            {descargarExcel(String.valueOf(id_indicador),title);}

        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<String> obtenerNombreSubIndicador(int id)
    {   ArrayList<String> datos = new ArrayList<>();
        String nombresubindicador="";
        String descripcionsubindicador="";
        try {
            Data data = new Data(Indicador_Data_Activity.this);
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

    public ArrayList<GraficoSubIndicador> obtenerModeloGraficoSubIndicadoresXId(int id)
    {   ArrayList<GraficoSubIndicador> modelosGraficos = new ArrayList<>();
        ArrayList<Integer> modeloGrafico = new ArrayList<>();
        try {
            Data data = new Data(Indicador_Data_Activity.this);
            data.open();
            modelosGraficos = data.getGraficoSubIndicadorXId(id);
            data.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return  modelosGraficos;

    }

    public void descargarGrafico(String titulo){
        Date now = new Date();
        Bitmap bitmap=null;

        try {
            String mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/"+titulo+"_"+now+".jpg";
            //String  path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

                    LinearLayout mViewToImage = (LinearLayout) findViewById(R.id.ly_indicador_data);
                    bitmap =Bitmap.createBitmap(mViewToImage.getWidth(),mViewToImage.getHeight(),Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    Drawable bgDrawable = mViewToImage.getBackground();
                    if (bgDrawable != null) {
                     bgDrawable.draw(canvas);
                    } else {
                    canvas.drawColor(Color.WHITE);
                    }
                    mViewToImage.draw(canvas);
                    File imageFile = new File(mPath);
                    FileOutputStream outputStream = new FileOutputStream(imageFile);
                    int quality = 100;
                    bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    Toast.makeText(Indicador_Data_Activity.this,"Imagen Descargada Exitosamente.",Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void descargarExcel(String id,String titulo){
        sqliteToExcel.exportAllNTables(""+titulo+".xls",id,titulo, new SQLiteToExcel.ExportListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted(String filePath) {
                Toast.makeText(Indicador_Data_Activity.this,"Excel Descargado Exitosamente",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(Indicador_Data_Activity.this,"Error:"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }











}
