package app.inei.appindicadorinei.modelo.pojos;

import android.content.ContentValues;

import app.inei.appindicadorinei.modelo.DAO.SQLConstantes;

public class SettingService {

    private String id;
    private String fecha;

    public SettingService(String id, String fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public SettingService() {
        this.id = "";
        this.fecha = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLConstantes.settings_id,id);
        contentValues.put(SQLConstantes.settings_fecha,fecha);
        return contentValues;
    }
}
