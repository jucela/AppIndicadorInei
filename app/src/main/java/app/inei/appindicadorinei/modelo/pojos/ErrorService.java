package app.inei.appindicadorinei.modelo.pojos;

import android.content.ContentValues;

import app.inei.appindicadorinei.modelo.DAO.SQLConstantes;

public class ErrorService {

    private String id;
    private String codigo;
    private String descripcion;
    private String estado;

    public ErrorService(String id, String codigo, String descripcion, String estado) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public ErrorService() {
        this.id = "";
        this.codigo = "";
        this.descripcion = "";
        this.estado = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.errors_id,id);
        contentValues.put(SQLConstantes.errors_codigo,codigo);
        contentValues.put(SQLConstantes.errors_descripcion,descripcion);
        contentValues.put(SQLConstantes.errors_estado,estado);
        return contentValues;
    }
}
