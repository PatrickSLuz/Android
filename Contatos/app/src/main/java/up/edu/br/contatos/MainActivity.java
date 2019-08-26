package up.edu.br.contatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import up.edu.br.contatos.DAO.Conexao;
import up.edu.br.contatos.DAO.ContatoDAO;
import up.edu.br.contatos.model.Contato;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Conexao(getApplicationContext(),"contato.db", null, 1);
    }

    public void salvar(View view){

        TextView txtNome = findViewById(R.id.txtNome);
        TextView txtTelefone = findViewById(R.id.txtTelefone);
        TextView txtTipo = findViewById(R.id.txtTipo);

        Contato contato = new Contato();

        contato.setNome(txtNome.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());
        contato.setTipo(txtTipo.getText().toString());

        ContatoDAO contatoDAO = ContatoDAO.criarInstancia();
        contatoDAO.salvar(contato);
    }
}
