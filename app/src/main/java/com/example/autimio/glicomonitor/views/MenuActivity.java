package com.example.autimio.glicomonitor.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.autimio.glicomonitor.R;

public class MenuActivity extends AppCompatActivity {

    private Context context;
    private AlertDialog.Builder alert;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageView button_glicose = (ImageView) findViewById(R.id.glico_image_button);
        ImageView button_insulina = (ImageView) findViewById(R.id.insulina_image_button);
        ImageView button_imc = (ImageView) findViewById(R.id.imc_image_button);

        TextView textGlico = (TextView) findViewById(R.id.text_glicose);
        TextView textImc = (TextView) findViewById(R.id.text_imc);
        TextView textInsulina = (TextView) findViewById(R.id.text_insulina);

        button_glicose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewGlicoseActivity.class);
                startActivity(i);
            }
        });
        textGlico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewGlicoseActivity.class);
                startActivity(i);
            }
        });


        button_insulina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewInsulinaActivity.class);
                startActivity(i);
            }
        });
        textInsulina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewInsulinaActivity.class);
                startActivity(i);
            }
        });

        button_imc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewImcActivity.class);
                startActivity(i);
            }
        });
        textImc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewImcActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.paginaface:

                return true;

            case R.id.sobre:
                new AlertDialog.Builder(this)
                        .setTitle("Glico Monitor")
                        .setMessage("\nPara todos os usuários que possuem diabetes e precisam de " +
                                "um acompanhamento dos resultados de forma organizada, prática e simples." +
                                "\n\n\nDesenvolvido por Autímio M. B. Filho \n\n Versão: 1.0")
                        .setIcon(R.drawable.glicomonitor2)
                        .setCancelable(false)
                        .setPositiveButton("Voltar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //code if yes
                            }
                        })
                        .show();

                return true;

            default:

                return false;

        }
    }

}