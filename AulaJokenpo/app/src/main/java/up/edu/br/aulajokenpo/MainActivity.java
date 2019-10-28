package up.edu.br.aulajokenpo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int pontosJogador = 0;
    int pontosComputador = 0;

    int jogador = new Random().nextInt();

    String latitude;
    String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("jokenpo");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    Integer jogadorremoto = d.child("jogador").getValue(Integer.class);
                    Integer escolharemoto = d.child("escolha").getValue(Integer.class);

                    ImageButton btnJogador = findViewById(R.id.btnJogador);
                    ImageButton btnCopmutador = findViewById(R.id.btnComputador);

                    if (jogadorremoto.equals(jogador)){
                        btnJogador.setImageResource(escolharemoto);
                    }else{
                        btnCopmutador.setImageResource(escolharemoto);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void zerar(ImageButton btnPC, ImageButton btnJogador, TextView txtVencedor, TextView txtResultJogador, TextView txtResultPC){
        btnJogador.setImageResource(R.drawable.vazio);
        btnPC.setImageResource(R.drawable.vazio);
        txtVencedor.setText("");
        txtResultJogador.setText("Você\n"+0);
        txtResultPC.setText("Adversário\n"+0);
        pontosJogador = 0;
        pontosComputador = 0;
    }

    public void jogar (View v){

        ImageButton btnJogador = findViewById(R.id.btnJogador);
        ImageButton btnCopmutador = findViewById(R.id.btnComputador);

        int jogadaPC = new Random().nextInt(3) + 1; // Classe para randomizar do Java.

        TextView txtVencedor = findViewById(R.id.txtVencedor);
        TextView txtResultPC = findViewById(R.id.txtResultPC);
        TextView txtResultJogador = findViewById(R.id.txtResultJogador);

        Jogada jogada = new Jogada();
        jogada.jogador = jogador;

        if (v.getId() == R.id.btnPedra){
            btnJogador.setImageResource(R.drawable.pedra);
            jogada.escolha = R.drawable.pedra;
        }
        if (v.getId() == R.id.btnPapel){
            btnJogador.setImageResource(R.drawable.papel);
            jogada.escolha = R.drawable.papel;
        }
        if (v.getId() == R.id.btnTesoura){
            btnJogador.setImageResource(R.drawable.tesoura);
            jogada.escolha = R.drawable.tesoura;
        }

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("jokenpo");
        myRef.push().setValue(jogada);
/*
        if(jogadaPC == 1){
            btnCopmutador.setImageResource(R.drawable.tesoura);
        }
        if(jogadaPC == 2){
            btnCopmutador.setImageResource(R.drawable.papel);
        }
        if(jogadaPC == 3){
            btnCopmutador.setImageResource(R.drawable.pedra);
        }
*/
        if (jogadaPC == 1){
            if(v.getId() == R.id.btnPedra){
                // jogador ganha
                txtVencedor.setText(" Jogador Venceu! ");
                pontosJogador++;
            }else if(v.getId() == R.id.btnPapel){
                // PC ganha
                txtVencedor.setText(" Computador Venceu! ");
                pontosComputador++;
            }else{
                // empate
                txtVencedor.setText(" Empate! ");
            }
        }else if (jogadaPC == 2){
            if (v.getId() == R.id.btnPedra){
                // PC ganha
                txtVencedor.setText(" Computador Venceu! ");
                pontosComputador++;
            }else if(v.getId() == R.id.btnTesoura){
                //Jogador ganha
                txtVencedor.setText(" Jogador Venceu! ");
                pontosJogador++;
            }else{
                // empate
                txtVencedor.setText(" Empate! ");
            }
        }else if (jogadaPC == 3){
            if (v.getId() == R.id.btnPapel){
                //jogador ganha
                txtVencedor.setText(" Jogador Venceu! ");
                pontosJogador++;
            }else if (v.getId() == R.id.btnTesoura){
                //PC ganha
                txtVencedor.setText(" Computador Venceu! ");
                pontosComputador++;
            }else {
                // empate
                txtVencedor.setText(" Empate! ");
            }
        }

        txtResultJogador.setText("Você\n"+pontosJogador);
        txtResultPC.setText("Adversário\n"+pontosComputador);

        if (pontosJogador == 10) {
            Toast.makeText(this, "JOGADOR VENCEU", Toast.LENGTH_SHORT).show();
            zerar(btnCopmutador, btnJogador, txtVencedor, txtResultJogador, txtResultPC);
        }else if(pontosComputador == 10) {
            Toast.makeText(this, "COMPUTADOR VENCEU", Toast.LENGTH_SHORT).show();
            zerar(btnCopmutador, btnJogador, txtVencedor, txtResultJogador, txtResultPC);
        }else {
            Toast.makeText(this, "EMPATE", Toast.LENGTH_SHORT).show();
            zerar(btnCopmutador, btnJogador, txtVencedor, txtResultJogador, txtResultPC);
        }
    }
}
