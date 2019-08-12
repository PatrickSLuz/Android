package edu.up.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Double valorUm = 0.0;
    Double valorDois = 0.0;
    String op = "";
    int tamanho_vlr_um = 0;
    boolean dps_resultado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // o APP renderiza este xml/layout.
    }

    public void clicar (View v){
        // View v é o elemento que foi clicado.

        EditText txtResultado = findViewById(R.id.txtResultado);
        Button btnPonto = findViewById(R.id.btnPonto);

        if(dps_resultado){
            txtResultado.setText("");
            dps_resultado = false;
        }

        if(v.getId() == R.id.btnZero){
            txtResultado.setText(txtResultado.getText() + "0");
        }else if(v.getId() == R.id.btnUm){
            txtResultado.setText(txtResultado.getText() + "1");
        }else if(v.getId() == R.id.btnDois){
            txtResultado.setText(txtResultado.getText() + "2");
        }else if(v.getId() == R.id.btnTres){
            txtResultado.setText(txtResultado.getText() + "3");
        }else if(v.getId() == R.id.btnQuatro){
            txtResultado.setText(txtResultado.getText() + "4");
        }else if(v.getId() == R.id.btnCinco){
            txtResultado.setText(txtResultado.getText() + "5");
        }else if(v.getId() == R.id.btnSeis){
            txtResultado.setText(txtResultado.getText() + "6");
        }else if(v.getId() == R.id.btnSete){
            txtResultado.setText(txtResultado.getText() + "7");
        }else if(v.getId() == R.id.btnOito){
            txtResultado.setText(txtResultado.getText() + "8");
        }else if(v.getId() == R.id.btnNove){
            txtResultado.setText(txtResultado.getText() + "9");
        }else if(v.getId() == R.id.btnPonto){
            txtResultado.setText(txtResultado.getText() + ".");
            btnPonto.setEnabled(false);
        }

        if(v.getId() == R.id.btnMais){
            op = "+";
            valorUm = Double.parseDouble(txtResultado.getText().toString());
            txtResultado.setText(txtResultado.getText() + " + ");
            btnPonto.setEnabled(true);
            // variavel para receber o tamanho do texto do EditText.
            tamanho_vlr_um = txtResultado.length();
        }else if(v.getId() == R.id.btnMenos){
            op = "-";
            valorUm = Double.parseDouble(txtResultado.getText().toString());
            txtResultado.setText(txtResultado.getText() + " - ");
            btnPonto.setEnabled(true);
            // variavel para receber o tamanho do texto do EditText.
            tamanho_vlr_um = txtResultado.length();
        }else if(v.getId() == R.id.btnDiv){
            op = "/";
            valorUm = Double.parseDouble(txtResultado.getText().toString());
            txtResultado.setText(txtResultado.getText() + " / ");
            btnPonto.setEnabled(true);
            // variavel para receber o tamanho do texto do EditText.
            tamanho_vlr_um = txtResultado.length();
        }else if(v.getId() == R.id.btnMulti){
            op = "x";
            valorUm = Double.parseDouble(txtResultado.getText().toString());
            txtResultado.setText(txtResultado.getText() + " x ");
            btnPonto.setEnabled(true);
            // variavel para receber o tamanho do texto do EditText.
            tamanho_vlr_um = txtResultado.length();
        }

        if (v.getId() == R.id.btnLimpar){
            txtResultado.setText("");
            tamanho_vlr_um = 0;
        }

        if(v.getId() == R.id.btnIgual){
            Double result = 0.0;

            // nesta linha a variavel esta recebendo apenas a String que esta apos o que foi digitado
            String vlrDois = txtResultado.getText().toString().substring(tamanho_vlr_um-1);

            valorDois = Double.parseDouble(vlrDois);

            if(op.equals("+")){
                result = valorUm + valorDois;
            }else if (op.equals("-")){
                result = valorUm - valorDois;
            }else if (op.equals("/")){
                result = valorUm / valorDois;
            }else if (op.equals("x")){
                result = valorUm * valorDois;
            }

            txtResultado.setText(txtResultado.getText() + " = " + String.valueOf(result));
            dps_resultado = true;
        }
        Toast.makeText(this, "Oie", Toast.LENGTH_LONG).show();
    }
}
