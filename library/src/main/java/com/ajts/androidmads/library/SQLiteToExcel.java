package com.ajts.androidmads.library;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SQLiteToExcel {

    private static Handler handler = new Handler(Looper.getMainLooper());

    private SQLiteDatabase database;
    private String mExportPath;
    private HSSFWorkbook workbook;

    private List<String> mExcludeColumns = null;
    private HashMap<String, String> mPrettyNameMapping = null;
    private ExportCustomFormatter mCustomFormatter = null;


    public SQLiteToExcel(Context context, String dbName) {
        this(context, dbName, Environment.getExternalStorageDirectory().toString() + File.separator);
    }

    public SQLiteToExcel(Context context, String dbName, String exportPath) {
        mExportPath = exportPath;
        try {
            database = SQLiteDatabase.openOrCreateDatabase(context.getDatabasePath(dbName).getAbsolutePath(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the exclude columns list
     *
     * @param excludeColumns
     */
    public void setExcludeColumns(List<String> excludeColumns) {
        mExcludeColumns = excludeColumns;
    }

    /**
     * Set the pretty name mapping
     *
     * @param prettyNameMapping
     */
    public void setPrettyNameMapping(HashMap<String, String> prettyNameMapping) {
        mPrettyNameMapping = prettyNameMapping;
    }

    /**
     * Set a the custom formatter for the column value output
     * @param customFormatter
     */
    public void setCustomFormatter(ExportCustomFormatter customFormatter) {
        mCustomFormatter = customFormatter;
    }

    private ArrayList<String> getAllTables() {
        ArrayList<String> tables = new ArrayList<>();
        Cursor cursor = database.rawQuery("select name from sqlite_master where type='table' order by name", null);
        while (cursor.moveToNext()) {
            tables.add(cursor.getString(0));
        }
        cursor.close();
        return tables;
    }

    private ArrayList<String> getColumns(String table) {
        ArrayList<String> columns = new ArrayList<>();
        Cursor cursor = database.rawQuery("PRAGMA table_info(" + table + ")", null);
        while (cursor.moveToNext()) {
            columns.add(cursor.getString(1));
        }
        cursor.close();
        return columns;
    }

    private void exportTables(List<String> tables, final String fileName) throws Exception {
        workbook = new HSSFWorkbook();
        for (int i = 0; i < tables.size(); i++) {
            if (!tables.get(i).equals("android_metadata")) {
                HSSFSheet sheet = workbook.createSheet(prettyNameMapping(tables.get(i)));
                createSheet(tables.get(i), sheet);
            }
        }
        File file = new File(mExportPath, fileName);
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.flush();
        fos.close();
        workbook.close();
        database.close();
    }

    public void exportSingleTable(final String table, final String fileName, ExportListener listener) {
        List<String> tables = new ArrayList<>();
        tables.add(table);
        startExportTables(tables, fileName, listener);
    }

    public void exportSpecificTables(final List<String> tables, String fileName, ExportListener listener) {
        startExportTables(tables, fileName, listener);
    }

    public void exportAllTables(final String fileName, ExportListener listener) {
        ArrayList<String> tables = getAllTables();
        startExportTables(tables, fileName, listener);
    }

    private void startExportTables(final List<String> tables, final String fileName, final ExportListener listener) {
        if (listener != null) {
            listener.onStart();
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    exportTables(tables, fileName);
                    if (listener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onCompleted(mExportPath + fileName);
                            }
                        });
                    }
                } catch (final Exception e) {
                    if (database != null && database.isOpen()) {
                        database.close();
                    }
                    if (listener != null)
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError(e);
                            }
                        });
                }
            }
        }).start();
    }

    private void createSheet(String table, HSSFSheet sheet) {
        HSSFRow rowA = sheet.createRow(0);
        ArrayList<String> columns = getColumns(table);
        int cellIndex = 0;
        for (int i = 0; i < columns.size(); i++) {
            String columnName = prettyNameMapping("" + columns.get(i));
            if (!excludeColumn(columnName)) {
                HSSFCell cellA = rowA.createCell(cellIndex);
                cellA.setCellValue(new HSSFRichTextString(columnName));
                cellIndex++;
            }
        }
        insertItemToSheet(table, sheet, columns);
    }

    private void insertItemToSheet(String table, HSSFSheet sheet, ArrayList<String> columns) {
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        Cursor cursor = database.rawQuery("select * from " + table, null);
        cursor.moveToFirst();
        int n = 1;
        while (!cursor.isAfterLast()) {
            HSSFRow rowA = sheet.createRow(n);
            int cellIndex = 0;
            for (int j = 0; j < columns.size(); j++) {
                String columnName = "" + columns.get(j);
                if (!excludeColumn(columnName)) {
                    HSSFCell cellA = rowA.createCell(cellIndex);
                    if (cursor.getType(j) == Cursor.FIELD_TYPE_BLOB) {
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) cellIndex, n, (short) (cellIndex + 1), n + 1);
                        anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                        patriarch.createPicture(anchor, workbook.addPicture(cursor.getBlob(j), HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        String value = cursor.getString(j);
                        if (null != mCustomFormatter) {
                            value = mCustomFormatter.process(columnName, value);
                        }
                        cellA.setCellValue(new HSSFRichTextString(value));
                    }
                    cellIndex++;
                }
            }
            n++;
            cursor.moveToNext();
        }
        cursor.close();
    }

    /**
     * Do we exclude the specified column from the export
     *
     * @param column
     * @return boolean
     */
    private boolean excludeColumn(String column) {
        boolean exclude = false;
        if (null != mExcludeColumns) {
            return mExcludeColumns.contains(column);
        }

        return exclude;
    }

    /**
     * Convert the specified name to a `pretty` name if a mapping exists
     *
     * @param name
     * @return
     */
    private String prettyNameMapping(String name) {
        if (null != mPrettyNameMapping) {
            if (mPrettyNameMapping.containsKey(name)) {
                name = mPrettyNameMapping.get(name);
            }
        }
        return name;
    }

    public interface ExportListener {
        void onStart();

        void onCompleted(String filePath);

        void onError(Exception e);
    }

    /**
     * Interface class for the custom formatter
     */
    public interface ExportCustomFormatter {
        String process(String columnName, String value);
    }

    /*Exportar Solo una tabla*/

    public void exportAllTable(final String fileName, String id, String titulo, ExportListener listener) {
        String table = "datos_indicador_service";
        startExportTable(table, fileName, listener, id, titulo);
    }

    private void startExportTable(final String table, final String fileName, final ExportListener listener, final String id, final String titulo) {
        if (listener != null) {
            listener.onStart();
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    exportTable(table, fileName,id,titulo);
                    if (listener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onCompleted(mExportPath + fileName);
                            }
                        });
                    }
                } catch (final Exception e) {
                    if (database != null && database.isOpen()) {
                        database.close();
                    }
                    if (listener != null)
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError(e);
                            }
                        });
                }
            }
        }).start();
    }

    private void exportTable(String table, final String fileName, String id, String titulo) throws Exception {
        workbook = new HSSFWorkbook();
        if (!table.equals("android_metadata")) {
            /*Nombre de libro*/
            HSSFSheet sheet = workbook.createSheet(prettyNameMapping(titulo));
            create1Sheet(table, sheet,id);
        }
        File file = new File(mExportPath, fileName);
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.flush();
        fos.close();
        workbook.close();
        database.close();
    }

    private void create1Sheet(String table, HSSFSheet sheet, String id) {
        HSSFRow rowA = sheet.createRow(0);
        ArrayList<String> columns = new ArrayList<>();
        //Nombres de cabezera de excel
        columns.add("Item");
        columns.add("Tipo");
        columns.add("Periodo");
        columns.add("Porcentaje");
        int cellIndex = 0;
        for (int i = 0; i < columns.size(); i++) {
            String columnName = prettyNameMapping("" + columns.get(i));
            if (!excludeColumn(columnName)) {
                HSSFCell cellA = rowA.createCell(cellIndex);
                cellA.setCellValue(new HSSFRichTextString(columnName));
                cellIndex++;
            }
        }
        insertItemTo1Sheet(table, sheet, columns,id);
    }

    private void insertItemTo1Sheet(String table, HSSFSheet sheet, ArrayList<String> columns, String id) {
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        Cursor cursor = database.rawQuery("select _id,tipo,periodo,dato from " +table+" where indicador_id="+id+"", null);
        cursor.moveToFirst();
        int n = 1;
        while (!cursor.isAfterLast()) {
            HSSFRow rowA = sheet.createRow(n);
            int cellIndex = 0;
            for (int j = 0; j < columns.size(); j++) {
                String columnName = "" + columns.get(j);
                if (!excludeColumn(columnName)) {
                    HSSFCell cellA = rowA.createCell(cellIndex);
                    if (cursor.getType(j) == Cursor.FIELD_TYPE_BLOB) {
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) cellIndex, n, (short) (cellIndex + 1), n + 1);
                        anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                        patriarch.createPicture(anchor, workbook.addPicture(cursor.getBlob(j), HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        String value = cursor.getString(j);
                        if (null != mCustomFormatter) {
                            value = mCustomFormatter.process(columnName, value);
                        }
                        cellA.setCellValue(new HSSFRichTextString(value));
                    }
                    cellIndex++;
                }
            }
            n++;
            cursor.moveToNext();
        }
        cursor.close();
    }

    /*EXPORTACION LIBRO POR CONSULTA */



    public void exportAllNTables(final String fileName, String id, String titulo,  ExportListener listener) {

        ArrayList<String> libros = new ArrayList<>();
        libros.add("Datos");
        startExportNTables(libros, fileName, listener,id,titulo);
    }

    private void startExportNTables(final List<String> tables, final String fileName, final ExportListener listener, final String id, final String titulo) {
        if (listener != null) {
            listener.onStart();
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    exportNTables(tables, fileName,id,titulo);
                    if (listener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onCompleted(mExportPath + fileName);
                            }
                        });
                    }
                } catch (final Exception e) {
                    if (database != null && database.isOpen()) {
                        database.close();
                    }
                    if (listener != null)
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onError(e);
                            }
                        });
                }
            }
        }).start();
    }

    private void exportNTables(List<String> tables, final String fileName, String id, String titulo) throws Exception {
        workbook = new HSSFWorkbook();
        for (int i = 0; i < tables.size(); i++) {
            if (!tables.get(i).equals("android_metadata")) {
                HSSFSheet sheet = workbook.createSheet(prettyNameMapping("Datos"));
                createNSheet(tables.get(i), sheet,id);

            }
        }
        File file = new File(mExportPath, fileName);
        FileOutputStream fos = new FileOutputStream(file);
        workbook.write(fos);
        fos.flush();
        fos.close();
        workbook.close();
        database.close();
    }

    private void createNSheet(String table, HSSFSheet sheet, String id) {
        HSSFRow rowA = sheet.createRow(0);
        //ArrayList<String> columns = getColumns(table);
        ArrayList<String> columns = new ArrayList<>();
        //Nombres de cabezera de excel
        columns.add("id_indicador");
        columns.add("id_subindicador");
        columns.add("nro_subindicador");
        columns.add("año");
        columns.add("ejex");
        columns.add("ejey");
        columns.add("num_grafico");
        columns.add("dato");
        columns.add("medida");
        int cellIndex = 0;
        for (int i = 0; i < columns.size(); i++) {
            String columnName = prettyNameMapping("" + columns.get(i));
            if (!excludeColumn(columnName)) {
                HSSFCell cellA = rowA.createCell(cellIndex);
                cellA.setCellValue(new HSSFRichTextString(columnName));
                cellIndex++;
            }
        }
        insertItemToNSheet(table, sheet,columns,id);
    }

    private void insertItemToNSheet(String table, HSSFSheet sheet, ArrayList<String> columns, String id) {
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        Cursor cursor = database.rawQuery("select id_indicador,id_subindicador,nro_subindicador,anio,ejex,ejey,num_grafico,dato,medida from data_indicador where id_subindicador="+id+"", null);
        cursor.moveToFirst();
        int n=1;
        while (!cursor.isAfterLast()) {
            HSSFRow rowA = sheet.createRow(n);
            int cellIndex = 0;
            for (int j = 0; j < columns.size(); j++) {
                String columnName = "" + columns.get(j);
                if (!excludeColumn(columnName)) {
                    HSSFCell cellA = rowA.createCell(cellIndex);
                    if (cursor.getType(j) == Cursor.FIELD_TYPE_BLOB) {
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) cellIndex, n, (short) (cellIndex + 1), n + 1);
                        anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
                        patriarch.createPicture(anchor, workbook.addPicture(cursor.getBlob(j), HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        String value = cursor.getString(j);
                        if (null != mCustomFormatter) {
                            value = mCustomFormatter.process(columnName, value);
                        }
                        cellA.setCellValue(new HSSFRichTextString(value));
                    }
                    cellIndex++;
                }
            }
            n++;
            cursor.moveToNext();
        }
        cursor.close();
    }



}