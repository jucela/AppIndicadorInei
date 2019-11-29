package app.inei.appindicadorinei.modelo.pojos;

import android.content.ContentValues;

import app.inei.appindicadorinei.modelo.DAO.SQLConstantes;

public class LeyendaSubIndicador {
    public int id;
    public int id_subindicador;
    public int nro_subindicador;
    public String nombre_nro_subindicador;

    public LeyendaSubIndicador(int id, int id_subindicador, int nro_subindicador, String nombre_nro_subindicador) {
        this.id = id;
        this.id_subindicador = id_subindicador;
        this.nro_subindicador = nro_subindicador;
        this.nombre_nro_subindicador = nombre_nro_subindicador;
    }

    public LeyendaSubIndicador() {
        this.id = 0;
        this.id_subindicador = 0;
        this.nro_subindicador = 0;
        this.nombre_nro_subindicador = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_subindicador() {
        return id_subindicador;
    }

    public void setId_subindicador(int id_subindicador) {
        this.id_subindicador = id_subindicador;
    }

    public int getNro_subindicador() {
        return nro_subindicador;
    }

    public void setNro_subindicador(int nro_subindicador) {
        this.nro_subindicador = nro_subindicador;
    }

    public String getNombre_nro_subindicador() {
        return nombre_nro_subindicador;
    }

    public void setNombre_nro_subindicador(String nombre_nro_subindicador) {
        this.nombre_nro_subindicador = nombre_nro_subindicador;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.leyendaindicador_cp_id,id);
        contentValues.put(SQLConstantes.leyendaindicador_cp_id_subindicador,id_subindicador);
        contentValues.put(SQLConstantes.leyendaindicador_cp_nro_subindicador,nro_subindicador);
        contentValues.put(SQLConstantes.leyendaindicador_cp_nombre_nro_subindicador,nombre_nro_subindicador);
        return contentValues;
    }
}
