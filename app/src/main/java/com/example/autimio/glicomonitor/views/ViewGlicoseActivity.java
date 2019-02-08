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
import com.example.autimio.glicomonitor.adapters.GlicoseAdapter;
import com.example.autimio.glicomonitor.models.Glicose;
import com.example.autimio.glicomonitor.utils.DatabaseHelper;
import com.example.autimio.glicomonitor.utils.DividerItemDecoration;
import com.example.autimio.glicomonitor.utils.RecyclerTouchListener;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class ViewGlicoseActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    GlicoseAdapter mGlicoseAdapter;
    FloatingActionButton addGlicose;
    Gson gson = new Gson();
    DatabaseHelper db = new DatabaseHelper(this);
    private List<Glicose> glicoses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view_glicose);

        glicoses = db.getAllGlicose();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        addGlicose = (FloatingActionButton) findViewById(R.id.adicionarGlicose);

        addGlicose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewGlicoseActivity.this, FormGlicoseActivity.class);
                startActivityForResult(i, 1);
            }
        });

        mGlicoseAdapter = new GlicoseAdapter(glicoses);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mGlicoseAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override

            public void onClick(View view, int position) {
                Glicose glicose = glicoses.get(position);
                Intent i = new Intent(ViewGlicoseActivity.this, FormGlicoseActivity.class);
                i.putExtra("glicose", gson.toJson(glicose));
                startActivityForResult(i, 2);
                Toast.makeText(getApplicationContext(), "ID Glicose selecionado: " + glicose.getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, final int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewGlicoseActivity.this);
                builder.setTitle("Opa!");
                builder.setMessage("Tem certeza que quer deletar este registro de glicose? ");
                Vibrar();

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Glicose glicose = glicoses.get(position);
                        glicoses.remove(glicose);
                        db.deleteGlicose(glicose);
                        mGlicoseAdapter.notifyDataSetChanged();
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
            String result = data.getStringExtra("glicose");
            Glicose glicose = gson.fromJson(result, Glicose.class);
            if(requestCode == 1){
                db.addGlicose(glicose);
            }
            else if (requestCode == 2){
                db.updateGlicose(glicose);
            }
            glicoses.clear();
            glicoses.addAll(db.getAllGlicose());
            mGlicoseAdapter.notifyDataSetChanged();
        }
    }

    private void Vibrar()
    {
        Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long milliseconds = 120;//'30' é o tempo em milissegundos, é basicamente o tempo de duração da vibração. portanto, quanto maior este numero, mais tempo de vibração você irá ter
        rr.vibrate(milliseconds);
    }
}