package com.example.autimio.glicomonitor.views;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.example.autimio.glicomonitor.R;
import com.example.autimio.glicomonitor.models.Insulina;
import com.google.gson.Gson;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class FormInsulinaActivity extends AppCompatActivity {

    EditText time;
    EditText date;
    DatePickerDialog datePickerDialog;
    EditText editTextQtdInsulina;
    EditText editTextData;
    EditText editTextHora;
    Button buttonSalvar;
    Gson gson = new Gson();
    Integer insulinaId;

    public static String completeToLeft(String value, char c, int size) {
        String result = value;
        while (result.length() < size) {
            result = c + result;
        }
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_insulina);

        editTextQtdInsulina = (EditText) findViewById(R.id.editTextQtdInsulina);
        date = (EditText) findViewById(R.id.date);
        time = (EditText) findViewById(R.id.time);
        buttonSalvar = (Button) findViewById(R.id.buttonSalvarInsulina);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(FormInsulinaActivity.this,
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
                mTimePicker = new TimePickerDialog(FormInsulinaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String strM = "" + selectedMinute;
                        String strH = "" + selectedHour;

                        strM = completeToLeft(strM, '0', 2);
                        strH = completeToLeft(strH, '0', 2);

                        time.setText(strH + ":" + strM);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos()){

                    Insulina insulina = new Insulina();

                    if(insulinaId != null)
                    {
                        insulina.setId(insulinaId);
                    }

                    insulina.setQtdIsulina(editTextQtdInsulina.getText().toString());
                    insulina.setData(date.getText().toString());
                    //insulina.setData(editTextData.getText().toString());
                    insulina.setHora(time.getText().toString());
                    //insulina.setHora(editTextHora.getText().toString());

                    Intent returnIntent = new Intent();

                    returnIntent.putExtra("insulina",gson.toJson(insulina));

                    setResult(Activity.RESULT_OK,returnIntent);

                    Toast.makeText(getApplicationContext(), "Insulina adicionada!", Toast.LENGTH_LONG).show();

                    finish();
                }
            }
        });

        Bundle b = getIntent().getExtras();

        if(b != null) {

            String result = b.getString("insulina");
            Insulina insulina = gson.fromJson(result, Insulina.class);
            insulinaId = insulina.getId();

            editTextQtdInsulina.setText(insulina.getQtdIsulina());
            date.setText(insulina.getData());
            //editTextData.setText(insulina.getData());
            time.setText(insulina.getHora());
            //editTextHora.setText(insulina.getHora());
        }
    }

    private boolean validarCampos(){
        return true;
    }
}

