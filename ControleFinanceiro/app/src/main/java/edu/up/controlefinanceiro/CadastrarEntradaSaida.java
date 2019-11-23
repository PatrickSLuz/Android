package edu.up.controlefinanceiro;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CadastrarEntradaSaida extends AppCompatActivity {

    EntradaSaida entradaSaida = new EntradaSaida();
    String montarData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_entrada_saida);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        // Evento de Selecionar uma Data do calendario
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                montarData = dayOfMonth +"/"+ (month+1) +"/"+ year;
            }
        });
    }

    public void btnSalvar(View view){
        RadioGroup rg = findViewById(R.id.radioGroup);

        // Check which radio button was clicked
        switch(rg.getCheckedRadioButtonId()) {
            case R.id.rbEntrada:
                entradaSaida.setTipo(0);
                break;
            case R.id.rbSaida:
                entradaSaida.setTipo(1);
                break;
        }

        TextView txtValor = findViewById(R.id.txtValor);
        TextView txtDescricao = findViewById(R.id.txtDesc);

        entradaSaida.setData(montarData);
        entradaSaida.setValor(Double.parseDouble(txtValor.getText().toString()));
        entradaSaida.setDescricao(txtDescricao.getText().toString());

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(); // Captura/Conecta com a Base de Dados/Firebase
        mDatabase.child("EntradasSaidas").push().setValue(entradaSaida);
    }

}
