package com.example.autimio.glicomonitor.utils;

import android.content.Context;

import com.example.autimio.glicomonitor.models.Usuario;

import java.util.List;

/**
 * Created by Autímio M. B. Filho on 18/12/2017.
 */

public class UsuarioController {
    private static UsuarioDAO usuarioDAO;
    private static UsuarioController instance;
    public static UsuarioController getInstance(Context context) {
        if (instance == null) {
            instance = new UsuarioController();
            usuarioDAO = new UsuarioDAO(context);
        }
        return instance;
    }
    public void insert(Usuario usuario) throws Exception {
        usuarioDAO.insert(usuario);
    }
    public void update(Usuario usuario) throws Exception {
        usuarioDAO.update(usuario);
    }
    public List<Usuario> findAll() throws Exception {
        return usuarioDAO.findAll();
    }
    public boolean validaLogin(String usuario, String senha) throws Exception {
        Usuario user = usuarioDAO.findByLogin(usuario, senha);
        if (user == null || user.getUsuario() == null || user.getSenha() == null) {
            return false;
        }
        String informado = usuario + senha;
        String esperado = user.getUsuario() + user.getSenha();
        if (informado.equals(esperado)) {
            return true;
        }
        return false;
    }
}