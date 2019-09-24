package app.inei.appindicadorinei.modelo.pojos;

public class SubIndicador {
    public int id_subindicador;
    public int id_indicador;
    public String nombre_subindicador;
    public String descripcion_subindicador;
    public String fuente;
    public int total_graficos;

    public SubIndicador(int id_subindicador, int id_indicador, String nombre_subindicador, String descripcion_subindicador, String fuente, int total_graficos) {
        this.id_subindicador = id_subindicador;
        this.id_indicador = id_indicador;
        this.nombre_subindicador = nombre_subindicador;
        this.descripcion_subindicador = descripcion_subindicador;
        this.fuente = fuente;
        this.total_graficos = total_graficos;
    }

    public SubIndicador() {
        this.id_subindicador = 0;
        this.id_indicador = 0;
        this.nombre_subindicador = "";
        this.descripcion_subindicador = "";
        this.fuente = "";
        this.total_graficos = 0;
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

    public String getNombre_subindicador() {
        return nombre_subindicador;
    }

    public void setNombre_subindicador(String nombre_subindicador) {
        this.nombre_subindicador = nombre_subindicador;
    }

    public String getDescripcion_subindicador() {
        return descripcion_subindicador;
    }

    public void setDescripcion_subindicador(String descripcion_subindicador) {
        this.descripcion_subindicador = descripcion_subindicador;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public int getTotal_graficos() {
        return total_graficos;
    }

    public void setTotal_graficos(int total_graficos) {
        this.total_graficos = total_graficos;
    }


}
