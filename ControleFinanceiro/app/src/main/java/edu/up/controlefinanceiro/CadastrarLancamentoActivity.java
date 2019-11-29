package edu.up.controlefinanceiro;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastrarLancamentoActivity extends AppCompatActivity {

    EntradaSaida entradaSaida = new EntradaSaida();
    String montarData;

    private String latitude = null;
    private String longitude = null;

    private Usuario usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_entrada_saida);

        if (getIntent().getExtras() != null){
            // Verificar usuario Logado
            usuarioLogado = (Usuario) getIntent().getExtras().getSerializable("usuarioLogado");
            if(usuarioLogado != null){
                entradaSaida.setEmail(usuarioLogado.getEmail());
            }else{
                logout();
            }
        }

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

    private void logout(){
        Intent intent = new Intent(this, LoginActivity.class);
        // Metodos para passar paramentros entre telas/activitys.
        Bundle bundle = new Bundle();
        bundle.putSerializable("logout", true);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // SETA Voltar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android - Default
                //startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                //finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                abrirTelaPrincipal();
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
        abrirTelaPrincipal();
    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuarioLogado", (Serializable) usuarioLogado);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
