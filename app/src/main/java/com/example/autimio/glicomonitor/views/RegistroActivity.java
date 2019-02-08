package com.example.autimio.glicomonitor.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.autimio.glicomonitor.R;

public class RegistroActivity extends AppCompatActivity {

    private Button button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        button = (Button) findViewById(R.id.voltamain);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Conta criada! xD", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}