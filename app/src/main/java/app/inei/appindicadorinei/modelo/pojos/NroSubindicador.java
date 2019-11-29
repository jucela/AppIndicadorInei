package app.inei.appindicadorinei.modelo.pojos;

public class NroSubindicador {
    public int nro_subindicador;
    public String ejex;

    public NroSubindicador(int nro_subindicador,String ejex) {
        this.nro_subindicador = nro_subindicador;
        this.ejex = ejex;
    }

    public NroSubindicador() {
        this.nro_subindicador = 0;
        this.ejex = "";
    }

    public int getNro_subindicador() {
        return nro_subindicador;
    }

    public void setNro_subindicador(int nro_subindicador) {
        this.nro_subindicador = nro_subindicador;
    }

    public String getEjex() {
        return ejex;
    }

    public void setEjex(String ejex) {
        this.ejex = ejex;
    }
}
