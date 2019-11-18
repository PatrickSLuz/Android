package edu.up.controlefinanceiro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastrarEntradaSaida extends AppCompatActivity {

    EntradaSaida entradaSaida = new EntradaSaida();

    String dataSelecionada = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_entrada_saida);

        TextView textData = findViewById(R.id.textData);

        // Verificando se tem algum paramentro que foi passado para chamar esta tela.
        if (getIntent().getExtras() != null){
            dataSelecionada = getIntent().getExtras().getSerializable("strDataSelecionada").toString();

            // Popular campos
            textData.setText(dataSelecionada);
        }
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
        TextView txtDescricao = findViewById(R.id.txtDescricao);

        entradaSaida.setData(dataSelecionada);
        entradaSaida.setValor(Double.parseDouble(txtValor.getText().toString()));
        entradaSaida.setDescricao(txtDescricao.getText().toString());

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(); // Captura/Conecta com a Base de Dados/Firebase
        mDatabase.child("EntradasSaidas").push().setValue(entradaSaida);
    }
}
