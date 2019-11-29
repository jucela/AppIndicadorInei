package app.inei.appindicadorinei.modelo.DAO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import app.inei.appindicadorinei.modelo.pojos.DataIndicador;
import app.inei.appindicadorinei.modelo.pojos.ErrorService;
import app.inei.appindicadorinei.modelo.pojos.GraficoSubIndicador;
import app.inei.appindicadorinei.modelo.pojos.Indicador;
import app.inei.appindicadorinei.modelo.pojos.LeyendaSubIndicador;
import app.inei.appindicadorinei.modelo.pojos.SettingService;
import app.inei.appindicadorinei.modelo.pojos.SubIndicador;

public class Data {
    Context contexto;
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    public  Data (Context context) throws IOException {
        this.contexto = context;
        sqLiteOpenHelper = new DataBaseHelper(contexto);
        createDataBase();
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(!dbExist){
            sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
            sqLiteDatabase.close();
            try{
                copyDataBase();
                sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
                //sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_INDICADOR);
                //sqLiteDatabase.close();
               }catch (IOException e){
                throw new Error("Error: Copiando base de datos");
            }
        }
    }

    public void copyDataBase() throws IOException{
        InputStream myInput = contexto.getAssets().open(SQLConstantes.DB_NAME);
        String outFileName = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) != -1){
            if (length > 0){
                myOutput.write(buffer,0,length);
            }
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();

    }

    public void open() throws SQLException {
        String myPath = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close(){
        if(sqLiteDatabase != null){
            sqLiteDatabase.close();
        }
    }

    public boolean checkDataBase(){
        //sqLiteDatabase =null;
        try{
            String myPath = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
            sqLiteDatabase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
            //sqLiteDatabase.close();
        }catch (Exception e){
            File dbFile = new File(SQLConstantes.DB_PATH + SQLConstantes.DB_NAME);
            return dbFile.exists();
        }
        if (sqLiteDatabase != null) sqLiteDatabase.close();

        return sqLiteDatabase != null ? true : false;
    }

    /*METODOS DE CAPTURA*/
    public Indicador getIndicador(int id){
        Indicador indicador = null;
        String argumento = Integer.toString(id);
        String[] whereArgs = new String[]{argumento};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tb_indicador,SQLConstantes.COLUMNAS_TB_INDICADOR,SQLConstantes.WHERE_CLAUSE_ID_INDICADOR,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                indicador = new Indicador();
                indicador.setId_indicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.indicador_cp_id_indicador)));
                indicador.setNombre_indicador(cursor.getString(cursor.getColumnIndex(SQLConstantes.indicador_cp_nombre_indicador)));
                indicador.setDescripcion_indicador(cursor.getString(cursor.getColumnIndex(SQLConstantes.indicador_cp_descripcion_indicador)));
                indicador.setTotal_subdindicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.indicador_cp_total_subdindicador)));

            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return indicador;
    }
    public ArrayList<Indicador> getAllIndicador(){
        ArrayList<Indicador> indicadores = new ArrayList<>();
        //String argumento = Integer.toString(id);
        //String[] whereArgs =  new String[]{argumento};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.rawQuery("SELECT id_indicador,nombre_indicador,descripcion_indicador,total_subindicador FROM indicador ",null);
            while(cursor.moveToNext()){
                Indicador dato = new Indicador();
                dato.setId_indicador(cursor.getInt(cursor.getColumnIndex("id_indicador")));
                dato.setNombre_indicador(cursor.getString(cursor.getColumnIndex("nombre_indicador")));
                dato.setDescripcion_indicador(cursor.getString(cursor.getColumnIndex("descripcion_indicador")));
                dato.setTotal_subdindicador(cursor.getInt(cursor.getColumnIndex("total_subindicador")));
                indicadores.add(dato);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return indicadores;

    }

    public SubIndicador getSubIndicador(int id){
        SubIndicador subindicador = null;
        String argumento = Integer.toString(id);
        String[] whereArgs = new String[]{argumento};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tb_subindicador,SQLConstantes.COLUMNAS_TB_SUBINDICADOR,SQLConstantes.WHERE_CLAUSE_ID_SUBINDICADOR,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                subindicador = new SubIndicador();
                subindicador.setId_subindicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.subindicador_cp_id_subindicador)));
                subindicador.setId_indicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.subindicador_cp_id_indicador)));
                subindicador.setNombre_subindicador(cursor.getString(cursor.getColumnIndex(SQLConstantes.subindicador_cp_nombre_subindicador)));
                subindicador.setDescripcion_subindicador(cursor.getString(cursor.getColumnIndex(SQLConstantes.subindicador_cp_descripcion_subindicador)));
                subindicador.setFuente(cursor.getString(cursor.getColumnIndex(SQLConstantes.subindicador_cp_fuente)));
                subindicador.setTotal_graficos(cursor.getInt(cursor.getColumnIndex(SQLConstantes.subindicador_cp_total_graficos)));


            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return subindicador;
    }

    public ArrayList<SubIndicador> getAllSubIndicador(){
        ArrayList<SubIndicador> subindicadores= new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.rawQuery("SELECT id_subindicador,id_indicador,nombre_subindicador,descripcion_subindicador,fuente,total_graficos FROM sub_indicador",null);
            while(cursor.moveToNext()){
                SubIndicador dato = new SubIndicador();
                dato.setId_subindicador(cursor.getInt(cursor.getColumnIndex("id_subindicador")));
                dato.setId_indicador(cursor.getInt(cursor.getColumnIndex("id_indicador")));
                dato.setNombre_subindicador(cursor.getString(cursor.getColumnIndex("nombre_subindicador")));
                dato.setDescripcion_subindicador(cursor.getString(cursor.getColumnIndex("descripcion_subindicador")));
                dato.setFuente(cursor.getString(cursor.getColumnIndex("fuente")));
                dato.setTotal_graficos(cursor.getInt(cursor.getColumnIndex("total_graficos")));
                subindicadores.add(dato);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return subindicadores;

    }

    public ArrayList<SubIndicador> getSubIndicadorXId(int id){
        ArrayList<SubIndicador> subindicadores= new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.rawQuery("SELECT id_subindicador,id_indicador,nombre_subindicador,descripcion_subindicador,fuente,total_graficos FROM sub_indicador where id_indicador = "+id+"  ",null);
            while(cursor.moveToNext()){
                SubIndicador dato = new SubIndicador();
                dato.setId_subindicador(cursor.getInt(cursor.getColumnIndex("id_subindicador")));
                dato.setId_indicador(cursor.getInt(cursor.getColumnIndex("id_indicador")));
                dato.setNombre_subindicador(cursor.getString(cursor.getColumnIndex("nombre_subindicador")));
                dato.setDescripcion_subindicador(cursor.getString(cursor.getColumnIndex("descripcion_subindicador")));
                dato.setFuente(cursor.getString(cursor.getColumnIndex("fuente")));
                dato.setTotal_graficos(cursor.getInt(cursor.getColumnIndex("total_graficos")));
                subindicadores.add(dato);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return subindicadores;

    }


    public GraficoSubIndicador getNroGraficoSubIndicador(int id,int modelo_grafico){
        GraficoSubIndicador graficosubindicador = null;
        String argumento = Integer.toString(id);
        String argumento2 = Integer.toString(modelo_grafico);
        String[] whereArgs = new String[]{argumento,argumento2};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tb_graficoindicador,SQLConstantes.COLUMNAS_TB_GRAFICOSUDINDICADOR,SQLConstantes.WHERE_CLAUSE_ID_SUBINDICADOR_AND_MODELO_GRAFICO,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                graficosubindicador = new GraficoSubIndicador();
                graficosubindicador.setId(cursor.getInt(cursor.getColumnIndex(SQLConstantes.graficosubindicador_cp_id)));
                graficosubindicador.setId_subindicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.graficosubindicador_cp_id_subindicador)));
                graficosubindicador.setId_indicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.graficosubindicador_cp_id_indicador)));
                graficosubindicador.setNum_grafico(cursor.getInt(cursor.getColumnIndex(SQLConstantes.graficosubindicador_cp_id_num_grafico)));
                graficosubindicador.setModelo_grafico(cursor.getInt(cursor.getColumnIndex(SQLConstantes.graficosubindicador_cp_modelo_grafico)));
                graficosubindicador.setTitulo_grafico(cursor.getString(cursor.getColumnIndex(SQLConstantes.graficosubindicador_cp_titulo_grafico)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return graficosubindicador;
    }

    public GraficoSubIndicador getTituloGraficoSubIndicador(int id,int num_grafico){
        GraficoSubIndicador graficosubindicador = null;
        String argumento = Integer.toString(id);
        String argumento2 = Integer.toString(num_grafico);
        String[] whereArgs = new String[]{argumento,argumento2};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tb_graficoindicador,SQLConstantes.COLUMNAS_TB_GRAFICOSUDINDICADOR,SQLConstantes.WHERE_CLAUSE_ID_SUBINDICADOR_AND_NRO_GRAFICO,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                graficosubindicador = new GraficoSubIndicador();
                graficosubindicador.setId(cursor.getInt(cursor.getColumnIndex(SQLConstantes.graficosubindicador_cp_id)));
                graficosubindicador.setId_subindicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.graficosubindicador_cp_id_subindicador)));
                graficosubindicador.setId_indicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.graficosubindicador_cp_id_indicador)));
                graficosubindicador.setNum_grafico(cursor.getInt(cursor.getColumnIndex(SQLConstantes.graficosubindicador_cp_id_num_grafico)));
                graficosubindicador.setModelo_grafico(cursor.getInt(cursor.getColumnIndex(SQLConstantes.graficosubindicador_cp_modelo_grafico)));
                graficosubindicador.setTitulo_grafico(cursor.getString(cursor.getColumnIndex(SQLConstantes.graficosubindicador_cp_titulo_grafico)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return graficosubindicador;
    }

    public ArrayList<GraficoSubIndicador> getGraficoSubIndicadorXId(int id){
        ArrayList<GraficoSubIndicador> graficosubindicador= new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.rawQuery("SELECT id,id_subindicador,id_indicador,num_grafico,modelo_grafico,titulo_grafico FROM grafico_subindicador where id_subindicador = "+id+"  ",null);
            while(cursor.moveToNext()){
                GraficoSubIndicador dato = new GraficoSubIndicador();
                dato.setId(cursor.getInt(cursor.getColumnIndex("id")));
                dato.setId_subindicador(cursor.getInt(cursor.getColumnIndex("id_subindicador")));
                dato.setId_indicador(cursor.getInt(cursor.getColumnIndex("id_indicador")));
                dato.setNum_grafico(cursor.getInt(cursor.getColumnIndex("num_grafico")));
                dato.setModelo_grafico(cursor.getInt(cursor.getColumnIndex("modelo_grafico")));
                dato.setTitulo_grafico(cursor.getString(cursor.getColumnIndex("titulo_grafico")));
                graficosubindicador.add(dato);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return graficosubindicador;

    }


    public ArrayList<DataIndicador> getDataSubIndicadorXId(int id,int nro,int nro_grafico){
        ArrayList<DataIndicador> datasubindicador= new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.rawQuery("SELECT id,id_indicador,id_subindicador,nro_subindicador,anio,ejex,ejey,num_grafico,dato,medida FROM data_indicador where id_subindicador = "+id+" and nro_subindicador = "+nro+"  and num_grafico="+nro_grafico+" ",null);
            while(cursor.moveToNext()){
                DataIndicador dato = new DataIndicador();
                dato.setId(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_id)));
                dato.setId_indicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_id_indicador)));
                dato.setId_subindicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_id_subindicador)));
                dato.setNro_subindicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_nro_subindicador)));
                dato.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_anio)));
                dato.setEjex(cursor.getString(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_ejex)));
                dato.setEjey(cursor.getString(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_ejey)));
                dato.setNum_grafico(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_num_grafico)));
                dato.setDato(cursor.getFloat(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_dato)));
                dato.setMedida(cursor.getString(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_medida)));
                datasubindicador.add(dato);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return datasubindicador;

    }

    public ArrayList<DataIndicador> getDataSubIndicadorXAnio(int id,int nro_subindicador,int nro_grafico){
        ArrayList<DataIndicador> datasubindicador= new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.rawQuery("SELECT id,id_indicador,id_subindicador,nro_subindicador,anio,ejex,ejey,num_grafico,dato,medida FROM data_indicador where id_subindicador = "+id+" and nro_subindicador = "+nro_subindicador+" and num_grafico = "+nro_grafico+" ",null);
            while(cursor.moveToNext()){
                DataIndicador dato = new DataIndicador();
                dato.setId(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_id)));
                dato.setId_indicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_id_indicador)));
                dato.setId_subindicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_id_subindicador)));
                dato.setNro_subindicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_nro_subindicador)));
                dato.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_anio)));
                dato.setEjex(cursor.getString(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_ejex)));
                dato.setEjey(cursor.getString(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_ejey)));
                dato.setNum_grafico(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_num_grafico)));
                dato.setDato(cursor.getFloat(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_dato)));
                dato.setMedida(cursor.getString(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_medida)));
                datasubindicador.add(dato);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return datasubindicador;

    }

    public ArrayList<DataIndicador> getDataSubIndicadorXNrosubindicador(int id,int nro_subindicador){
        ArrayList<DataIndicador> datasubindicador= new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.rawQuery("SELECT id,id_indicador,id_subindicador,nro_subindicador,anio,ejex,ejey,num_grafico,dato,medida FROM data_indicador where id_subindicador = "+id+" and nro_subindicador = "+nro_subindicador+" ",null);
            while(cursor.moveToNext()){
                DataIndicador dato = new DataIndicador();
                dato.setId(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_id)));
                dato.setId_indicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_id_indicador)));
                dato.setId_subindicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_id_subindicador)));
                dato.setNro_subindicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_nro_subindicador)));
                dato.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_anio)));
                dato.setEjex(cursor.getString(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_ejex)));
                dato.setEjey(cursor.getString(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_ejey)));
                dato.setNum_grafico(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_num_grafico)));
                dato.setDato(cursor.getFloat(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_dato)));
                dato.setMedida(cursor.getString(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_medida)));
                datasubindicador.add(dato);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return datasubindicador;

    }

    public ArrayList<DataIndicador> getDataSubIndicadorXIdNumero(int id,int nro_grafico){
        ArrayList<DataIndicador> datasubindicador= new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.rawQuery("SELECT id,id_indicador,id_subindicador,nro_subindicador,anio,ejex,ejey,num_grafico,dato,medida FROM data_indicador where id_subindicador = "+id+" and num_grafico = "+nro_grafico+" ",null);
            while(cursor.moveToNext()){
                DataIndicador dato = new DataIndicador();
                dato.setId(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_id)));
                dato.setId_indicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_id_indicador)));
                dato.setId_subindicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_id_subindicador)));
                dato.setNro_subindicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_nro_subindicador)));
                dato.setAnio(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_anio)));
                dato.setEjex(cursor.getString(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_ejex)));
                dato.setEjey(cursor.getString(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_ejey)));
                dato.setNum_grafico(cursor.getInt(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_num_grafico)));
                dato.setDato(cursor.getFloat(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_dato)));
                dato.setMedida(cursor.getString(cursor.getColumnIndex(SQLConstantes.dataindicador_cp_medida)));
                datasubindicador.add(dato);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return datasubindicador;

    }


    public LeyendaSubIndicador getLeyendaSubIndicador(int id, int nro){
        LeyendaSubIndicador leyenda = null;
        String argumento1 = Integer.toString(id);
        String argumento2 = Integer.toString(nro);
        String[] whereArgs = new String[]{argumento1,argumento2};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tb_leyendaindicador,SQLConstantes.COLUMNAS_TB_LEYENDASUBINDICADOR,SQLConstantes.WHERE_CLAUSE_ID_SUBINDICADOR_AND_NRO_SUBINDICADOR_,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                leyenda = new LeyendaSubIndicador();
                leyenda.setId(cursor.getInt(cursor.getColumnIndex(SQLConstantes.leyendaindicador_cp_id)));
                leyenda.setId_subindicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.leyendaindicador_cp_id_subindicador)));
                leyenda.setNro_subindicador(cursor.getInt(cursor.getColumnIndex(SQLConstantes.leyendaindicador_cp_nro_subindicador)));
                leyenda.setNombre_nro_subindicador(cursor.getString(cursor.getColumnIndex(SQLConstantes.leyendaindicador_cp_nombre_nro_subindicador)));

            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return leyenda;
    }

    public SettingService getAllSetting(){
        SettingService indicador = null;
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tb_settings_service,SQLConstantes.COLUMNAS_TB_SETTINGS_SERVICE,null,null,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                indicador = new SettingService();
                indicador.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.settings_id)));
                indicador.setFecha(cursor.getString(cursor.getColumnIndex(SQLConstantes.settings_fecha)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return indicador;
    }
    public ErrorService getAllError(){
        ErrorService error = null;
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tb_errors_service,SQLConstantes.COLUMNAS_TB_ERRORS_SERVICE,null,null,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                error = new ErrorService();
                error.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.errors_id)));
                error.setCodigo(cursor.getString(cursor.getColumnIndex(SQLConstantes.errors_codigo)));
                error.setDescripcion(cursor.getString(cursor.getColumnIndex(SQLConstantes.errors_descripcion)));
                error.setEstado(cursor.getString(cursor.getColumnIndex(SQLConstantes.errors_estado)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return error;
    }

    public void insertarIndicadorService(Indicador indicadorService){
        ContentValues contentValues = indicadorService.toValues();
        sqLiteDatabase.insert(SQLConstantes.tb_indicador,null,contentValues);
    }
    public void insertarSubIndicadorService(SubIndicador subIndicadorService){
        ContentValues contentValues = subIndicadorService.toValues();
        sqLiteDatabase.insert(SQLConstantes.tb_subindicador,null,contentValues);
    }
    public void insertarDataIndicadorService(DataIndicador dataIndicadorService){
        ContentValues contentValues = dataIndicadorService.toValues();
        sqLiteDatabase.insert(SQLConstantes.tb_dataindicador,null,contentValues);
    }
    public void insertarGraficoSubIndicadorService(GraficoSubIndicador graficoSubIndicadorService){
        ContentValues contentValues = graficoSubIndicadorService.toValues();
        sqLiteDatabase.insert(SQLConstantes.tb_graficoindicador,null,contentValues);
    }
    public void insertarLeyendaSubIndicadorService(LeyendaSubIndicador leyendaSubIndicadorService){
        ContentValues contentValues = leyendaSubIndicadorService.toValues();
        sqLiteDatabase.insert(SQLConstantes.tb_leyendaindicador,null,contentValues);
    }

    public void actualizarDataIndicadorService(int iddata, ContentValues contentValues){
        String id = String.valueOf(iddata);
        String[] whereArgs = new String[]{id};
        sqLiteDatabase.update(SQLConstantes.tb_dataindicador,contentValues,SQLConstantes.WHERE_CLAUSE_ID_DATAINDICADOR,whereArgs);
    }
    public void actualizarIndicadorService(int idindicador, ContentValues contentValues){
        String id = String.valueOf(idindicador);
        String[] whereArgs = new String[]{id};
        sqLiteDatabase.update(SQLConstantes.tb_indicador,contentValues,SQLConstantes.WHERE_CLAUSE_ID_INDICADOR,whereArgs);
    }
    public void actualizarSubIndicadorService(int idsubindicador, ContentValues contentValues){
        String id = String.valueOf(idsubindicador);
        String[] whereArgs = new String[]{id};
        sqLiteDatabase.update(SQLConstantes.tb_subindicador,contentValues,SQLConstantes.WHERE_CLAUSE_ID_SUBINDICADOR,whereArgs);
    }
    public void actualizarGraficoService(int idgrafico, ContentValues contentValues){
        String id = String.valueOf(idgrafico);
        String[] whereArgs = new String[]{id};
        sqLiteDatabase.update(SQLConstantes.tb_graficoindicador,contentValues,SQLConstantes.WHERE_CLAUSE_ID_GRAFICO,whereArgs);
    }
    public void actualizarLeyendaService(int idleyenda, ContentValues contentValues){
        String id = String.valueOf(idleyenda);
        String[] whereArgs = new String[]{id};
        sqLiteDatabase.update(SQLConstantes.tb_leyendaindicador,contentValues,SQLConstantes.WHERE_CLAUSE_ID_LEYENDA,whereArgs);
    }


    public void insertarSettingService(SettingService settingService){
        ContentValues contentValues = settingService.toValues();
        sqLiteDatabase.insert(SQLConstantes.tb_settings_service,null,contentValues);
    }
    public void insertarErrorService(ErrorService errorService){
        ContentValues contentValues = errorService.toValues();
        sqLiteDatabase.insert(SQLConstantes.tb_errors_service,null,contentValues);
    }
    public void actualizarErrorService(ContentValues contentValues){
        sqLiteDatabase.update(SQLConstantes.tb_errors_service,contentValues,SQLConstantes.WHERE_CLAUSE_ID_ERROR_SERVICE,null);
    }
    public void actualizarSettingService(ContentValues contentValues){
        sqLiteDatabase.update(SQLConstantes.tb_settings_service,contentValues,SQLConstantes.WHERE_CLAUSE_ID_SETTING_SERVICE,null);
    }

    public void deleteIndicador(){
        sqLiteDatabase.execSQL("delete from "+ SQLConstantes.tb_indicador);
    }
    public void deleteSubIndicador(){
        sqLiteDatabase.execSQL("delete from "+ SQLConstantes.tb_subindicador);
    }
    public void deleteDataIndicador(){
        sqLiteDatabase.execSQL("delete from "+ SQLConstantes.tb_dataindicador);
    }
    public void deleteGraficoSubIndicador(){
        sqLiteDatabase.execSQL("delete from "+ SQLConstantes.tb_graficoindicador);
    }
    public void deleteLeyendaSubIndicador(){
        sqLiteDatabase.execSQL("delete from "+ SQLConstantes.tb_leyendaindicador);
    }








}
