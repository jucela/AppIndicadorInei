package app.inei.appindicadorinei.modelo.pojos;

import android.content.ContentValues;

import app.inei.appindicadorinei.modelo.DAO.SQLConstantes;

public class GraficoSubIndicador {

    public int id;
    public int id_subindicador;
    public int id_indicador;
    public int num_grafico;
    public int modelo_grafico;
    public String titulo_grafico;

    public GraficoSubIndicador(int id, int id_subindicador, int id_indicador, int num_graficos, int modelo_grafico, String titulo_grafico) {
        this.id = id;
        this.id_subindicador = id_subindicador;
        this.id_indicador = id_indicador;
        this.num_grafico = num_graficos;
        this.modelo_grafico = modelo_grafico;
        this.titulo_grafico = titulo_grafico;
    }

    public GraficoSubIndicador() {
        this.id = 0;
        this.id_subindicador = 0;
        this.id_indicador = 0;
        this.num_grafico = 0;
        this.modelo_grafico = 0;
        this.titulo_grafico = "";
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

    public int getId_indicador() {
        return id_indicador;
    }

    public void setId_indicador(int id_indicador) {
        this.id_indicador = id_indicador;
    }

    public int getNum_grafico() {
        return num_grafico;
    }

    public void setNum_grafico(int num_grafico) {
        this.num_grafico = num_grafico;
    }

    public int getModelo_grafico() {
        return modelo_grafico;
    }

    public void setModelo_grafico(int modelo_grafico) {
        this.modelo_grafico = modelo_grafico;
    }

    public String getTitulo_grafico() {
        return titulo_grafico;
    }

    public void setTitulo_grafico(String titulo_grafico) {
        this.titulo_grafico = titulo_grafico;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.graficosubindicador_cp_id,id);
        contentValues.put(SQLConstantes.graficosubindicador_cp_id_subindicador,id_subindicador);
        contentValues.put(SQLConstantes.graficosubindicador_cp_id_indicador,id_indicador);
        contentValues.put(SQLConstantes.graficosubindicador_cp_id_num_grafico,num_grafico);
        contentValues.put(SQLConstantes.graficosubindicador_cp_modelo_grafico,modelo_grafico);
        contentValues.put(SQLConstantes.graficosubindicador_cp_titulo_grafico,titulo_grafico);
        return contentValues;
    }
}
