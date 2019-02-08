package com.example.autimio.glicomonitor.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.autimio.glicomonitor.models.Glicose;
import com.example.autimio.glicomonitor.models.Imc;
import com.example.autimio.glicomonitor.models.Insulina;
import com.example.autimio.glicomonitor.models.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by autimio on 01/11/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_glicomonitor";
    private static final String TABLE_GLICOSE = "glicose";
    private static final String TABLE_IMC = "imc";
    private static final String TABLE_INSULINA = "insulina";
    private static final String TABLE_USUARIO = "usuario";


    // Tabela Glicose, identificador colunas
    private static final String KEY_ID_GLICOSE = "id_glicose";
    private static final String KEY_REFEICAO_GLICOSE = "refeicao_glicose";
    private static final String KEY_TAXA_GLICOSE = "taxa_glicose";
    private static final String KEY_DATA_GLICOSE = "data_glicose";
    private static final String KEY_HORA_GLICOSE = "hora_glicose";

    // Tabela Imc, identificador colunas
    private static final String KEY_ID_IMC = "id_imc";
    private static final String KEY_ALTURA_IMC = "altura_imc";
    private static final String KEY_PESO_IMC = "peso_imc";
    private static final String KEY_DATA_IMC = "data_imc";
    private static final String KEY_HORA_IMC = "hora_imc";

    // Tabela Insulina, identificador colunas
    private static final String KEY_ID_INSULINA = "id_insulina";
    private static final String KEY_QTD_INSULINA = "peso_insulina";
    private static final String KEY_DATA_INSULINA = "data_insulina";
    private static final String KEY_HORA_INSULINA = "hora_insulina";

