package com.example.autimio.glicomonitor.views;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.autimio.glicomonitor.R;
import com.example.autimio.glicomonitor.models.Imc;
import com.google.gson.Gson;

import java.util.Calendar;

import static com.example.autimio.glicomonitor.views.FormInsulinaActivity.completeToLeft;

public class FormImcActivity extends AppCompatActivity {

    EditText time;
    EditText date;
    DatePickerDialog datePickerDialog;
    EditText editTextAltura;
    EditText editTextPeso;
    Button buttonSalvar;
    Gson gson = new Gson();
    Integer imcId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_imc);

        editTextAltura = (EditText) findViewById(R.id.editTextAlturaImc);
        editTextPeso = (EditText) findViewById(R.id.editTextPesoImc);
        date = (EditText) findViewById(R.id.date);
        time = (EditText) findViewById(R.id.time);
        buttonSalvar = (Button) findViewById(R.id.buttonSalvarImc);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(FormImcActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(FormImcActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String strM = "" + selectedMinute;
                        String strH = "" + selectedHour;
                        strM = completeToLeft(strM, '0', 2);
                        strH = completeToLeft(strH, '0', 2);

                        time.setText(strH + ":" + strM);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Selecione a hora:");
                mTimePicker.show();
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos()) {

                    Imc imc = new Imc();

                    if (imcId != null) {
                        imc.setId(imcId);
                    }
                    imc.setAltura(editTextAltura.getText().toString());
                    imc.setPeso(editTextPeso.getText().toString());
                    imc.setData(date.getText().toString());
                    imc.setHora(time.getText().toString());

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("imc", gson.toJson(imc));
                    setResult(Activity.RESULT_OK, returnIntent);
                    Toast.makeText(getApplicationContext(), "IMC adicionada!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        Bundle b = getIntent().getExtras();

        if(b != null) {

            String result = b.getString("imc");
            Imc imc = gson.fromJson(result, Imc.class);
            imcId = imc.getId();

            editTextAltura.setText(imc.getAltura());
            editTextPeso.setText(imc.getPeso());
            date.setText(imc.getData());
            time.setText(imc.getHora());
        }
    }
    private boolean validarCampos(){
        return true;
    }
}
