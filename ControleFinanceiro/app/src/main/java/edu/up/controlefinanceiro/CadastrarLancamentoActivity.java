package edu.up.controlefinanceiro;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

public class CadastrarLancamentoActivity extends AppCompatActivity {

    EntradaSaida entradaSaida = new EntradaSaida();
    String montarData;

    private String latitude = null;
    private String longitude = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_entrada_saida);

        // Comunicando com o GPS do Celular
        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = "" + location.getLatitude();
                longitude = "" + location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, ll);
        // GPS

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        // Evento de Selecionar uma Data do calendario
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                if(dayOfMonth < 10){
                    montarData = "0" + dayOfMonth +"/"+ (month+1) +"/"+ year;
                }else{
                    montarData = dayOfMonth +"/"+ (month+1) +"/"+ year;
                }

            }
        });

        // SETA voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true); // Ativar o botão
        getSupportActionBar().setTitle("Gerar Lançamento"); // Titulo para ser exibido na sua Action Bar em frente à seta
    }

    // SETA Voltar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android - Default
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    public void btnAddLocalizacao(View view){
        TextView txtLatitude = findViewById(R.id.txtLatitude);
        TextView txtLongitude = findViewById(R.id.txtLongitude);
        txtLatitude.setText("Latitude: " + latitude);
        txtLongitude.setText("Longitude: " + longitude);
        entradaSaida.setLatitude(latitude);
        entradaSaida.setLongitude(longitude);
    }

    public void btnSalvar(View view){
        RadioGroup rg = findViewById(R.id.radioGroup);

        // Check which radio button was clicked
        switch(rg.getCheckedRadioButtonId()) {
            case R.id.rbEntrada:
                entradaSaida.setTipo(Long.valueOf(0));
                break;
            case R.id.rbSaida:
                entradaSaida.setTipo(Long.valueOf(1));
                break;
        }

        TextView txtValor = findViewById(R.id.txtValor);
        TextView txtDescricao = findViewById(R.id.txtDesc);

        if(montarData == null){
            Date data = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            entradaSaida.setData(formatter.format(data));
        }else{
            entradaSaida.setData(montarData);
        }
        entradaSaida.setValor(Double.parseDouble(txtValor.getText().toString()));
        entradaSaida.setDescricao(txtDescricao.getText().toString());

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(); // Captura/Conecta com a Base de Dados/Firebase
        mDatabase.child("EntradasSaidas").push().setValue(entradaSaida);
        abrirCadLancamento();
    }

    private void abrirCadLancamento(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
