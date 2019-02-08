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
import com.example.autimio.glicomonitor.adapters.ImcAdapter;
import com.example.autimio.glicomonitor.models.Imc;
import com.example.autimio.glicomonitor.utils.DatabaseHelper;
import com.example.autimio.glicomonitor.utils.DividerItemDecoration;
import com.example.autimio.glicomonitor.utils.RecyclerTouchListener;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class ViewImcActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ImcAdapter mImcAdapter;
    FloatingActionButton addImc;
    Gson gson = new Gson();
    DatabaseHelper db = new DatabaseHelper(this);
    private List<Imc> imcs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view_imc);

        imcs = db.getAllImc();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        addImc = (FloatingActionButton) findViewById(R.id.adicionarImc);

        addImc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewImcActivity.this, FormImcActivity.class);
                startActivityForResult(i, 1);
            }
        });

        mImcAdapter = new ImcAdapter(imcs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mImcAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override

            public void onClick(View view, int position) {
                Imc imc = imcs.get(position);
                Intent i = new Intent(ViewImcActivity.this, FormImcActivity.class);
                i.putExtra("imc", gson.toJson(imc));
                startActivityForResult(i, 2);
                Toast.makeText(getApplicationContext(), "ID selecionado: " + imc.getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, final int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewImcActivity.this);
                builder.setTitle("Opa!");
                builder.setMessage("Tem certeza que quer deletar este registro de IMC? ");
                Vibrar();

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Imc imc = imcs.get(position);
                        imcs.remove(imc);
                        db.deleteImc(imc);
                        mImcAdapter.notifyDataSetChanged();
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
            String result = data.getStringExtra("imc");
            Imc imc = gson.fromJson(result, Imc.class);
            if(requestCode == 1){
                db.addImc(imc);
            }
            else if (requestCode == 2){
                db.updateImc(imc);
            }
            imcs.clear();
            imcs.addAll(db.getAllImc());
            mImcAdapter.notifyDataSetChanged();
        }
    }

    private void Vibrar()
    {
        Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long milliseconds = 120;//'30' é o tempo em milissegundos, é basicamente o tempo de duração da vibração. portanto, quanto maior este numero, mais tempo de vibração você irá ter
        rr.vibrate(milliseconds);
    }
}