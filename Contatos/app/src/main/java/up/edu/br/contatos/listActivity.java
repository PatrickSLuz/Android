package up.edu.br.contatos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
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

    // Recycler View
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // ListView
    ListView listContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // ListView
        listContatos = findViewById(R.id.listContatos);

        // RecyclerView
        recyclerView = findViewById(R.id.recycler_view_new);

        // Botão para fazer um novo cadastro
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

        // Capturando as informações do BD
        ContatoDAO contatoDAO = ContatoDAO.criarInstancia();
        List<Contato> contatos = contatoDAO.listar();

        //eventosListView();
        //atualizarListaListView();

        eventosRecyclerView();
        atualizarListaRecyclerView(contatos);

    }

    public void eventosListView(){
        // clicar/tocar e segurar no item da Lista para Deletar
        listContatos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, int position, long id) {
                final Contato c = (Contato) parent.getItemAtPosition(position);
                final ContatoDAO cDAO = new ContatoDAO();
                AlertDialog alerta;

                //Cria o gerador do AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(listActivity.this);
                //define o titulo
                builder.setTitle("Excluir Contato");
                //define a mensagem
                builder.setMessage("Você deseja excluir o Contato "+c.getNome()+" - "+c.getTelefone());
                //define um botão como positivo
                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Toast.makeText(listActivity.this, "positivo=" + arg1, Toast.LENGTH_SHORT).show();
                        cDAO.deletar(c);
                        // Atualizar a lista após a exclusão de um item
                        //atualizarLista();
                        ((ArrayAdapter<Contato>)parent.getAdapter()).remove(c);
                        ((ArrayAdapter<Contato>)parent.getAdapter()).notifyDataSetChanged();
                        Toast.makeText(listActivity.this, "Deletou: " + c.getId() + " | " + c.getNome(), Toast.LENGTH_SHORT).show();
                    }
                });
                //define um botão como negativo.
                builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //Toast.makeText(listActivity.this, "negativo=" + arg1, Toast.LENGTH_SHORT).show();
                    }
                });
                //cria o AlertDialog
                alerta = builder.create();
                //Exibe
                alerta.show();

                return true;
            }
        });

        // clicar/tocar em um item da lista para editar os dados
        // passa o contato por paramento para abrir a outra tela com suas informações carregadas
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
    }

    public void atualizarListaListView(){
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

    public void eventosRecyclerView(){
        MotionEvent.actionToString(1);
    }

    public void atualizarListaRecyclerView(List<Contato> contatos){
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        mAdapter = new ListAdapterRecycler(contatos);
        recyclerView.setAdapter(mAdapter);
    }
}