//    // Tabela Usuários, identificador colunas
    private static final String KEY_ID_USUARIO = "id";
    private static final String KEY_NOME = "nome";
    private static final String KEY_DATA_NASCIMENTO = "data_nascimento";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SENHA = "senha";

    protected SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_GLICOSE_TABLE = "CREATE TABLE " + TABLE_GLICOSE + "("
                + KEY_ID_GLICOSE + " INTEGER PRIMARY KEY,"
                + KEY_REFEICAO_GLICOSE + " TEXT,"
                + KEY_TAXA_GLICOSE + " REAL,"
                + KEY_DATA_GLICOSE + " DATE,"
                + KEY_HORA_GLICOSE + " TEXT"
                + ")";
        db.execSQL(CREATE_GLICOSE_TABLE);

        String CREATE_IMC_TABLE = "CREATE TABLE " + TABLE_IMC + "("
                + KEY_ID_IMC + " INTEGER PRIMARY KEY,"
                + KEY_ALTURA_IMC + " REAL,"
                + KEY_PESO_IMC + " REAL,"
                + KEY_DATA_IMC + " DATE,"
                + KEY_HORA_IMC + " TEXT"
                + ")";
        db.execSQL(CREATE_IMC_TABLE);

        String CREATE_INSULINA_TABLE = "CREATE TABLE " + TABLE_INSULINA + "("
                + KEY_ID_INSULINA + " INTEGER PRIMARY KEY,"
                + KEY_QTD_INSULINA + " REAL,"
                + KEY_DATA_INSULINA + " DATE,"
                + KEY_HORA_INSULINA + " TEXT"
                + ")";
        db.execSQL(CREATE_INSULINA_TABLE);

        String CREATE_USUARIO_TABLE = "CREATE TABLE " + TABLE_USUARIO + "("
                + KEY_ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NOME + " VARCHAR( 20 ) NOT NULL,"
               // + KEY_DATA_NASCIMENTO + " DATE,"
               // + KEY_EMAIL + " TEXT,"
                + KEY_SENHA + " VARCHAR( 8 )"
                + ")";
        db.execSQL(CREATE_USUARIO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GLICOSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSULINA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIO);
        onCreate(db);
    }

    /*
     * Operações CRUD(Create, Read, Update, Delete)
     */

    public void addGlicose(Glicose glicose) {
        SQLiteDatabase db = this.getWritableDatabase(); // indica que o BD será utilizado como leitura e escrita

        ContentValues values = new ContentValues();
        values.put(KEY_REFEICAO_GLICOSE, glicose.getRefeicao());
        values.put(KEY_TAXA_GLICOSE, glicose.getTaxaDeGlicose());
        values.put(KEY_DATA_GLICOSE, glicose.getData());
        values.put(KEY_HORA_GLICOSE, glicose.getHora());

        db.insert(TABLE_GLICOSE, null, values);
        db.close(); // Closing database connection
    }

    public void addImc(Imc imc) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ALTURA_IMC, imc.getAltura());
        values.put(KEY_PESO_IMC, imc.getPeso());
        values.put(KEY_DATA_IMC, imc.getData());
        values.put(KEY_HORA_IMC, imc.getHora());

        db.insert(TABLE_IMC, null, values);
        db.close(); // Closing database connection
    }

    public void addInsulina(Insulina insulina) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_QTD_INSULINA, insulina.getQtdIsulina());
        values.put(KEY_DATA_INSULINA, insulina.getData());
        values.put(KEY_HORA_INSULINA, insulina.getHora());

        db.insert(TABLE_INSULINA, null, values);
        db.close(); // Closing database connection
    }

    public Glicose getGlicose(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GLICOSE, new String[]
                        {KEY_ID_IMC, KEY_REFEICAO_GLICOSE, KEY_TAXA_GLICOSE, KEY_DATA_GLICOSE, KEY_HORA_GLICOSE}, KEY_ID_GLICOSE + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Glicose glicose = new Glicose();
        glicose.setId(cursor.getInt(0));
        glicose.setRefeicao(cursor.getString(1));
        glicose.setTaxaDeGlicose(cursor.getString(2));
        glicose.setData(cursor.getString(3));
        glicose.setHora(cursor.getString(4));

        return glicose;
    }

    public Imc getImc(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_IMC, new String[]
                        {KEY_ID_IMC, KEY_ALTURA_IMC, KEY_PESO_IMC, KEY_DATA_IMC, KEY_HORA_IMC}, KEY_ID_IMC + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Imc imc = new Imc();
        imc.setId(cursor.getInt(0));
        imc.setAltura(cursor.getString(1));
        imc.setPeso(cursor.getString(2));
        imc.setData(cursor.getString(3));
        imc.setHora(cursor.getString(4));

        return imc;
    }

    public Insulina getInsulina(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_INSULINA, new String[]{KEY_ID_INSULINA, KEY_QTD_INSULINA, KEY_DATA_INSULINA, KEY_HORA_INSULINA}, KEY_ID_INSULINA + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Insulina insulina = new Insulina();
        insulina.setId(cursor.getInt(0));
        insulina.setQtdIsulina(cursor.getString(1));
        insulina.setData(cursor.getString(2));
        insulina.setHora(cursor.getString(3));

        return insulina;
    }

    public List<Glicose> getAllGlicose() {

        List<Glicose> glicoseList = new ArrayList<Glicose>();

        String selectQuery = "SELECT  * FROM " + TABLE_GLICOSE;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Glicose glicose = new Glicose();

                glicose.setId(cursor.getInt(0));
                glicose.setRefeicao(cursor.getString(1));
                glicose.setTaxaDeGlicose(cursor.getString(2));
                glicose.setData(cursor.getString(3));
                glicose.setHora(cursor.getString(4));

                glicoseList.add(glicose);

            } while (cursor.moveToNext());
        }

        return glicoseList;
    }

    public List<Imc> getAllImc() {
        List<Imc> imcList = new ArrayList<Imc>();

        String selectQuery = "SELECT  * FROM " + TABLE_IMC;

        SQLiteDatabase db = this.getWritableDatabase(); // indica que o BD será utilizado como leitura e escrita

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Imc imc = new Imc();

                imc.setId(cursor.getInt(0));
                imc.setAltura(cursor.getString(1));
                imc.setPeso(cursor.getString(2));
                imc.setData(cursor.getString(3));
                imc.setHora(cursor.getString(4));

                imcList.add(imc);

            } while (cursor.moveToNext());
        }

        return imcList;
    }

    public List<Insulina> getAllInsulina() {
        List<Insulina> insulinaList = new ArrayList<Insulina>();
        String selectQuery = "SELECT  * FROM " + TABLE_INSULINA;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Insulina insulina = new Insulina();

                insulina.setId(cursor.getInt(0));
                insulina.setQtdIsulina(cursor.getString(1));
                insulina.setData(cursor.getString(2));
                insulina.setHora(cursor.getString(3));

                insulinaList.add(insulina);

            } while (cursor.moveToNext());
        }

        return insulinaList;
    }

    public int updateGlicose(Glicose glicose) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REFEICAO_GLICOSE, glicose.getRefeicao());
        values.put(KEY_TAXA_GLICOSE, glicose.getTaxaDeGlicose());
        values.put(KEY_DATA_GLICOSE, glicose.getData());
        values.put(KEY_HORA_GLICOSE, glicose.getHora());

        return db.update(TABLE_GLICOSE, values, KEY_ID_GLICOSE + " = ?",
                new String[]{String.valueOf(glicose.getId())});


    }

    public int updateImc(Imc imc) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ALTURA_IMC, imc.getAltura());
        values.put(KEY_PESO_IMC, imc.getPeso());
        values.put(KEY_DATA_IMC, imc.getData());
        values.put(KEY_HORA_IMC, imc.getHora());

        return db.update(TABLE_IMC, values, KEY_ID_IMC + " = ?",
                new String[]{String.valueOf(imc.getId())});
    }

    public int updateInsulina(Insulina insulina) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_QTD_INSULINA, insulina.getQtdIsulina());
        values.put(KEY_DATA_INSULINA, insulina.getData());
        values.put(KEY_HORA_INSULINA, insulina.getHora());

        return db.update(TABLE_INSULINA, values, KEY_ID_INSULINA + " = ?",
                new String[]{String.valueOf(insulina.getId())});
    }

    public void deleteGlicose(Glicose glicose) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GLICOSE, KEY_ID_GLICOSE + " = ?",
                new String[]{String.valueOf(glicose.getId())});
        db.close();
    }

    public void deleteImc(Imc imc) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_IMC, KEY_ID_IMC + " = ?",
                new String[]{String.valueOf(imc.getId())});
        db.close();
    }

    public void deleteInsulina(Insulina insulina) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INSULINA, KEY_ID_INSULINA + " = ?",
                new String[]{String.valueOf(insulina.getId())});
        db.close();
    }

    public int getGlicoseCount() {

        String countQuery = "SELECT  * FROM " + TABLE_GLICOSE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int getImcCount() {

        String countQuery = "SELECT  * FROM " + TABLE_IMC;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public int getInsulinaCount() {

        String countQuery = "SELECT  * FROM " + TABLE_INSULINA;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    public SQLiteDatabase getDatabase() {
        if (database == null) {
            database = getWritableDatabase();
        }
        return database;
    }
}