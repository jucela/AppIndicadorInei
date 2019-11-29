package app.inei.appindicadorinei.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Calendar;

import app.inei.appindicadorinei.R;
import app.inei.appindicadorinei.modelo.DAO.Data;
import app.inei.appindicadorinei.modelo.DAO.SQLConstantes;
import app.inei.appindicadorinei.modelo.DAO.VolleySingleton;
import app.inei.appindicadorinei.modelo.pojos.DataIndicador;
import app.inei.appindicadorinei.modelo.pojos.ErrorService;
import app.inei.appindicadorinei.modelo.pojos.GraficoSubIndicador;
import app.inei.appindicadorinei.modelo.pojos.Indicador;
import app.inei.appindicadorinei.modelo.pojos.LeyendaSubIndicador;
import app.inei.appindicadorinei.modelo.pojos.SettingService;
import app.inei.appindicadorinei.modelo.pojos.SubIndicador;

public class Inicio_Activity extends AppCompatActivity {
    VideoView videoView;
    Data data;
    Context context;
    String fecha;
    String estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        videoView=(VideoView) findViewById(R.id.videoIntro);
        Uri video= Uri.parse("android.resource://"+getPackageName()+"/"+ R.raw.intro);
        videoView.setVideoURI(video);
        videoView.start();
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        videoView.startAnimation(myAnim);

