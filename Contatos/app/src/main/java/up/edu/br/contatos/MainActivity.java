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

    Spinner spinnerTipo;
    String tipo = "Nenhum";

    private Contato contato;

    TextView txtNome;
    TextView txtTelefone;
    TextView txtCep;
    TextView txtCpf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Conexao(getApplicationContext(),"contato.db", null, 1);

        txtNome = findViewById(R.id.txtNome);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtCep = findViewById(R.id.txtCep);
        txtCpf = findViewById(R.id.txtCpf);

        // Verificando se tem algum paramentro que foi passado para chamar esta tela.
        if (getIntent().getExtras() != null){
            contato = (Contato) getIntent().getExtras().getSerializable("contato");

            txtNome.setText(contato.getNome());
            txtTelefone.setText(contato.getTelefone());
            txtCep.setText(contato.getCep());
            txtCpf.setText(contato.getCpf());

        }

        spinnerTipo = findViewById(R.id.spinnerTipo);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.tipos_contatos, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTipo.setAdapter(spinnerAdapter);

        // Capturando o que foi selecionado no Spinner
        spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipo = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void salvar(View view){

        txtNome = findViewById(R.id.txtNome);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtCep = findViewById(R.id.txtCep);
        txtCpf = findViewById(R.id.txtCpf);

        if (contato == null){
            contato = new Contato();
        }

        contato.setNome(txtNome.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());
        contato.setTipo(tipo);
        contato.setCep(txtCep.getText().toString());
        contato.setCpf(txtCpf.getText().toString());

        ContatoDAO contatoDAO = ContatoDAO.criarInstancia();
        contatoDAO.salvar(contato);

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        // Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                //REDIRECIONAR
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        Intent intent = new Intent(getApplicationContext(), listActivity.class);
        startActivity(intent);
    }
}
