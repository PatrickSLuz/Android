package edu.up.controlefinanceiro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class DetalhesMes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_mes);

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
