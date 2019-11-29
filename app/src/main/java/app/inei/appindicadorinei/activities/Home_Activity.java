package app.inei.appindicadorinei.activities;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.fragments.Home_Fragment;
import app.inei.appindicadorinei.modelo.DAO.Data;
import app.inei.appindicadorinei.modelo.DAO.SQLConstantes;
import app.inei.appindicadorinei.modelo.DAO.VolleySingleton;
import app.inei.appindicadorinei.modelo.pojos.DataIndicador;
import app.inei.appindicadorinei.modelo.pojos.ErrorService;
import app.inei.appindicadorinei.modelo.pojos.GraficoSubIndicador;
import app.inei.appindicadorinei.modelo.pojos.Indicador;
import app.inei.appindicadorinei.modelo.pojos.LeyendaSubIndicador;
import app.inei.appindicadorinei.modelo.pojos.SubIndicador;

public class Home_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Data data;
    String estado;
    Context context;
    //boolean conexion=false;
    private ProgressDialog progressDialog;
    final int contador =0;
    JSONObject jsonParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDrawer);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(Home_Activity.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Home_Fragment home_fragment = new Home_Fragment(Home_Activity.this);
        fragmentTransaction.replace(R.id.fragment_layout,home_fragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.drawer_, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ConnectivityManager con = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =con.getActiveNetworkInfo();
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

            if (id == R.id.id_item_information) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Si desea sincronizar la información de Indicadores Demográficos,Sociales y Económicos 2019, presione el boton SINCRONIZAR.")
                    //.setView(R.drawable.ic_3d_rotation)
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
            //return true;
        } else
            if (id == R.id.id_item_actualizar)  {
            if(networkInfo!=null && networkInfo.isConnected())
            {
                verificarConexionWS();
                Log.i("MENSAJE", "Comprobando conexión...");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Desea Actualizar la Información?")
                        .setIcon(R.drawable.ic_action_reload_black_18)
                        .setTitle("Sincronizar")
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(Home_Activity.this, "Sincronizando...", Toast.LENGTH_LONG).show();
                                if(estadoConexionWS()==false || estadoConexionWS()==true)/*No hay Conexion*/
                                {   Log.i("MENSAJE", "Verificando conexión con Web Service");
                                    //verificarConexionWS();
                                    try {Thread.sleep(1500);}
                                    catch (InterruptedException e)
                                    {e.printStackTrace();}
                                    if(estadoConexionWS()==true)/*Si hay Conexion*/
                                    {
                                        Log.i("MENSAJE", "Conexión exitosa con Web Service.");
                                        Log.i("MENSAJE", "Mismo o diferente día");
                                        new cargarDataApi2().execute();
                                        updateSettings();
                                    }
                                    else
                                    {   /*NO hay Conexion*/
                                        Toast.makeText(Home_Activity.this, "No se pudo Sincronizar,Vuelva a intentarlo más tarde.", Toast.LENGTH_LONG).show();
                                        Log.i("MENSAJE:", "No hay Conexiòn con Web Service");
                                    }
                                }
                                else
                                {
                                    Toast.makeText(Home_Activity.this, "Problemas en la configuración de las fechas.", Toast.LENGTH_LONG).show();
                                    Log.i("MENSAJE:", "La tabla de fecha esta vacia");
                                }
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                //return true;
            }
            else {
                Toast.makeText(this, "Verifique si tiene una conexión a INTERNET para SINCRONIZAR", Toast.LENGTH_LONG).show();}

        } else
            if (id == R.id.id_item_exit)        {
                insertDataOnline();
                //crearjson();
                //return true;
            }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        ConnectivityManager con = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =con.getActiveNetworkInfo();
        // Handle navigation view item clicks here.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Home_Fragment home_fragment = new Home_Fragment(Home_Activity.this);
            fragmentTransaction.replace(R.id.fragment_layout,home_fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_search) {
            Intent intent = new Intent(getApplicationContext(), Search_Activity.class);
            startActivity(intent);
        }else if (id == R.id.nav_sincronizar) {
            if(networkInfo!=null && networkInfo.isConnected())
            {
                verificarConexionWS();
                Log.i("MENSAJE", "Comprobando conexión...");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Desea Actualizar la Información?")
                        .setIcon(R.drawable.ic_action_reload)
                        .setTitle("Sincronizar")
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(Home_Activity.this, "Sincronizando...", Toast.LENGTH_LONG).show();
                                if(estadoConexionWS()==false || estadoConexionWS()==true)/*No hay Conexion*/
                                {   Log.i("MENSAJE", "Verificando conexión con Web Service");
                                    //verificarConexionWS();
                                    try {Thread.sleep(1000);}
                                    catch (InterruptedException e)
                                    {e.printStackTrace();}
                                    if(estadoConexionWS()==true)/*Si hay Conexion*/
                                    {
                                        Log.i("MENSAJE", "Conexión exitosa con Web Service.");
                                        Log.i("MENSAJE", "Mismo o diferente día");
                                        new cargarDataApi2().execute();
                                        updateSettings();

                                    }
                                    else
                                    {   /*NO hay Conexion*/
                                        Toast.makeText(Home_Activity.this, "No se pudo Sincronizar,Vuelva a intentarlo más tarde.", Toast.LENGTH_LONG).show();
                                        Log.i("MENSAJE:", "No hay Conexiòn con Web Service");
                                    }
                                }
                                else
                                {
                                    Toast.makeText(Home_Activity.this, "Problemas en la configuración de las fechas.", Toast.LENGTH_LONG).show();
                                    Log.i("MENSAJE:", "La tabla de fecha esta vacia");
                                }
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                //return true;
            }
            else {
                Toast.makeText(this, "Verifique si tiene una conexión a INTERNET para SINCRONIZAR", Toast.LENGTH_LONG).show();}

        }else if (id == R.id.nav_exit) {
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void descargarGrafico(String titulo){
        Date now = new Date();
        View mViewToImage;
        Bitmap bitmap;

        try {
            String mPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/"+titulo+"_"+now+".jpg";
            //lo = mViewToImage.findViewById(R.id.layout_grafico_t1);
            mViewToImage = findViewById(R.id.id_x);
            mViewToImage.setDrawingCacheEnabled(true);
            bitmap =Bitmap.createBitmap(mViewToImage.getDrawingCache());
            mViewToImage.setDrawingCacheEnabled(false);
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(Home_Activity.this,"Imagen Descargada Exitosamente!",Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void verificarConexionWS(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_indicador, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                updateErrors("0","No Hay Errores en la Conexion Home XD","1");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError){
                    updateErrors("1","Su dispositivo no está conectado a internet.","0");
                    //Toast.makeText(Inicio_Activity.this, "Su dispositivo no está conectado a internet.",Toast.LENGTH_SHORT).show();
                } else if (error.getCause() instanceof MalformedURLException){
                    updateErrors("2","Solicitud Incorrecta..","0");
                    //Toast.makeText(Inicio_Activity.this, "Solicitud Incorrecta.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError || error.getCause() instanceof IllegalStateException || error.getCause() instanceof JSONException || error.getCause() instanceof XmlPullParserException){
                    updateErrors("3","Parse Error (debido a json o xml no valido)","0");
                    //Toast.makeText(Inicio_Activity.this, "Parse Error (debido a json o xml no valido).",Toast.LENGTH_SHORT).show();
                } else if (error.getCause() instanceof OutOfMemoryError){
                    updateErrors("4","Error de memoria insuficiente.","0");
                    //Toast.makeText(Inicio_Activity.this, "Error de memoria insuficiente.", Toast.LENGTH_SHORT).show();
                }else if (error instanceof AuthFailureError){
                    updateErrors("5","Servidor no pudo encontrar la solicitud autenticada.","0");
                    //Toast.makeText(Inicio_Activity.this, "Servidor no pudo encontrar la solicitud autenticada.",Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError || error.getCause() instanceof ServerError) {
                    updateErrors("6","El servidor NO responde..","0");
                    //Toast.makeText(Inicio_Activity.this, "El servidor NO responde.", Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError || error.getCause() instanceof SocketTimeoutException || error.getCause() instanceof ConnectTimeoutException || error.getCause() instanceof SocketException || (error.getCause().getMessage() != null
                        && error.getCause().getMessage().contains("Tiempo de conexiòn Agotado"))) {
                    updateErrors("7","Error de tiempo de espera de conexión","0");
                    //Toast.makeText(Inicio_Activity.this, "Error de tiempo de espera de conexión",Toast.LENGTH_SHORT).show();
                } else {
                    updateErrors("8","Un error desconocido ocurrió.","0");
                    //Toast.makeText(Inicio_Activity.this, "Un error desconocido ocurrió.",Toast.LENGTH_SHORT).show();
                }


            }
        });
        //mQueue2.add(request);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }

    private void jsonParseIndicador(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_indicador, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    data = new Data(context);
                    data.open();
                    JSONArray jsonArray = response.getJSONArray("indicador");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject indicador = jsonArray.getJSONObject(i);
                        Indicador IndicadorService   = new Indicador(indicador.getInt("id_indicador"),indicador.getString("nombre_indicador"),indicador.getString("descripcion_indicador"),indicador.getInt("total_subindicador"));
                        data.insertarIndicadorService(IndicadorService);
                    }
                    data.close();
                    Toast.makeText(Home_Activity.this,"Se Cargo Indicador.",Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Activity.this,"No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();
                //Log.i("ERROR:data_tpindicado"+error.getMessage()+"->"+error.getCause(),error.toString());

            }
        });
        //mQueue2.add(request);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }
    private void jsonParseLeyendaSubIndicador(){
        progressDialog.setTitle("SINCRONIZANDO...");
        progressDialog.setIcon(R.drawable.ic_action_reload_black_18);
        progressDialog.setMessage("Espere un Momento Por Favor!");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Log.i("MENSAJE", "ingreso Metodo jsonLeyendaIndicador");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_leyenda_service, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                cleanTableLeyendaSubIndicador();
                try {
                    data = new Data(context);
                    data.open();
                    JSONArray jsonArray = response.getJSONArray("leyenda_subindicador");
                    Log.i("MENSAJE", "jsonArray"+jsonArray);
                    Log.i("MENSAJE", "jsonArrayLength-"+jsonArray.length());
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject leyendasubindicador = jsonArray.getJSONObject(i);
                        LeyendaSubIndicador leyendaSubIndicadorService = new LeyendaSubIndicador(leyendasubindicador.getInt("id"),leyendasubindicador.getInt("id_subindicador"),leyendasubindicador.getInt("nro_subindicador"),leyendasubindicador.getString("nombre_nro_subindicador"));
                        data.insertarLeyendaSubIndicadorService(leyendaSubIndicadorService);
                        Log.i("MENSAJE", "jsonArrayLeyendaSubIndicador"+i);
                    }
                    data.close();
                    Log.i("MENSAJE", "Termino de Cargar Leyenda Subindicador");
                    Toast.makeText(Home_Activity.this,"Se Cargo Leyenda.",Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("MENSAJE", "No se pudo Sincronizar Leyenda Indicador");
                Toast.makeText(Home_Activity.this,"No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();
                //Log.i("ERROR:data_tpindicado"+error.getMessage()+"->"+error.getCause(),error.toString());

            }
        });
        //mQueue2.add(request);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }
    private void jsonParseSubIndicador() {
        Log.i("MENSAJE", "ingreso Metodo jsonIndicador");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_subindicador_service, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                cleanTableSubIndicador();
                try {
                    data = new Data(context);
                    data.open();
                    JSONArray jsonArray = response.getJSONArray("sub_indicador");
                    Log.i("MENSAJE", "jsonArray"+jsonArray);
                    Log.i("MENSAJE", "jsonArrayLength-"+jsonArray.length());
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject subindicador = jsonArray.getJSONObject(i);
                        SubIndicador subIndicadorService = new SubIndicador(subindicador.getInt("id_subindicador"),subindicador.getInt("id_indicador"),subindicador.getString("nombre_subindicador"),subindicador.getString("descripcion_subindicador"),subindicador.getString("fuente"),subindicador.getInt("total_graficos"));
                        data.insertarSubIndicadorService(subIndicadorService);
                        Log.i("MENSAJE", "jsonArraySubIndicador"+i);
                    }
                    data.close();
                    Log.i("MENSAJE", "Termino de Cargar Subindicador");
                    Toast.makeText(Home_Activity.this,"Se Cargo Grafico Subindicador.",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("MENSAJE", "No se pudo Sincronizar SubIndicador");
                Toast.makeText(Home_Activity.this,"No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();

            }
        });
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }
    private void jsonParseGraficoSubIndicador(){
        Log.i("MENSAJE", "ingreso Metodo jsonGraficoIndicador");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_grafico_service, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                cleanTableGraficoSubIndicador();
                try {
                    data = new Data(context);
                    data.open();
                    JSONArray jsonArray = response.getJSONArray("grafico_subindicador");
                    Log.i("MENSAJE", "jsonArray"+jsonArray);
                    Log.i("MENSAJE", "jsonArrayLength-"+jsonArray.length());
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject graficosubindicador = jsonArray.getJSONObject(i);
                        GraficoSubIndicador graficoSubIndicadorService = new GraficoSubIndicador(graficosubindicador.getInt("id"),graficosubindicador.getInt("id_subindicador"),graficosubindicador.getInt("id_indicador"),graficosubindicador.getInt("num_grafico"),graficosubindicador.getInt("modelo_grafico"),graficosubindicador.getString("titulo_grafico"));
                        data.insertarGraficoSubIndicadorService(graficoSubIndicadorService);
                        Log.i("MENSAJE", "jsonArrayGraficoSubIndicador"+i);
                    }
                    data.close();
                    Log.i("MENSAJE", "Termino de Cargar Grafico Subindicador");
                    Toast.makeText(Home_Activity.this,"Se Cargo Grafico",Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("MENSAJE", "No se pudo Sincronizar GraficoIndicador");
                Toast.makeText(Home_Activity.this,"No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();
                //Log.i("ERROR:data_tpindicado"+error.getMessage()+"->"+error.getCause(),error.toString());

            }
        });
        //mQueue2.add(request);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }
    private void jsonParseDataIndicador(){
        Log.i("MENSAJE", "ingreso Metodo jsonDataIndicador");
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_dataindicador_service, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                cleanTableDataIndicador();
                try {
                    data = new Data(context);
                    data.open();
                    JSONArray jsonArray = response.getJSONArray("data_indicador");
                    Log.i("MENSAJE", "jsonArray"+jsonArray);
                    Log.i("MENSAJE", "jsonArrayLength-"+jsonArray.length());
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject dataindicador = jsonArray.getJSONObject(i);
                        DataIndicador dataIndicadorService = new DataIndicador(dataindicador.getInt("id"),dataindicador.getInt("id_indicador"),dataindicador.getInt("id_subindicador"),dataindicador.getInt("nro_subindicador"),dataindicador.getInt("anio"),dataindicador.getString("ejex"),dataindicador.getString("ejey"),dataindicador.getInt("num_grafico"),Float.parseFloat(dataindicador.getString("dato")),dataindicador.getString("medida"));
                        data.insertarDataIndicadorService(dataIndicadorService);
                        Log.i("MENSAJE", "jsonArrayDataSubIndicador"+i);
                    }
                    data.close();
                    Log.i("MENSAJE", "Termino de Cargar Data Subindicador");
                    progressDialog.dismiss();
                    Toast.makeText(Home_Activity.this,"Se Cargo Data Indicador",Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.i("MENSAJE", "No se pudo Sincronizar DataIndicador");
                Toast.makeText(Home_Activity.this,"No se pudo Sincronizar Correctamente,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();
                //Log.i("ERROR:data_tpindicado"+error.getMessage()+"->"+error.getCause(),error.toString());

            }
        });
        //mQueue2.add(request);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }

    private void jsonParseIndicador2(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_indicador, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    data = new Data(context);
                    data.open();
                    JSONArray jsonArray = response.getJSONArray("indicador");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject indicador = jsonArray.getJSONObject(i);
                        ContentValues contentValues = new ContentValues(20);
                        contentValues.put(SQLConstantes.indicador_cp_nombre_indicador,indicador.getString("nombre_indicador"));
                        contentValues.put(SQLConstantes.indicador_cp_descripcion_indicador,indicador.getString("descripcion_indicador"));
                        contentValues.put(SQLConstantes.indicador_cp_total_subdindicador,indicador.getInt("total_subindicador"));
                        data.actualizarIndicadorService(indicador.getInt("id_indicador"),contentValues);
                    }
                    data.close();
                    Toast.makeText(Home_Activity.this,"Se Cargo Indicador.",Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Activity.this,"No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();
                //Log.i("ERROR:data_tpindicado"+error.getMessage()+"->"+error.getCause(),error.toString());

            }
        });
        //mQueue2.add(request);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }
    private void jsonParseSubIndicador2() {
        //init();
        //showPDialog1();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_subindicador_service, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    data = new Data(context);
                    data.open();
                    JSONArray jsonArray = response.getJSONArray("sub_indicador");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject subindicador = jsonArray.getJSONObject(i);
                        ContentValues contentValues = new ContentValues(20);
                        contentValues.put(SQLConstantes.subindicador_cp_id_indicador,subindicador.getInt("id_indicador"));
                        contentValues.put(SQLConstantes.subindicador_cp_nombre_subindicador,subindicador.getString("nombre_subindicador"));
                        contentValues.put(SQLConstantes.subindicador_cp_descripcion_subindicador,subindicador.getString("descripcion_subindicador"));
                        contentValues.put(SQLConstantes.subindicador_cp_fuente,subindicador.getString("fuente"));
                        contentValues.put(SQLConstantes.subindicador_cp_total_graficos,subindicador.getInt("total_graficos"));
                        data.actualizarSubIndicadorService(subindicador.getInt("id_subindicador"),contentValues);
                    }
                    data.close();
                    Toast.makeText(Home_Activity.this,"Se Cargo Grafico Subindicador.",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Activity.this,"No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();

            }
        });
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }
    private void jsonParseGraficoSubIndicador2(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_grafico_service, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    data = new Data(context);
                    data.open();
                    JSONArray jsonArray = response.getJSONArray("grafico_subindicador");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject graficosubindicador = jsonArray.getJSONObject(i);
                        ContentValues contentValues = new ContentValues(20);
                        contentValues.put(SQLConstantes.graficosubindicador_cp_id_subindicador,graficosubindicador.getInt("id_subindicador"));
                        contentValues.put(SQLConstantes.graficosubindicador_cp_id_indicador,graficosubindicador.getInt("id_indicador"));
                        contentValues.put(SQLConstantes.graficosubindicador_cp_id_num_grafico,graficosubindicador.getInt("num_grafico"));
                        contentValues.put(SQLConstantes.graficosubindicador_cp_modelo_grafico,graficosubindicador.getInt("modelo_grafico"));
                        contentValues.put(SQLConstantes.graficosubindicador_cp_titulo_grafico,graficosubindicador.getString("titulo_grafico"));
                        data.actualizarGraficoService(graficosubindicador.getInt("id"),contentValues);
                    }
                    data.close();
                    Toast.makeText(Home_Activity.this,"Se Cargo Grafico",Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Activity.this,"No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();
                //Log.i("ERROR:data_tpindicado"+error.getMessage()+"->"+error.getCause(),error.toString());

            }
        });
        //mQueue2.add(request);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }
    private void jsonParseLeyendaSubIndicador2(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_leyenda_service, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    data = new Data(context);
                    data.open();
                    JSONArray jsonArray = response.getJSONArray("leyenda_subindicador");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject leyendasubindicador = jsonArray.getJSONObject(i);
                        ContentValues contentValues = new ContentValues(20);
                        contentValues.put(SQLConstantes.leyendaindicador_cp_id_subindicador,leyendasubindicador.getInt("id_subindicador"));
                        contentValues.put(SQLConstantes.leyendaindicador_cp_nro_subindicador,leyendasubindicador.getInt("nro_subindicador"));
                        contentValues.put(SQLConstantes.leyendaindicador_cp_nombre_nro_subindicador,leyendasubindicador.getString("nombre_nro_subindicador"));
                        data.actualizarLeyendaService(leyendasubindicador.getInt("id"),contentValues);
                    }
                    data.close();
                    Toast.makeText(Home_Activity.this,"Se Cargo Leyenda.",Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Activity.this,"No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();
                //Log.i("ERROR:data_tpindicado"+error.getMessage()+"->"+error.getCause(),error.toString());

            }
        });
        //mQueue2.add(request);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }
    private void jsonParseDataIndicador2(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_dataindicador_service, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    data = new Data(context);
                    data.open();
                    JSONArray jsonArray = response.getJSONArray("data_indicador");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject dataindicador = jsonArray.getJSONObject(i);
                        ContentValues contentValues = new ContentValues(20);
                        contentValues.put(SQLConstantes.dataindicador_cp_id_indicador,dataindicador.getInt("id_indicador"));
                        contentValues.put(SQLConstantes.dataindicador_cp_id_subindicador,dataindicador.getInt("id_subindicador"));
                        contentValues.put(SQLConstantes.dataindicador_cp_nro_subindicador,dataindicador.getInt("nro_subindicador"));
                        contentValues.put(SQLConstantes.dataindicador_cp_anio,dataindicador.getInt("anio"));
                        contentValues.put(SQLConstantes.dataindicador_cp_ejex,dataindicador.getString("ejex"));
                        contentValues.put(SQLConstantes.dataindicador_cp_ejey,dataindicador.getString("ejey"));
                        contentValues.put(SQLConstantes.dataindicador_cp_num_grafico,dataindicador.getInt("num_grafico"));
                        contentValues.put(SQLConstantes.dataindicador_cp_dato,dataindicador.getString("dato"));
                        contentValues.put(SQLConstantes.dataindicador_cp_medida,dataindicador.getString("medida"));
                        data.actualizarDataIndicadorService(dataindicador.getInt("id"),contentValues);
                    }
                    data.close();
                    //progressDialog.dismiss();
                    Toast.makeText(Home_Activity.this,"Se Cargo Data Indicador (Actualizar).",Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
                Toast.makeText(Home_Activity.this,"No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();
                //Log.i("ERROR:data_tpindicado"+error.getMessage()+"->"+error.getCause(),error.toString());

            }
        });
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }




    private void updateSettings(){

        Calendar calendario = Calendar.getInstance();
        int yy = calendario.get(Calendar.YEAR);
        int mm = calendario.get(Calendar.MONTH)+1;
        int dd = calendario.get(Calendar.DAY_OF_MONTH);
        final String  fechahora = checkDigito(dd)+"/"+checkDigito(mm)+"/"+checkDigito(yy);
        try {
            data = new Data(getApplicationContext());
            data.open();
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(SQLConstantes.settings_fecha,fechahora);
            data.actualizarSettingService(contentValues);
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void updateErrors(String codigo,String descrip,String estado){
        try {
            data = new Data(getApplicationContext());
            data.open();
            ContentValues contentValues = new ContentValues(3);
            contentValues.put(SQLConstantes.errors_codigo,codigo);
            contentValues.put(SQLConstantes.errors_descripcion,descrip);
            contentValues.put(SQLConstantes.errors_estado,estado);
            data.actualizarErrorService(contentValues);
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void cleanTableIndicador(){
        try {
            Data data = new Data(context);
            data.open();
            data.deleteIndicador();
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void cleanTableSubIndicador(){
        try {
            Data data = new Data(context);
            data.open();
            data.deleteSubIndicador();
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void cleanTableDataIndicador(){
        try {
            Data data = new Data(context);
            data.open();
            data.deleteDataIndicador();
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void cleanTableGraficoSubIndicador(){
        try {
            Data data = new Data(context);
            data.open();
            data.deleteGraficoSubIndicador();
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void cleanTableLeyendaSubIndicador(){
        try {
            Data data = new Data(context);
            data.open();
            data.deleteLeyendaSubIndicador();
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
    private boolean estadoConexionWS(){
        boolean flag=false;

        try {
            data = new Data(getApplicationContext());
            data.open();
            ErrorService errorService = data.getAllError();
            estado = errorService.getEstado();
            if(estado.equals("1"))
            {flag=true;}
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    private class cargarDataApi1 extends AsyncTask<Void,Integer,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("SINCRONIZANDO.");
            progressDialog.setIcon(R.drawable.ic_action_reload_black_18);
            progressDialog.setMessage("Espere un Momento Por Favor!");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog. STYLE_HORIZONTAL);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(Void... strings) {
            int x = 1;
            int total = 8272;
            String result = null;
            String mensaje ="No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.....";
            Log.i("MENSAJE","PROCESO PARALELO");
            //SUBINDICADOR
            try {

                URL url = new URL(SQLConstantes.link_subindicador_service);
                Log.i("MENSAJE","URL"+url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(30000);
                Log.i("MENSAJE","HttpURLConnection"+urlConnection);
                Log.i("MENSAJE","HttpURLConnection.getResponseCode"+urlConnection.getResponseCode());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Log.i("MENSAJE","urlConnection.getInputStream()-"+urlConnection.getInputStream());
                Log.i("MENSAJE","InputStream"+in);
                result = inputStreamToString(in);
                Log.i("MENSAJE","RESULT"+result);
                data = new Data(context);
                data.open();
                JSONObject jsonObject = new JSONObject(result);
                Log.i("MENSAJE","JSONObject"+jsonObject);
                JSONArray jsonArray = jsonObject.getJSONArray("sub_indicador");
                Log.i("MENSAJE","jsonArray"+jsonArray.length());
                if(jsonArray.length()>0) {
                    cleanTableSubIndicador();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject subindicador = jsonArray.getJSONObject(i);
                        SubIndicador subIndicadorService = new SubIndicador(subindicador.getInt("id_subindicador"),subindicador.getInt("id_indicador"),subindicador.getString("nombre_subindicador"),subindicador.getString("descripcion_subindicador"),subindicador.getString("fuente"),subindicador.getInt("total_graficos"));
                        data.insertarSubIndicadorService(subIndicadorService);
                        publishProgress((int) ((x / (float) total) * 100));
                        //publishProgress(x,(int)Math.floor(x/7738));
                        x++;
                        Log.i("MENSAJE", "jsonArray" + x);
                    }
                    data.close();
                    urlConnection.disconnect();
                    //mensaje = "Se Sincronizo Correctamente!";
                }
                else {mensaje = "No existen Datos en el Servicio";}
            } catch (Exception e) {
                Log.i("MENSAJE",""+e);
                e.printStackTrace();
            }
            //GRAFICO
            try {

                URL url = new URL(SQLConstantes.link_grafico_service);
                Log.i("MENSAJE","URL"+url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(30000);
                Log.i("MENSAJE","HttpURLConnection"+urlConnection);
                Log.i("MENSAJE","HttpURLConnection.getResponseCode"+urlConnection.getResponseCode());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Log.i("MENSAJE","urlConnection.getInputStream()-"+urlConnection.getInputStream());
                Log.i("MENSAJE","InputStream"+in);
                result = inputStreamToString(in);
                Log.i("MENSAJE","RESULT"+result);
                data = new Data(context);
                data.open();
                JSONObject jsonObject = new JSONObject(result);
                Log.i("MENSAJE","JSONObject"+jsonObject);
                JSONArray jsonArray = jsonObject.getJSONArray("grafico_subindicador");
                Log.i("MENSAJE","jsonArray"+jsonArray.length());
                if(jsonArray.length()>0) {
                    cleanTableGraficoSubIndicador();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject graficosubindicador = jsonArray.getJSONObject(i);
                        GraficoSubIndicador graficoSubIndicadorService = new GraficoSubIndicador(graficosubindicador.getInt("id"),graficosubindicador.getInt("id_subindicador"),graficosubindicador.getInt("id_indicador"),graficosubindicador.getInt("num_grafico"),graficosubindicador.getInt("modelo_grafico"),graficosubindicador.getString("titulo_grafico"));
                        data.insertarGraficoSubIndicadorService(graficoSubIndicadorService);
                        publishProgress((int) ((x / (float) total) * 100));
                        //publishProgress(x,(int)Math.floor(x/7738));
                        x++;
                        Log.i("MENSAJE", "jsonArray" + x);
                    }
                    data.close();
                    urlConnection.disconnect();
                    //mensaje = "Se Sincronizo Correctamente!";
                }
                else {mensaje = "No existen Datos en el Servicio";}
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("MENSAJE",""+e);
            }
            //LEYENDA
            try {

                URL url = new URL(SQLConstantes.link_leyenda_service);
                Log.i("MENSAJE","URL"+url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(30000);
                Log.i("MENSAJE","HttpURLConnection"+urlConnection);
                Log.i("MENSAJE","HttpURLConnection.getResponseCode"+urlConnection.getResponseCode());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Log.i("MENSAJE","urlConnection.getInputStream()-"+urlConnection.getInputStream());
                Log.i("MENSAJE","InputStream"+in);
                result = inputStreamToString(in);
                Log.i("MENSAJE","RESULT"+result);
                data = new Data(context);
                data.open();
                JSONObject jsonObject = new JSONObject(result);
                Log.i("MENSAJE","JSONObject"+jsonObject);
                JSONArray jsonArray = jsonObject.getJSONArray("leyenda_subindicador");
                Log.i("MENSAJE","jsonArray"+jsonArray.length());
                if(jsonArray.length()>0) {
                    cleanTableLeyendaSubIndicador();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject leyendasubindicador = jsonArray.getJSONObject(i);
                        LeyendaSubIndicador leyendaSubIndicadorService = new LeyendaSubIndicador(leyendasubindicador.getInt("id"),leyendasubindicador.getInt("id_subindicador"),leyendasubindicador.getInt("nro_subindicador"),leyendasubindicador.getString("nombre_nro_subindicador"));
                        data.insertarLeyendaSubIndicadorService(leyendaSubIndicadorService);
                        publishProgress((int) ((x / (float) total) * 100));
                        //publishProgress(x,(int)Math.floor(x/7738));
                        x++;
                        Log.i("MENSAJE", "jsonArray" + x);
                    }
                    data.close();
                    urlConnection.disconnect();
                    //mensaje = "Se Sincronizo Correctamente!";
                }
                else {mensaje = "No existen Datos en el Servicio";}
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("MENSAJE",""+e);
            }
            //DATA
            try {

                URL url = new URL(SQLConstantes.link_dataindicador_service);
                Log.i("MENSAJE","URL"+url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(50000);
                Log.i("MENSAJE","HttpURLConnection"+urlConnection);
                Log.i("MENSAJE","HttpURLConnection.getResponseCode"+urlConnection.getResponseCode());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Log.i("MENSAJE","urlConnection.getInputStream()-"+urlConnection.getInputStream());
                Log.i("MENSAJE","InputStream"+in);
                result = inputStreamToString(in);
                Log.i("MENSAJE","RESULT"+result);
                data = new Data(context);
                data.open();
                JSONObject jsonObject = new JSONObject(result);
                Log.i("MENSAJE","JSONObject"+jsonObject);
                JSONArray jsonArray = jsonObject.getJSONArray("data_indicador");
                Log.i("MENSAJE","jsonArray"+jsonArray.length());
                if(jsonArray.length()>0) {
                    cleanTableDataIndicador();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject dataindicador = jsonArray.getJSONObject(i);
                        DataIndicador dataIndicadorService = new DataIndicador(dataindicador.getInt("id"), dataindicador.getInt("id_indicador"), dataindicador.getInt("id_subindicador"), dataindicador.getInt("nro_subindicador"), dataindicador.getInt("anio"), dataindicador.getString("ejex"), dataindicador.getString("ejey"), dataindicador.getInt("num_grafico"), Float.parseFloat(dataindicador.getString("dato")), dataindicador.getString("medida"));
                        data.insertarDataIndicadorService(dataIndicadorService);
                        publishProgress((int) ((x / (float) total) * 100));
                        //publishProgress(x,(int)Math.floor(x/7738));
                        x++;
                        Log.i("MENSAJE", "jsonArray" + x);
                    }
                    data.close();
                    urlConnection.disconnect();
                    mensaje = "Se Sincronizo Correctamente!";
                }
                else {mensaje = "No existen Datos en el Servicio";}
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("MENSAJE",""+e);
            }
            return mensaje;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Toast.makeText(Home_Activity.this,""+s,Toast.LENGTH_LONG).show();

        }
    }

    private class cargarDataApi2 extends AsyncTask<Void,Integer,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("SINCRONIZANDO.");
            progressDialog.setIcon(R.drawable.ic_action_reload_black_18);
            progressDialog.setMessage("Espere un Momento Por Favor!");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog. STYLE_HORIZONTAL);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(Void... strings) {
            int x = 1;
            int total = 8272;
            String result = null;
            String mensaje ="No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.....";
            Log.i("MENSAJE","PROCESO PARALELO");
            //SUBINDICADOR
            try {

                URL url = new URL(SQLConstantes.link_subindicador_service);
                Log.i("MENSAJE","URL"+url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(10000);
                try {
                Log.i("MENSAJE","HttpURLConnection"+urlConnection);
                Log.i("MENSAJE","HttpURLConnection.getResponseCode"+urlConnection.getResponseCode());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Log.i("MENSAJE","urlConnection.getInputStream()-"+urlConnection.getInputStream());
                Log.i("MENSAJE","InputStream"+in);
                result = inputStreamToString(in);
                Log.i("MENSAJE","RESULT"+result);
                data = new Data(context);
                data.open();
                JSONObject jsonObject = new JSONObject(result);
                Log.i("MENSAJE","JSONObject"+jsonObject);
                JSONArray jsonArray = jsonObject.getJSONArray("sub_indicador");
                Log.i("MENSAJE","jsonArray"+jsonArray.length());
                if(jsonArray.length()>0) {
                    cleanTableSubIndicador();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject subindicador = jsonArray.getJSONObject(i);
                        SubIndicador subIndicadorService = new SubIndicador(subindicador.getInt("id_subindicador"),subindicador.getInt("id_indicador"),subindicador.getString("nombre_subindicador"),subindicador.getString("descripcion_subindicador"),subindicador.getString("fuente"),subindicador.getInt("total_graficos"));
                        data.insertarSubIndicadorService(subIndicadorService);
                        publishProgress((int) ((x / (float) total) * 100));
                        x++;
                        Log.i("MENSAJE", "jsonArray" + x);
                    }
                    data.close();
                    urlConnection.disconnect();
                    //mensaje = "Se Sincronizo Correctamente!";
                }
                else {mensaje = "No existen Datos en el Servicio";}
                }finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.i("MENSAJE",""+e);
                e.printStackTrace();
            }
            //GRAFICO
            try {

                URL url = new URL(SQLConstantes.link_grafico_service);
                Log.i("MENSAJE","URL"+url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(10000);
                try {


                Log.i("MENSAJE","HttpURLConnection"+urlConnection);
                Log.i("MENSAJE","HttpURLConnection.getResponseCode"+urlConnection.getResponseCode());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Log.i("MENSAJE","urlConnection.getInputStream()-"+urlConnection.getInputStream());
                Log.i("MENSAJE","InputStream"+in);
                result = inputStreamToString(in);
                Log.i("MENSAJE","RESULT"+result);
                data = new Data(context);
                data.open();
                JSONObject jsonObject = new JSONObject(result);
                Log.i("MENSAJE","JSONObject"+jsonObject);
                JSONArray jsonArray = jsonObject.getJSONArray("grafico_subindicador");
                Log.i("MENSAJE","jsonArray"+jsonArray.length());
                if(jsonArray.length()>0) {
                    cleanTableGraficoSubIndicador();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject graficosubindicador = jsonArray.getJSONObject(i);
                        GraficoSubIndicador graficoSubIndicadorService = new GraficoSubIndicador(graficosubindicador.getInt("id"),graficosubindicador.getInt("id_subindicador"),graficosubindicador.getInt("id_indicador"),graficosubindicador.getInt("num_grafico"),graficosubindicador.getInt("modelo_grafico"),graficosubindicador.getString("titulo_grafico"));
                        data.insertarGraficoSubIndicadorService(graficoSubIndicadorService);
                        publishProgress((int) ((x / (float) total) * 100));
                        //publishProgress(x,(int)Math.floor(x/7738));
                        x++;
                        Log.i("MENSAJE", "jsonArray" + x);
                    }
                    data.close();
                    urlConnection.disconnect();
                    //mensaje = "Se Sincronizo Correctamente!";
                }
                else {mensaje = "No existen Datos en el Servicio";}
                }finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("MENSAJE",""+e);
            }
            //LEYENDA
            try {

                URL url = new URL(SQLConstantes.link_leyenda_service);
                Log.i("MENSAJE","URL"+url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(10000);
                try {


                Log.i("MENSAJE","HttpURLConnection"+urlConnection);
                Log.i("MENSAJE","HttpURLConnection.getResponseCode"+urlConnection.getResponseCode());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Log.i("MENSAJE","urlConnection.getInputStream()-"+urlConnection.getInputStream());
                Log.i("MENSAJE","InputStream"+in);
                result = inputStreamToString(in);
                Log.i("MENSAJE","RESULT"+result);
                data = new Data(context);
                data.open();
                JSONObject jsonObject = new JSONObject(result);
                Log.i("MENSAJE","JSONObject"+jsonObject);
                JSONArray jsonArray = jsonObject.getJSONArray("leyenda_subindicador");
                Log.i("MENSAJE","jsonArray"+jsonArray.length());
                if(jsonArray.length()>0) {
                    cleanTableLeyendaSubIndicador();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject leyendasubindicador = jsonArray.getJSONObject(i);
                        LeyendaSubIndicador leyendaSubIndicadorService = new LeyendaSubIndicador(leyendasubindicador.getInt("id"),leyendasubindicador.getInt("id_subindicador"),leyendasubindicador.getInt("nro_subindicador"),leyendasubindicador.getString("nombre_nro_subindicador"));
                        data.insertarLeyendaSubIndicadorService(leyendaSubIndicadorService);
                        publishProgress((int) ((x / (float) total) * 100));
                        //publishProgress(x,(int)Math.floor(x/7738));
                        x++;
                        Log.i("MENSAJE", "jsonArray" + x);
                    }
                    data.close();
                    urlConnection.disconnect();
                    //mensaje = "Se Sincronizo Correctamente!";
                }
                else {mensaje = "No existen Datos en el Servicio";}
                }finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("MENSAJE",""+e);
            }
            //DATA
            try {

                URL url = new URL(SQLConstantes.link_dataindicador_service);
                Log.i("MENSAJE","URL"+url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(20000);
                try {
                Log.i("MENSAJE","HttpURLConnection"+urlConnection);
                Log.i("MENSAJE","HttpURLConnection.getResponseCode"+urlConnection.getResponseCode());
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                Log.i("MENSAJE","urlConnection.getInputStream()-"+urlConnection.getInputStream());
                Log.i("MENSAJE","InputStream"+in);
                result = inputStreamToString(in);
                Log.i("MENSAJE","RESULT"+result);
                data = new Data(context);
                data.open();
                JSONObject jsonObject = new JSONObject(result);
                Log.i("MENSAJE","JSONObject"+jsonObject);
                JSONArray jsonArray = jsonObject.getJSONArray("data_indicador");
                Log.i("MENSAJE","jsonArray"+jsonArray.length());
                if(jsonArray.length()>0) {
                    cleanTableDataIndicador();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject dataindicador = jsonArray.getJSONObject(i);
                        DataIndicador dataIndicadorService = new DataIndicador(dataindicador.getInt("id"), dataindicador.getInt("id_indicador"), dataindicador.getInt("id_subindicador"), dataindicador.getInt("nro_subindicador"), dataindicador.getInt("anio"), dataindicador.getString("ejex"), dataindicador.getString("ejey"), dataindicador.getInt("num_grafico"), Float.parseFloat(dataindicador.getString("dato")), dataindicador.getString("medida"));
                        data.insertarDataIndicadorService(dataIndicadorService);
                        publishProgress((int) ((x / (float) total) * 100));
                        //publishProgress(x,(int)Math.floor(x/7738));
                        x++;
                        Log.i("MENSAJE", "jsonArray" + x);
                    }
                    data.close();
                    urlConnection.disconnect();
                    mensaje = "Se Sincronizo Correctamente!";
                }
                else {mensaje = "No existen Datos en el Servicio";}
                }finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("MENSAJE",""+e);
            }
            return mensaje;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Toast.makeText(Home_Activity.this,""+s,Toast.LENGTH_LONG).show();

        }
    }


    private String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader rd = new BufferedReader(isr);
        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer.toString();
    }




    private void insertarDatos(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_insercion+"id_indicador=110&nombre_indicador=30&descripcion_indicador=40&total_subindicador=45", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(Home_Activity.this,"Exito al guardar",Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Activity.this,"Error al Guardar",Toast.LENGTH_SHORT).show();

            }
        });
        //mQueue2.add(request);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }


    private void insertarDatos2(){
        jsonParams = new JSONObject();
        try {
            jsonParams.put("id_indicador", "500");
            jsonParams.put("nombre_indicador", "prueba");
            jsonParams.put("descripcion_indicador","pruebas");
            jsonParams.put("total_subindicador", "100");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("Datos:",""+jsonParams);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, SQLConstantes.link_insercion2,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(Home_Activity.this,"Exito al guardar",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Activity.this,"Error al Guardar",Toast.LENGTH_SHORT).show();

            }
        });
        //mQueue2.add(request);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }




    public void  crearjson(){
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("id_indicador", "500");
            jsonParams.put("nombre_indicador", "prueba");
            jsonParams.put("descripcion_indicador","pruebas");
            jsonParams.put("total_subindicador", "100");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Datos:",""+jsonParams);

    }

    public void  crearjsonArray(){
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("id_indicador", "500");
            jsonParams.put("nombre_indicador", "prueba");
            jsonParams.put("descripcion_indicador","pruebas");
            jsonParams.put("total_subindicador", "100");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Datos:",""+jsonParams);

    }

    private void insertDataOnline() {

        StringRequest request = new StringRequest(Request.Method.POST, SQLConstantes.link_insercion2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Home_Activity.this,"Exito al guardar",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home_Activity.this,"Error al guardar",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String,String>();
                parameters.put("id_indicador", "500");
                parameters.put("nombre_indicador", "prueba");
                parameters.put("descripcion_indicador","pruebas");
                parameters.put("total_subindicador", "100");
                return parameters;
            }
        };

        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);

    }

}
