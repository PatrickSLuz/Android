package up.edu.br.contatos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import up.edu.br.contatos.DAO.Conexao;
import up.edu.br.contatos.DAO.ContatoDAO;
import up.edu.br.contatos.model.Contato;

public class listActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abrir outa tela
                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(it);
            }
        });
        // criando uma conexão com o Banco de Dados
        new Conexao(getApplicationContext(), "contato.db", null, 1);

        // Lista
        ListView listContatos = findViewById(R.id.listContatos);

        listContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contato c = (Contato) parent.getItemAtPosition(position);

                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                // Metodos para passar paramentros entre telas/activitys.
                Bundle bundle = new Bundle();
                bundle.putSerializable("contato", c);
                it.putExtras(bundle);
                startActivity(it);
            }
        });

        // Itens da Lista
        ArrayAdapter<Contato> arrayContatos = new ArrayAdapter<Contato>(getApplicationContext(), android.R.layout.simple_list_item_1);

        // Capturando as informações do BD
        ContatoDAO contatoDAO = ContatoDAO.criarInstancia();
        List<Contato> contatos = contatoDAO.listar();

        // Adicionando registro ao item da lista, que é o Array Adapter
        arrayContatos.addAll(contatos);

        // Inserir o Item na Lista
        listContatos.setAdapter(arrayContatos);

    }

}
