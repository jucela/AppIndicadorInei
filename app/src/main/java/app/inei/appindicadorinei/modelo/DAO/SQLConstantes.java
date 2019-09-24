package app.inei.appindicadorinei.modelo.DAO;

public class SQLConstantes {
    public static String DB_PATH = "/data/data/app.inei.appindicadorinei/databases/";

    public static String DB_NAME = "datosindicador.sqlite";

    public static String tb_indicador     = "indicador";
    public static String tb_subindicador  = "sub_indicador";
    public static String tb_dataindicador = "data_indicador";
    public static String tb_leyendaindicador = "leyenda_subindicador";
    public static String tb_graficoindicador = "grafico_subindicador";

    public static String indicador_cp_id_indicador          = "id_indicador";
    public static String indicador_cp_nombre_indicador      = "nombre_indicador";
    public static String indicador_cp_descripcion_indicador = "descripcion_indicador";
    public static String indicador_cp_total_subdindicador   = "total_subindicador";

    public static String subindicador_cp_id_indicador             = "id_indicador";
    public static String subindicador_cp_id_subindicador          = "id_subindicador";
    public static String subindicador_cp_nombre_subindicador      = "nombre_subindicador";
    public static String subindicador_cp_descripcion_subindicador = "descripcion_subindicador";
    public static String subindicador_cp_fuente                   = "fuente";
    public static String subindicador_cp_total_graficos           = "total_graficos";


    public static String dataindicador_cp_id_indicador    = "id_indicador";
    public static String dataindicador_cp_id_subindicador = "id_subindicador";
    public static String dataindicador_cp_nro_subindicador= "nro_subindicador";
    public static String dataindicador_cp_anio            = "anio";
    public static String dataindicador_cp_ejex            = "ejex";
    public static String dataindicador_cp_ejey            = "ejey";
    public static String dataindicador_cp_num_grafico     = "num_grafico";
    public static String dataindicador_cp_dato            = "dato";
    public static String dataindicador_cp_medida          = "medida";

    public static String leyendaindicador_cp_id                     = "id";
    public static String leyendaindicador_cp_id_subindicador        = "id_subindicador";
    public static String leyendaindicador_cp_nro_subindicador       = "nro_subindicador";
    public static String leyendaindicador_cp_nombre_nro_subindicador= "nombre_nro_subindicador";

    public static String graficosubindicador_cp_id                     = "id";
    public static String graficosubindicador_cp_id_subindicador        = "id_subindicador";
    public static String graficosubindicador_cp_id_indicador           = "id_indicador";
    public static String graficosubindicador_cp_id_num_grafico        = "num_grafico";
    public static String graficosubindicador_cp_modelo_grafico         = "modelo_grafico";



    public static final String SQL_CREATE_TABLA_INDICADOR =
            "CREATE TABLE " + tb_indicador + "(" +
                    indicador_cp_id_indicador + " TEXT," +
                    indicador_cp_nombre_indicador + " TEXT," +
                    indicador_cp_descripcion_indicador + " TEXT," +
                    indicador_cp_total_subdindicador + " TEXT"+");"
            ;

    public static final String SQL_CREATE_TABLA_SUBINDICADOR =
            "CREATE TABLE " + tb_subindicador + "(" +
                    subindicador_cp_id_subindicador + " TEXT," +
                    subindicador_cp_id_indicador + " TEXT," +
                    subindicador_cp_nombre_subindicador + " TEXT," +
                    subindicador_cp_descripcion_subindicador + " TEXT," +
                    subindicador_cp_fuente + " TEXT," +
                    subindicador_cp_total_graficos + " TEXT"+");"
            ;

    public static final String SQL_CREATE_TABLA_DATAINDICADOR =
            "CREATE TABLE " + tb_dataindicador + "(" +
                    dataindicador_cp_id_indicador + " TEXT," +
                    dataindicador_cp_id_subindicador + " TEXT," +
                    dataindicador_cp_nro_subindicador + " TEXT," +
                    dataindicador_cp_anio + " TEXT," +
                    dataindicador_cp_ejex + " TEXT," +
                    dataindicador_cp_ejey + " TEXT," +
                    dataindicador_cp_num_grafico + " TEXT," +
                    dataindicador_cp_dato + " TEXT," +
                    dataindicador_cp_medida + " TEXT"+");"
            ;

    public static final String[] COLUMNAS_TB_INDICADOR = {
                    indicador_cp_id_indicador,
                    indicador_cp_nombre_indicador,
                    indicador_cp_descripcion_indicador,
                    indicador_cp_total_subdindicador
    };

    public static final String[] COLUMNAS_TB_SUBINDICADOR = {
                    subindicador_cp_id_indicador,
                    subindicador_cp_id_subindicador,
                    subindicador_cp_nombre_subindicador,
                    subindicador_cp_descripcion_subindicador,
                    subindicador_cp_fuente,
                    subindicador_cp_total_graficos
    };

    public static final String[] COLUMNAS_TB_DATAINDICADOR = {
                    dataindicador_cp_id_indicador,
                    dataindicador_cp_id_subindicador,
                    dataindicador_cp_nro_subindicador,
                    dataindicador_cp_anio,
                    dataindicador_cp_ejex,
                    dataindicador_cp_ejey,
                    dataindicador_cp_num_grafico,
                    dataindicador_cp_dato,
                    dataindicador_cp_medida
    };

    public static final String[] COLUMNAS_TB_LEYENDASUBINDICADOR = {
            leyendaindicador_cp_id,
            leyendaindicador_cp_id_subindicador,
            leyendaindicador_cp_nro_subindicador,
            leyendaindicador_cp_nombre_nro_subindicador
    };

    public static final String[] COLUMNAS_TB_GRAFICOSUDINDICADOR = {
            graficosubindicador_cp_id,
            graficosubindicador_cp_id_subindicador,
            graficosubindicador_cp_id_indicador,
            graficosubindicador_cp_id_num_grafico,
            graficosubindicador_cp_modelo_grafico
    };

    public static final String WHERE_CLAUSE_ID_INDICADOR     = "id_indicador=?";
    public static final String WHERE_CLAUSE_ID_SUBINDICADOR  = "id_subindicador=?";
    public static final String WHERE_CLAUSE_NRO_SUBINDICADOR = "nro_subindicador=?";
    public static final String WHERE_CLAUSE_ID_SUBINDICADOR_AND_NRO_SUBINDICADOR_ = "id_subindicador=? and nro_subindicador=?";






}
