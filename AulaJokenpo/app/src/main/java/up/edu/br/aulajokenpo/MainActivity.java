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

    int jogador = new Random().nextInt();

    String latitude;
    String longitude;

    int jogadaPC = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Comunicando com o GPS do Celular
        // Responsável por fazer a ponte entre o aplication
        // e o gps do smartphone
        LocationManager lm = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        // Escuta as alterações de posições de GPS
        // Quando uma nova posição é recebida
        // o método location changed é chamad
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

        // Permissões necessárias para acessar o gps do smartphone
        String[] permissoes =
                {Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION};

        // solicita a aprovação do usuário para capturar as posições de gps
        ActivityCompat.requestPermissions(this, permissoes, 1);

        //valida se tem a permissão de acesso ao gps
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // regra para solicitar as posições de gps
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
                        jogadaPC = escolharemoto;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    public void jogar (View v){

        ImageButton btnJogador = findViewById(R.id.btnJogador);

        Jogada jogada = new Jogada();
        jogada.setJogador(jogador);
        jogada.setLatitude(latitude);
        jogada.setLongitude(longitude);

        if (v.getId() == R.id.btnPedra){
            btnJogador.setImageResource(R.drawable.pedra);
            jogada.setEscolha(R.drawable.pedra);
        }
        if (v.getId() == R.id.btnPapel){
            btnJogador.setImageResource(R.drawable.papel);
            jogada.setEscolha(R.drawable.papel);
        }
        if (v.getId() == R.id.btnTesoura){
            btnJogador.setImageResource(R.drawable.tesoura);
            jogada.setEscolha(R.drawable.tesoura);
        }

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("jokenpo");
        myRef.push().setValue(jogada);

/*        // Fazendo Verificação de quem Vence uma Rodada
        if(jogadaPC != 0){
            if (jogadaPC == R.drawable.tesoura){
                if(v.getId() == R.id.btnPedra){
                    // jogador ganha
                    Toast.makeText(this, "Você Venceu!", Toast.LENGTH_SHORT).show();
                }else if(v.getId() == R.id.btnPapel){
                    // PC ganha
                    Toast.makeText(this, "Adversário Venceu!", Toast.LENGTH_SHORT).show();
                }else{
                    // empate
                    Toast.makeText(this, "Empate!", Toast.LENGTH_SHORT).show();
                }
            }else if (jogadaPC == R.drawable.papel){
                if (v.getId() == R.id.btnPedra){
                    // PC ganha
                    Toast.makeText(this, "Adversário Venceu!", Toast.LENGTH_SHORT).show();
                }else if(v.getId() == R.id.btnTesoura){
                    //Jogador ganha
                    Toast.makeText(this, "Você Venceu!", Toast.LENGTH_SHORT).show();
                }else{
                    // empate
                    Toast.makeText(this, "Empate!", Toast.LENGTH_SHORT).show();
                }
            }else if (jogadaPC == R.drawable.pedra){
                if (v.getId() == R.id.btnPapel){
                    //jogador ganha
                    Toast.makeText(this, "Você Venceu!", Toast.LENGTH_SHORT).show();
                }else if (v.getId() == R.id.btnTesoura){
                    //PC ganha
                    Toast.makeText(this, "Adversário Venceu!", Toast.LENGTH_SHORT).show();
                }else {
                    // empate
                    Toast.makeText(this, "Empate!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        */
    }
}
