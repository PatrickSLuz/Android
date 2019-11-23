package edu.up.controlefinanceiro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Double totalEntradaMes = 0.0;
    private Double totalSaidaMes = 0.0;

    private List<EntradaSaida> lancamentos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Controle Financeiro"); // Titulo para ser exibido na sua Action Bar

        final TextView txtTotalEntradaMes = findViewById(R.id.txtTotalEntradaMes);
        final TextView txtTotalSaidaMes = findViewById(R.id.txtTotalSaidaMes);

        // Mes e Ano Atual
        TextView txtMesAnoAtual = findViewById(R.id.txtMesAnoAtual);
        Date dataAtual = new Date();
        final String mesEanoAtual = new SimpleDateFormat("MM-yyyy").format(dataAtual);
        String ano = new SimpleDateFormat("yyyy").format(dataAtual);
        txtMesAnoAtual.setText(obterNomeMes(dataAtual.getMonth()) + " de " + ano);

        // Menu 3 pontinhos
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true); // Ativar o botão

        // Captura/Conecta com a Base de Dados/Firebase
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        // Read from the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalEntradaMes = 0.0;
                totalSaidaMes = 0.0;
                for (DataSnapshot ds : dataSnapshot.child("EntradasSaidas").getChildren()){
                    String data = ds.child("data").getValue(String.class);
                    String mesEano = data.substring(3).replace("/","-");
                    if(mesEano.equals(mesEanoAtual)){
                        Long tipo = ds.child("tipo").getValue(Long.class);
                        Double valor = ds.child("valor").getValue(Double.class);
                        if(tipo == 0){
                            totalEntradaMes += valor;
                        }else{
                            totalSaidaMes += valor;
                        }
                        String desc = ds.child("descricao").getValue(String.class);
                        lancamentos.add(new EntradaSaida(tipo, data, valor, desc));
                    }
                }
                txtTotalEntradaMes.setText("+ "+totalEntradaMes);
                txtTotalSaidaMes.setText("- "+totalSaidaMes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public String obterNomeMes(int mes){
        String[] meses = {"Janeiro",
        "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        return meses[mes];
    }

    public void btnCadLancamento(View view){
        Intent intent = new Intent(this, CadastrarLancamentoActivity.class);
        startActivity(intent);
    }

    public void btnDetalheMes(View view){
        Intent intent = new Intent(this, DetalhesMesActivity.class);
        // Metodos para passar paramentros entre telas/activitys.
        Bundle bundle = new Bundle();
        bundle.putSerializable("lancamentos", (Serializable) lancamentos);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
