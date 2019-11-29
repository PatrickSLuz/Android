package edu.up.controlefinanceiro;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DetalhesMesActivity extends AppCompatActivity {

    // Recycler View
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // Lista de Lançamentos
    List<EntradaSaida> lancamentos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_mes);

        // Verificar paramentos recebidos
        if (getIntent().getExtras() != null){
            lancamentos = (List<EntradaSaida>) getIntent().getExtras().getSerializable("lancamentos");
        }

        // RecyclerView
        recyclerView = findViewById(R.id.recycler_view_detalhes);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter
        mAdapter = new ListAdapterRecycler(lancamentos);
        recyclerView.setAdapter(mAdapter);

        // SETA voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true); // Ativar o botão
        getSupportActionBar().setTitle("Mês Detalhado"); // Titulo para ser exibido na sua Action Bar em frente à seta
    }

    // SETA Voltar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android - Default
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }
}
