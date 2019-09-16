package up.edu.br.contatos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import up.edu.br.contatos.DAO.Conexao;
import up.edu.br.contatos.DAO.ContatoDAO;
import up.edu.br.contatos.model.Contato;

public class MainActivity extends AppCompatActivity {

    private Contato contato;

    TextView txtNome;
    TextView txtTelefone;
    Spinner spinnerTipo;
    TextView txtCep;
    TextView txtCpf;

    String[] arraySpinnerTipo = new String[] {"Celular", "Residencial", "Fixo", "Comercial"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Conexao(getApplicationContext(),"contato.db", null, 1);

        txtNome = findViewById(R.id.txtNome);
        txtTelefone = findViewById(R.id.txtTelefone);
        spinnerTipo = findViewById(R.id.spinnerTipo);
        txtCep = findViewById(R.id.txtCep);
        txtCpf = findViewById(R.id.txtCpf);

        // Criando e Preenchendo o SPINNER:
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinnerTipo);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTipo.setAdapter(spinnerAdapter);

        // Verificando se tem algum paramentro que foi passado para chamar esta tela.
        if (getIntent().getExtras() != null){
            contato = (Contato) getIntent().getExtras().getSerializable("contato");
            // Popular campos
            txtNome.setText(contato.getNome());
            txtTelefone.setText(contato.getTelefone());

            int position = -1;
            for(int i = 0; i < arraySpinnerTipo.length; i++){
                if(contato.getTipo().equals(arraySpinnerTipo[i])){ // se o tipo cadastrado for igual a posição atual do array
                    position = i;
                    break;
                }
            }
            spinnerTipo.setSelection(position); // setando o item encontrado no Spinner

            txtCep.setText(contato.getCep());
            txtCpf.setText(contato.getCpf());
        }
    }

    public void salvar(View view){

        if (contato == null){
            contato = new Contato();
        }

        contato.setNome(txtNome.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());
        contato.setTipo(spinnerTipo.getSelectedItem().toString());
        contato.setCep(txtCep.getText().toString());
        contato.setCpf(txtCpf.getText().toString());

        ContatoDAO contatoDAO = ContatoDAO.criarInstancia();

        if(contato.getId() == null){
            contatoDAO.salvar(contato);
        }else{
            contatoDAO.alterar(contato);
        }

        Intent intent = new Intent(getApplicationContext(), listActivity.class);
        startActivity(intent);
    }
}
