package com.example.autimio.glicomonitor.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.example.autimio.glicomonitor.models.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Autímio M. B. Filho on 18/12/2017.
 */

public class UsuarioDAO extends DatabaseHelper {
    private final String TABLE = "usuario";
    public UsuarioDAO(Context context) {
        super(context);
    }

    public void insert(Usuario usuario) throws Exception {
        ContentValues values = new ContentValues();
        values.put("usuario", usuario.getUsuario());
        values.put("senha", usuario.getSenha());
        getDatabase().insert(TABLE, null, values);
    }

    public void update(Usuario usuario) throws Exception {
        ContentValues values = new ContentValues();
        values.put("usuario", usuario.getUsuario());
        values.put("senha", usuario.getSenha());
        getDatabase().update(TABLE, values, "id = ?", new String[] { "" + usuario.getId() });
    }

    public Usuario findById(Integer id) {
        String sql = "SELECT * FROM " + TABLE + " WHERE id = ?";
        String[] selectionArgs = new String[] { "" + id };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaUsuario(cursor);
    }

    public List<Usuario> findAll() throws Exception {
        List<Usuario> retorno = new ArrayList <Usuario>();
        String sql = "SELECT * FROM " + TABLE;
        Cursor cursor = getDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            retorno.add(montaUsuario(cursor));
            cursor.moveToNext();
        }
        return retorno;
    }

    public Usuario montaUsuario(Cursor cursor) {
        if (cursor.getCount() == 0) {
            return null;
        }
        Integer id = cursor.getInt(cursor.getColumnIndex("id"));
        String usuario = cursor.getString(cursor.getColumnIndex("usuario"));
        String senha = cursor.getString(cursor.getColumnIndex("senha"));
        return new Usuario(id, usuario, senha);
    }
    /**
     * @param usuario
     * @param senha
     * @return
     */
    public Usuario findByLogin(String usuario, String senha) {
        String sql = "SELECT * FROM " + TABLE + " WHERE usuario = ? AND senha = ?";
        String[] selectionArgs = new String[] { usuario, senha };
        Cursor cursor = getDatabase().rawQuery(sql, selectionArgs);
        cursor.moveToFirst();
        return montaUsuario(cursor);
    }
}