        //new MyAsyncTask().execute(0);
        new cargadoInicio().execute();
    }

    public class MyAsyncTask extends AsyncTask<Integer,Integer,String> {
        /*En este método van todas aquellas instrucciones que se ejecutarán antes de iniciar la tarea en segundo plano.
          Normalmente es la inicialización de variables, objetos y la preparación de componentes de la interfaz.*/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Integer... integers) {
            String mensaje = "";
//            if(estadoConexionWS()==false || estadoConexionWS()==true)
//            {   Log.i("MENSAJE", "Verificando conexión con Web Service");
//                verificarConexionWS();
//                try {Thread.sleep(5000);}
//                catch (InterruptedException e)
//                {e.printStackTrace();}
//                if(estadoConexionWS()==true)
//                {
//                    Log.i("MENSAJE", "Conexión exitosa con Web Service.");
//                    if(estadoSetting()==true)
//                    {
//                        Log.i("MENSAJE", "Mismo día,Cargara data establecida.");
//                        try {Thread.sleep(5000);}
//                        catch (InterruptedException e)
//                        {e.printStackTrace();}
//                    }
//                    else{  Log.i("MENSAJE", "Dìa Diferente,Cargara data de Web Service.");
//
//                        cleanTableSubIndicador();
//                        jsonParseSubIndicador();
//                        updateSettings();
//                        try {Thread.sleep(5000);}
//                        catch (InterruptedException e)
//                        {e.printStackTrace();}
//                    }
//                }
//                else { try {Thread.sleep(5000);}
//                catch (InterruptedException e)
//                {e.printStackTrace();}
//                    Log.i("MENSAJE", "No se pudo establecer conexión con webservice");
//                }
//            }
//            else
//            {
//                Log.i("MENSAJE:", "La fecha esta vacia");
//            }
            return mensaje;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String mensaje) {
            super.onPostExecute(mensaje);
            Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
            startActivity(intent);
            finish();
        }

    }

    public class cargadoInicio extends AsyncTask<Integer,Integer,String> {
        /*En este método van todas aquellas instrucciones que se ejecutarán antes de iniciar la tarea en segundo plano.
          Normalmente es la inicialización de variables, objetos y la preparación de componentes de la interfaz.*/
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Integer... integers) {
            String mensaje = "";
            try {Thread.sleep(10000);}
                catch (InterruptedException e)
                {e.printStackTrace();}

            return mensaje;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String mensaje) {
            super.onPostExecute(mensaje);
            try {Thread.sleep(1000);}
            catch (InterruptedException e)
            {e.printStackTrace();}
            Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
            startActivity(intent);
            finish();
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
                    data = new Data(getApplicationContext());
                    JSONArray jsonArray = response.getJSONArray("indicador");
                    for (int i=0;i<jsonArray.length();i++)
                    {   data = new Data(context);
                        data.open();
                        JSONObject indicador = jsonArray.getJSONObject(i);
                        String id_indicador          = indicador.getString("id_indicador");
                        String nombre_indicador      = indicador.getString("nombre_indicador");
                        String descripcion_indicador = indicador.getString("descripcion_indicador");
                        String total_subindicador    = indicador.getString("total_subindicador");
                        Indicador IndicadorService   = new Indicador(Integer.parseInt(id_indicador),nombre_indicador,descripcion_indicador,Integer.parseInt(total_subindicador));
                        data.insertarIndicadorService(IndicadorService);
                    }
                    data.close();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Inicio_Activity.this,"No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();
                //Log.i("ERROR:data_tpindicado"+error.getMessage()+"->"+error.getCause(),error.toString());

            }
        });
        //mQueue2.add(request);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }
    private void jsonParseSubIndicador(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_subindicador, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    data = new Data(getApplicationContext());
                    JSONArray jsonArray = response.getJSONArray("subindicador");
                    for (int i=0;i<jsonArray.length();i++)
                    {   data = new Data(context);
                        data.open();
                        JSONObject subindicador = jsonArray.getJSONObject(i);
                        String id_subindicador          = subindicador.getString("id_subindicador");
                        String id_indicador             = subindicador.getString("id_indicador");
                        String nombre_subindicador      = subindicador.getString("nombre_subindicador");
                        String descripcion_subindicador = subindicador.getString("descripcion_subindicador");
                        String fuente                   = subindicador.getString("fuente");
                        String total_graficos           = subindicador.getString("total_graficos");
                        SubIndicador subIndicadorService = new SubIndicador(Integer.parseInt(id_subindicador),Integer.parseInt(id_indicador),nombre_subindicador,descripcion_subindicador,fuente,Integer.parseInt(total_graficos));
                        data.insertarSubIndicadorService(subIndicadorService);
                    }
                    data.close();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Inicio_Activity.this,"No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();
                //Log.i("ERROR:data_tpindicado"+error.getMessage()+"->"+error.getCause(),error.toString());

            }
        });
        //mQueue2.add(request);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }
    private void jsonParseDataIndicador(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_dataindicador_service, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    data = new Data(getApplicationContext());
                    JSONArray jsonArray = response.getJSONArray("dataindicador");
                    for (int i=0;i<jsonArray.length();i++)
                    {   data = new Data(context);
                        data.open();
                        JSONObject dataindicador = jsonArray.getJSONObject(i);
                        int id                = dataindicador.getInt("id");
                        String id_indicador      = dataindicador.getString("id_indicador");
                        String id_subindicador   = dataindicador.getString("id_subindicador");
                        String nro_subindicador  = dataindicador.getString("nro_subindicador");
                        String anio              = dataindicador.getString("anio");
                        String ejex              = dataindicador.getString("ejex");
                        String ejey              = dataindicador.getString("ejey");
                        String num_grafico       = dataindicador.getString("num_grafico");
                        String dato              = dataindicador.getString("dato");
                        String medida            = dataindicador.getString("medida");
                        DataIndicador dataIndicadorService = new DataIndicador(id, Integer.parseInt(id_indicador),Integer.parseInt(id_subindicador),Integer.parseInt(nro_subindicador),Integer.parseInt(anio),ejex,ejey,Integer.parseInt(num_grafico),Float.parseFloat(dato),medida);
                        data.insertarDataIndicadorService(dataIndicadorService);
                    }
                    data.close();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Inicio_Activity.this,"No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();
                //Log.i("ERROR:data_tpindicado"+error.getMessage()+"->"+error.getCause(),error.toString());

            }
        });
        //mQueue2.add(request);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }
    private void jsonParseGraficoSubIndicador(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_grafico_service, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    data = new Data(getApplicationContext());
                    JSONArray jsonArray = response.getJSONArray("graficosubindicador");
                    for (int i=0;i<jsonArray.length();i++)
                    {   data = new Data(context);
                        data.open();
                        JSONObject leyendasubindicador = jsonArray.getJSONObject(i);
                        String id              = leyendasubindicador.getString("id");
                        String id_subindicador = leyendasubindicador.getString("id_subindicador");
                        String id_indicador    = leyendasubindicador.getString("id_indicador");
                        String num_grafico     = leyendasubindicador.getString("num_grafico");
                        String modelo_grafico  = leyendasubindicador.getString("modelo_grafico");
                        String titulo_grafico  = leyendasubindicador.getString("titulo_grafico");
                        GraficoSubIndicador graficoSubIndicadorService = new GraficoSubIndicador(Integer.parseInt(id),Integer.parseInt(id_subindicador),Integer.parseInt(id_indicador),Integer.parseInt(num_grafico),Integer.parseInt(modelo_grafico),titulo_grafico);
                        data.insertarGraficoSubIndicadorService(graficoSubIndicadorService);
                    }
                    data.close();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Inicio_Activity.this,"No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();
                //Log.i("ERROR:data_tpindicado"+error.getMessage()+"->"+error.getCause(),error.toString());

            }
        });
        //mQueue2.add(request);
        VolleySingleton.getInstanciaVolley(getApplicationContext()).addToRequestQueue(request);
    }
    private void jsonParseLeyendaSubIndicador(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SQLConstantes.link_leyenda_service, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    data = new Data(getApplicationContext());
                    JSONArray jsonArray = response.getJSONArray("leyendasubindicador");
                    for (int i=0;i<jsonArray.length();i++)
                    {   data = new Data(context);
                        data.open();
                        JSONObject leyendasubindicador = jsonArray.getJSONObject(i);
                        String id                      = leyendasubindicador.getString("id");
                        String id_subindicador         = leyendasubindicador.getString("id_subindicador");
                        String nro_subindicador        = leyendasubindicador.getString("nro_subindicador");
                        String nombre_nro_subindicador = leyendasubindicador.getString("nombre_nro_subindicador");
                        LeyendaSubIndicador leyendaSubIndicadorService = new LeyendaSubIndicador(Integer.parseInt(id),Integer.parseInt(id_subindicador),Integer.parseInt(nro_subindicador),nombre_nro_subindicador);
                        data.insertarLeyendaSubIndicadorService(leyendaSubIndicadorService);
                    }
                    data.close();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Inicio_Activity.this,"No se pudo Sincronizar,Vuelva a intentarlo en unos minutos.",Toast.LENGTH_LONG).show();
                //Log.i("ERROR:data_tpindicado"+error.getMessage()+"->"+error.getCause(),error.toString());

            }
        });
        //mQueue2.add(request);
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
    private boolean estadoSetting(){
        boolean flag=false;
        Calendar calendario = Calendar.getInstance();
        int yy = calendario.get(Calendar.YEAR);
        int mm = calendario.get(Calendar.MONTH)+1;
        int dd = calendario.get(Calendar.DAY_OF_MONTH);
        final String  fechahora = checkDigito(dd)+"/"+checkDigito(mm)+"/"+checkDigito(yy);
        try {
            data = new Data(getApplicationContext());
            data.open();
            SettingService settingService = data.getAllSetting();
            fecha = settingService.getFecha();
            if(fecha.equals(fechahora))
            {flag=true;}
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
