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
            tamanho_vlr_um = txtResultado.length();
            if (tamanho_vlr_um > 0){
                valorUm = Double.parseDouble(txtResultado.getText().toString());
            }else{
                txtResultado.setText(valorUm.toString());
            }
            txtResultado.setText(txtResultado.getText() + " + ");
            btnPonto.setEnabled(true);
            // variavel para receber o tamanho do texto do EditText.
            tamanho_vlr_um = txtResultado.length();

        }else if(v.getId() == R.id.btnMenos){
            op = "-";
            tamanho_vlr_um = txtResultado.length();
            if (tamanho_vlr_um > 0){
                valorUm = Double.parseDouble(txtResultado.getText().toString());
            }else{
                txtResultado.setText(valorUm.toString());
            }
            txtResultado.setText(txtResultado.getText() + " - ");
            btnPonto.setEnabled(true);
            // variavel para receber o tamanho do texto do EditText.
            tamanho_vlr_um = txtResultado.length();

        }else if(v.getId() == R.id.btnDiv){
            op = "/";
            tamanho_vlr_um = txtResultado.length();
            if (tamanho_vlr_um > 0){
                valorUm = Double.parseDouble(txtResultado.getText().toString());
            }else{
                txtResultado.setText(valorUm.toString());
            }
            txtResultado.setText(txtResultado.getText() + " / ");
            btnPonto.setEnabled(true);
            // variavel para receber o tamanho do texto do EditText.
            tamanho_vlr_um = txtResultado.length();

        }else if(v.getId() == R.id.btnMulti){
            op = "x";
            tamanho_vlr_um = txtResultado.length();
            if (tamanho_vlr_um > 0){
                valorUm = Double.parseDouble(txtResultado.getText().toString());
            }else{
                txtResultado.setText(valorUm.toString());
            }
            txtResultado.setText(txtResultado.getText() + " x ");
            btnPonto.setEnabled(true);
            // variavel para receber o tamanho do texto do EditText.
            tamanho_vlr_um = txtResultado.length();
        }else if(v.getId() == R.id.btnPotencia){
            op = "pot";
            tamanho_vlr_um = txtResultado.length();
            if (tamanho_vlr_um > 0){
                valorUm = Double.parseDouble(txtResultado.getText().toString());
            }else{
                txtResultado.setText(valorUm.toString());
            }
            txtResultado.setText(txtResultado.getText() + " pot ");
            btnPonto.setEnabled(true);
            // variavel para receber o tamanho do texto do EditText.
            tamanho_vlr_um = txtResultado.length();

        }else if(v.getId() == R.id.btnProcentagem){
            op = "%";
            tamanho_vlr_um = txtResultado.length();
            if (tamanho_vlr_um > 0){
                valorUm = Double.parseDouble(txtResultado.getText().toString());
            }else{
                txtResultado.setText(valorUm.toString());
            }
            txtResultado.setText(txtResultado.getText() + " % ");
            btnPonto.setEnabled(true);
            // variavel para receber o tamanho do texto do EditText.
            tamanho_vlr_um = txtResultado.length();

        }else if (v.getId() == R.id.btnRaiz){
            op = "raiz";
            tamanho_vlr_um = txtResultado.length();
            if (tamanho_vlr_um > 0){
                valorUm = Double.parseDouble(txtResultado.getText().toString());
            }else{
                txtResultado.setText(valorUm.toString());
            }
            txtResultado.setText(txtResultado.getText() + " √ ");
            btnPonto.setEnabled(true);
            // variavel para receber o tamanho do texto do EditText.
            tamanho_vlr_um = txtResultado.length();

        }

        if (v.getId() == R.id.btnLimpar){
            txtResultado.setText("");
            valorUm = 0.0;
            valorDois = 0.0;
            tamanho_vlr_um = 0;
        }

        if(v.getId() == R.id.btnIgual){
            Double result = 0.0;

            if (op.equals("raiz")){
                result = Math.sqrt(valorUm);
            }else{
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

                if(op.equals("pot")){
                    result = Math.pow(valorUm, valorDois);
                }else if (op.equals("%")){
                    result = (valorUm/100) * valorDois;
                }
            }

            txtResultado.setText(String.valueOf(result));
            valorUm = result; // valor um recebe o resultado caso seja necessário fazer outra operacao com este resultado
            dps_resultado = true;
        }
    }
}
