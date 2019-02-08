package com.example.autimio.glicomonitor.views;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.example.autimio.glicomonitor.R;
import com.example.autimio.glicomonitor.adapters.InsulinaAdapter;
import com.example.autimio.glicomonitor.models.Insulina;
import com.example.autimio.glicomonitor.utils.DatabaseHelper;
import com.example.autimio.glicomonitor.utils.DividerItemDecoration;
import com.example.autimio.glicomonitor.utils.RecyclerTouchListener;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class ViewInsulinaActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    InsulinaAdapter mInsulinaAdapter;
    FloatingActionButton addInsulina;
    Gson gson = new Gson();
    DatabaseHelper db = new DatabaseHelper(this);
    private List<Insulina> insulinas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view_insulina);

        insulinas = db.getAllInsulina();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        addInsulina = (FloatingActionButton) findViewById(R.id.adicionarInsulina);

        addInsulina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewInsulinaActivity.this, FormInsulinaActivity.class);
                startActivityForResult(i, 1);
            }
        });

        mInsulinaAdapter = new InsulinaAdapter(insulinas);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mInsulinaAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override

            public void onClick(View view, int position) {
                Insulina insulina = insulinas.get(position);
                Intent i = new Intent(ViewInsulinaActivity.this, FormInsulinaActivity.class);
                i.putExtra("insulina", gson.toJson(insulina));
                startActivityForResult(i, 2);
                Toast.makeText(getApplicationContext(), "ID selecionado: " + insulina.getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, final int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewInsulinaActivity.this);
                builder.setTitle("Opa!");
                builder.setMessage("Tem certeza que quer deletar este registro de insulina? ");
                Vibrar();

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Insulina insulina = insulinas.get(position);
                        insulinas.remove(insulina);
                        db.deleteInsulina(insulina);
                        mInsulinaAdapter.notifyDataSetChanged();
                        Vibrar();
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

                AlertDialog alerta = builder.create();
                alerta.show();
            }
        }));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            String result = data.getStringExtra("insulina");
            Insulina insulina = gson.fromJson(result, Insulina.class);
            if(requestCode == 1){
                db.addInsulina(insulina);
            }
            else if (requestCode == 2){
                db.updateInsulina(insulina);
            }
            insulinas.clear();
            insulinas.addAll(db.getAllInsulina());
            mInsulinaAdapter.notifyDataSetChanged();
        }
    }

    private void Vibrar()
    {
        Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long milliseconds = 120;//'30' é o tempo em milissegundos, é basicamente o tempo de duração da vibração. portanto, quanto maior este numero, mais tempo de vibração você irá ter
        rr.vibrate(milliseconds);
    }
}