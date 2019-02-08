package com.example.autimio.glicomonitor.views;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.util.Calendar;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.autimio.glicomonitor.models.Glicose;
import com.google.gson.Gson;
import com.example.autimio.glicomonitor.R;
import static com.example.autimio.glicomonitor.views.FormInsulinaActivity.completeToLeft;

public class FormGlicoseActivity extends AppCompatActivity {

    EditText time;
    EditText date;
    DatePickerDialog datePickerDialog;

    String[] refeicoes={"Café da manhã","Brunch","Almoço","Lanche","Jantar", "Sobremesa", "Ceia"};

    EditText editTextRefeicao;
    EditText editTextTaxaGlicose;
    Button buttonSalvar;
    Integer glicoseId;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_glicose);

        date = (EditText) findViewById(R.id.date);
        time = (EditText) findViewById(R.id.time);
        editTextRefeicao = (EditText) findViewById(R.id.editTextRefeicao);
        editTextTaxaGlicose = (EditText) findViewById(R.id.editTextTaxaGlicose);
        buttonSalvar = (Button) findViewById(R.id.buttonSalvarGlicose);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(FormGlicoseActivity.this,
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
                mTimePicker = new TimePickerDialog(FormGlicoseActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                if(validarCampos()){

                    Glicose glicose = new Glicose();

                    if(glicoseId != null) {
                        glicose.setId(glicoseId);
                    }

                    glicose.setRefeicao(editTextRefeicao.getText().toString());
                    glicose.setTaxaDeGlicose(editTextTaxaGlicose.getText().toString());
                    glicose.setData(date.getText().toString());
                    glicose.setHora(time.getText().toString());

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("glicose",gson.toJson(glicose));
                    setResult(Activity.RESULT_OK,returnIntent);
                    Toast.makeText(getApplicationContext(), "Glicose adicionada!", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        Bundle b = getIntent().getExtras();

        if(b != null) {

            String result = b.getString("glicose");
            Glicose glicose = gson.fromJson(result, Glicose.class);
            glicoseId = glicose.getId();

            editTextRefeicao.setText(glicose.getRefeicao());
            editTextTaxaGlicose.setText(glicose.getTaxaDeGlicose());
            date.setText(glicose.getData());
            time.setText(glicose.getHora());
        }
    }

    private boolean validarCampos(){
        return true;
    }

    public void notificacao() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.journaldev.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle("");
        builder.setContentText("Your notification content here.");
        builder.setSubText("Tap to view the website.");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(1, builder.build());
    }

}
