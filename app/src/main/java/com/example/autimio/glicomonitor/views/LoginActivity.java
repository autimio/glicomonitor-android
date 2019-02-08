package com.example.autimio.glicomonitor.views;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.autimio.glicomonitor.R;
import com.example.autimio.glicomonitor.models.Usuario;
import com.example.autimio.glicomonitor.utils.UsuarioController;

public class LoginActivity extends AppCompatActivity {

    private Intent intent = null;

    private EditText editUsuario, editSenha;
    private Context context;
    private UsuarioController usuarioController;
    private AlertDialog.Builder alert;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        usuarioController = UsuarioController.getInstance(context);
        editUsuario = (EditText) findViewById(R.id.edit_text_usuario);
        editSenha = (EditText) findViewById(R.id.edit_text_senha);
        try {
            testaInicializacao();
        } catch (Exception e) {
            //exibeDialogo("Erro inicializando banco de dados");
           // e.printStackTrace();
        }
    }
    /**
     * @throws Exception
     */
    public void testaInicializacao() throws Exception {
        if (usuarioController.findAll().isEmpty()) {
            Usuario usuario = new Usuario(null, "autimio", "12345678");
            usuarioController.insert(usuario);
        }
    }
    /**
     *
     */
    public void exibeDialogo(String mensagem) {
        alert = new AlertDialog.Builder(context);
        alert.setPositiveButton("OK", null);
        alert.setMessage(mensagem);
        alert.create().show();
    }
    public void validar(View view) {
        String usuario = editUsuario.getText().toString();
        String senha = editSenha.getText().toString();
        try {
            boolean isValid = usuarioController.validaLogin(usuario, senha);
            if (isValid) {
                exibeDialogo("Usuario e senha validados com sucesso!");
            } else {
                exibeDialogo("Verifique usuario e senha!");
            }
        } catch (Exception e) {
            exibeDialogo("Erro validando usuario e senha");
            e.printStackTrace();
        }
    }


    public void login(View view) {
        intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
    }

    public void registro(View view) {
        intent = new Intent(LoginActivity.this, RegistroActivity.class);
        startActivity(intent);
    }
}
