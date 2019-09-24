package app.inei.appindicadorinei.modelo.pojos;

public class DataIndicador {
    public int id_indicador;
    public int id_subindicador;
    public int nro_subindicador;
    public int anio;
    public String ejex;
    public String ejey;
    public int num_grafico;
    public float dato;
    public String medida;

    public DataIndicador(int id_indicador, int id_subindicador, int nro_subindicador, int anio, String ejex, String ejey, int num_grafico, float dato, String medida) {
        this.id_indicador = id_indicador;
        this.id_subindicador = id_subindicador;
        this.nro_subindicador = nro_subindicador;
        this.anio = anio;
        this.ejex = ejex;
        this.ejey = ejey;
        this.num_grafico = num_grafico;
        this.dato = dato;
        this.medida = medida;
    }

    public DataIndicador() {
        this.id_indicador = 0;
        this.id_subindicador = 0;
        this.nro_subindicador = 0;
        this.anio = 0;
        this.ejex = "";
        this.ejey = "";
        this.num_grafico = 0;
        this.dato = 0;
        this.medida = "";
    }

    public int getId_indicador() {
        return id_indicador;
    }

    public void setId_indicador(int id_indicador) {
        this.id_indicador = id_indicador;
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

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getEjex() {
        return ejex;
    }

    public void setEjex(String ejex) {
        this.ejex = ejex;
    }

    public String getEjey() {
        return ejey;
    }

    public void setEjey(String ejey) {
        this.ejey = ejey;
    }

    public int getNum_grafico() {
        return num_grafico;
    }

    public void setNum_grafico(int num_grafico) {
        this.num_grafico = num_grafico;
    }

    public float getDato() {
        return dato;
    }

    public void setDato(float dato) {
        this.dato = dato;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }
}
