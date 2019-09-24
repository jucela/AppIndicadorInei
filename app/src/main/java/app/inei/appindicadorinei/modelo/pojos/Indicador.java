package app.inei.appindicadorinei.modelo.pojos;

import android.content.ContentValues;

import app.inei.appindicadorinei.modelo.DAO.SQLConstantes;

public class Indicador {

    public int id_indicador;
    public String nombre_indicador;
    public String descripcion_indicador;
    public int total_subdindicador;

    public Indicador(int id_indicador, String nombre_indicador, String descripcion_indicador, int total_subdindicador) {
        this.id_indicador = id_indicador;
        this.nombre_indicador = nombre_indicador;
        this.descripcion_indicador = descripcion_indicador;
        this.total_subdindicador = total_subdindicador;
    }

    public Indicador() {
        this.id_indicador=0;
        this.nombre_indicador="";
        this.descripcion_indicador="";
        this.total_subdindicador=0;
    }

    public int getId_indicador() {
        return id_indicador;
    }

    public void setId_indicador(int id_indicador) {
        this.id_indicador = id_indicador;
    }

    public String getNombre_indicador() {
        return nombre_indicador;
    }

    public void setNombre_indicador(String nombre_indicador) {
        this.nombre_indicador = nombre_indicador;
    }

    public String getDescripcion_indicador() {
        return descripcion_indicador;
    }

    public void setDescripcion_indicador(String descripcion_indicador) {
        this.descripcion_indicador = descripcion_indicador;
    }

    public int getTotal_subdindicador() {
        return total_subdindicador;
    }

    public void setTotal_subdindicador(int total_subdindicador) {
        this.total_subdindicador = total_subdindicador;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.indicador_cp_id_indicador,id_indicador);
        contentValues.put(SQLConstantes.indicador_cp_nombre_indicador,nombre_indicador);
        contentValues.put(SQLConstantes.indicador_cp_descripcion_indicador,descripcion_indicador);
        contentValues.put(SQLConstantes.indicador_cp_total_subdindicador,total_subdindicador);
        return contentValues;
    }
}
