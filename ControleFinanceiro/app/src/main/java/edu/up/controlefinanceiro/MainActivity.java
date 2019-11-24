package edu.up.controlefinanceiro;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
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

    String mesEanoAtual = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mes e Ano escolhido para exibir
        TextView txtMesAnoAtual = findViewById(R.id.txtMesAnoAtual);

        if (getIntent().getExtras() != null){
            mesEanoAtual = (String) getIntent().getExtras().getSerializable("mesEano");
            String mes = obterNomeMes(Integer.parseInt(mesEanoAtual.substring(0,2)) -1);
            String ano = mesEanoAtual.substring(3);
            txtMesAnoAtual.setText(mes + " de " + ano);
        }else{
            Date dataAtual = new Date();
            mesEanoAtual = new SimpleDateFormat("MM-yyyy").format(dataAtual);
            String ano = new SimpleDateFormat("yyyy").format(dataAtual);
            txtMesAnoAtual.setText(obterNomeMes(dataAtual.getMonth()) + " de " + ano);
        }

        getSupportActionBar().setTitle("Controle Financeiro"); // Titulo para ser exibido na sua Action Bar

        final TextView txtTotalEntradaMes = findViewById(R.id.txtTotalEntradaMes);
        final TextView txtTotalSaidaMes = findViewById(R.id.txtTotalSaidaMes);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R. menu.main, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_detalhe:
                abrirLancamentos();
                return true;
            case R.id.action_outro_mes:
                trocarMes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String obterNomeMes(int mes){
        String[] meses = {"Janeiro",
        "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        return meses[mes];
    }

    public int obterNumeroMes(String mes){
        int n = -1;
        String[] meses = {"Janeiro",
                "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        for (int i = 0; i < 12; i++){
            if(meses[i].equals(mes)){
                n = i+1;
                break;
            }
        }
        return n;
    }

    public void btnCadLancamento(View view){
        Intent intent = new Intent(this, CadastrarLancamentoActivity.class);
        startActivity(intent);
    }

    public void btnDetalheMes(View view){
       abrirLancamentos();
    }

    private void trocarMes(){
        AlertDialog alerta;

        //Define o layout do AlertDialog
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_layout, null);
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //definir layout personalizado
        builder.setView(dialogView);
        //define o titulo
        builder.setTitle("Selecionar outro Mês");

        final TextView txtAno = dialogView.findViewById(R.id.txtAno);
        final Spinner spMes = dialogView.findViewById(R.id.spMes);

        //define um botão como positivo
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                int mes = obterNumeroMes(spMes.getSelectedItem().toString());
                String mesEanoSelecionado;
                if(mes < 10){
                    mesEanoSelecionado =  "0" + mes + "-" +txtAno.getText().toString();
                }else{
                    mesEanoSelecionado =  mes + "-" +txtAno.getText().toString();
                }
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                // Metodos para passar paramentros entre telas/activitys.
                Bundle bundle = new Bundle();
                bundle.putSerializable("mesEano", mesEanoSelecionado);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }

    private void abrirLancamentos(){
        Intent intent = new Intent(this, DetalhesMesActivity.class);
        // Metodos para passar paramentros entre telas/activitys.
        Bundle bundle = new Bundle();
        bundle.putSerializable("lancamentos", (Serializable) lancamentos);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
