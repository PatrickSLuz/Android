package up.edu.br.jokenpo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int qntJogadas = 0;
    int pontosJogador = 0;
    int pontosComputador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jogar (View v){

        qntJogadas++;

        int jogadaPC = new Random().nextInt(3) + 1; // Classe para randomizar do Java.

        ImageButton btnJogador = findViewById(R.id.btnJogador);
        ImageButton btnCopmutador = findViewById(R.id.btnComputador);

        TextView txtResultado = findViewById(R.id.txtResultado);
        TextView txtVencedor = findViewById(R.id.txtVencedor);

        if (v.getId() == R.id.btnPedra){
            btnJogador.setImageResource(R.drawable.pedra);
        }
        if (v.getId() == R.id.btnPapel){
            btnJogador.setImageResource(R.drawable.papel);
        }
        if (v.getId() == R.id.btnTesoura){
            btnJogador.setImageResource(R.drawable.tesoura);
        }

        if(jogadaPC == 1){
            btnCopmutador.setImageResource(R.drawable.tesoura);
        }
        if(jogadaPC == 2){
            btnCopmutador.setImageResource(R.drawable.papel);
        }
        if(jogadaPC == 3){
            btnCopmutador.setImageResource(R.drawable.pedra);
        }

        if (jogadaPC == 1){
            if(v.getId() == R.id.btnPedra){
                // jogador ganha
                txtResultado.setText(txtResultado.getText() + " +1 ");
                txtVencedor.setText(" Jogador Venceu! ");
                pontosJogador++;
            }else if(v.getId() == R.id.btnPapel){
                // PC ganha
                txtResultado.setText(txtResultado.getText() + " +0 ");
                txtVencedor.setText(" Computador Venceu! ");
                pontosComputador++;
            }else{
                // empate
                txtResultado.setText(txtResultado.getText() + " - ");
                txtVencedor.setText(" Empate! ");
            }
        }else if (jogadaPC == 2){
            if (v.getId() == R.id.btnPedra){
                // PC ganha
                txtResultado.setText(txtResultado.getText() + " +0 ");
                txtVencedor.setText(" Computador Venceu! ");
                pontosComputador++;
            }else if(v.getId() == R.id.btnTesoura){
                //Jogador ganha
                txtResultado.setText(txtResultado.getText() + " +1 ");
                txtVencedor.setText(" Jogador Venceu! ");
                pontosJogador++;
            }else{
                // empate
                txtResultado.setText(txtResultado.getText() + " - ");
                txtVencedor.setText(" Empate! ");
            }
        }else if (jogadaPC == 3){
            if (v.getId() == R.id.btnPapel){
                //jogador ganha
                txtResultado.setText(txtResultado.getText() + " +1 ");
                txtVencedor.setText(" Jogador Venceu! ");
                pontosJogador++;
            }else if (v.getId() == R.id.btnTesoura){
                //PC ganha
                txtResultado.setText(txtResultado.getText() + " +0 ");
                txtVencedor.setText(" Computador Venceu! ");
                pontosComputador++;
            }else {
                // empate
                txtResultado.setText(txtResultado.getText() + " - ");
                txtVencedor.setText(" Empate! ");
            }
        }

        if (qntJogadas == 15){
            if(pontosComputador > pontosJogador){
                // PC vence
                Toast.makeText(this, "COMPUTADOR VENCEU", Toast.LENGTH_SHORT).show();
            }else if (pontosJogador > pontosComputador){
                // Jogador vence
                Toast.makeText(this, "JOGADOR VENCEU", Toast.LENGTH_SHORT).show();
            }else{
                //empate
                Toast.makeText(this, "EMPATE", Toast.LENGTH_SHORT).show();
            }
            btnJogador.setImageResource(R.drawable.vazio);
            btnCopmutador.setImageResource(R.drawable.vazio);
            txtResultado.setText("");
            txtVencedor.setText("");
            pontosJogador = 0;
            pontosComputador = 0;
            qntJogadas = 0;
        }
    }
}
