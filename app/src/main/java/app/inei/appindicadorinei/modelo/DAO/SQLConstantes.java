package app.inei.appindicadorinei.modelo.DAO;

public class SQLConstantes {
    public static String DB_PATH = "/data/data/app.inei.appindicadorinei/databases/";

    public static String DB_NAME = "datosindicador.sqlite";

    public static String tb_indicador        = "indicador";
    public static String tb_subindicador     = "sub_indicador";
    public static String tb_dataindicador    = "data_indicador";
    public static String tb_leyendaindicador = "leyenda_subindicador";
    public static String tb_graficoindicador = "grafico_subindicador";
    public static String tb_settings_service = "setting_service";
    public static String tb_errors_service   = "error_service";

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


    public static String dataindicador_cp_id              = "id";
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
    public static String graficosubindicador_cp_id_num_grafico         = "num_grafico";
    public static String graficosubindicador_cp_modelo_grafico         = "modelo_grafico";
    public static String graficosubindicador_cp_titulo_grafico         = "titulo_grafico";

    public static String link_indicador_service     ="http://191.98.163.157/DevAppIndicadores/conexion_sqlsrv_indicadores.php";
    public static String link_subindicador_service  ="http://191.98.163.157/DevAppIndicadores/conexion_sqlsrv_sub_indicador.php";
    public static String link_dataindicador_service ="http://191.98.163.157/DevAppIndicadores/conexion_sqlsrv_data_indicador.php";
    public static String link_grafico_service       ="http://191.98.163.157/DevAppIndicadores/conexion_sqlsrv_grafico_subindicador.php";
    public static String link_leyenda_service       ="http://191.98.163.157/DevAppIndicadores/conexion_sqlsrv_leyenda_subindicador.php";
    public static String link_data                  ="http://191.98.163.157/DevAppIndicadores/conexion_sqlsrv_data.php";
    public static String link_insercion             ="http://191.98.163.157/DevAppIndicadores/insercion_sqlsrv_indicadores.php?";
    public static String link_insercion2            ="http://191.98.163.157/DevAppIndicadores/enviojson_sqlsrv_indicadores.php";

    public static String link_indicador    ="https://api.myjson.com/bins/1006ny";
    public static String link_subindicador ="https://api.myjson.com/bins/unhxq";

    public static String settings_id     = "id";
    public static String settings_fecha  = "fecha";

    public static String errors_id          = "id";
    public static String errors_codigo      = "codigo";
    public static String errors_descripcion = "descripcion";
    public static String errors_estado      = "estado";



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
                    dataindicador_cp_id,
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
            graficosubindicador_cp_modelo_grafico,
            graficosubindicador_cp_titulo_grafico
    };

    public static final String[] COLUMNAS_TB_SETTINGS_SERVICE = {
            settings_id,
            settings_fecha
    };

    public static final String[] COLUMNAS_TB_ERRORS_SERVICE = {
            errors_id,
            errors_codigo,
            errors_descripcion,
            errors_estado
    };

    public static final String WHERE_CLAUSE_ID_INDICADOR     = "id_indicador=?";
    public static final String WHERE_CLAUSE_ID_SUBINDICADOR  = "id_subindicador=?";
    public static final String WHERE_CLAUSE_ID_DATAINDICADOR  = "id=?";
    public static final String WHERE_CLAUSE_ID_GRAFICO  = "id=?";
    public static final String WHERE_CLAUSE_ID_LEYENDA  = "id=?";
    public static final String WHERE_CLAUSE_ID_SUBINDICADOR_AND_MODELO_GRAFICO  = "id_subindicador=? and modelo_grafico=?";
    public static final String WHERE_CLAUSE_ID_SUBINDICADOR_AND_NRO_GRAFICO  = "id_subindicador=? and num_grafico=?";
    public static final String WHERE_CLAUSE_NRO_SUBINDICADOR = "nro_subindicador=?";
    public static final String WHERE_CLAUSE_ID_SUBINDICADOR_AND_NRO_SUBINDICADOR_ = "id_subindicador=? and nro_subindicador=?";
    public static final String WHERE_CLAUSE_ID_ERROR_SERVICE = "id=1";
    public static final String WHERE_CLAUSE_ID_SETTING_SERVICE = "id=1";






}
