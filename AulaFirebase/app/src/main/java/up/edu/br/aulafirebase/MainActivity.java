package up.edu.br.aulafirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Contato contato1 = new Contato("Patrick","patrick@gmail.com");
        Contato contato2 = new Contato("Jão","jao@gmail.com");
        Contato contato3 = new Contato("Pedrin","pedrin@gmail.com");
        Contato contato4 = new Contato("José","jose@gmail.com");

        // Conect and Write a message to the database

        /* FORMA 1 */
        /*
        final FirebaseDatabase database = FirebaseDatabase.getInstance(); // Captura/Conecta com a Base de Dados/Firebase
        DatabaseReference myRef = database.getReference();

        myRef.child("contatos").child("C001").setValue(contato1);
        myRef.child("contatos").child("C002").setValue(contato2);
        */

        /* FORMA 2 */
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(); // Captura/Conecta com a Base de Dados/Firebase

        mDatabase.child("contatos").push().setValue(contato3);
        mDatabase.child("contatos").push().setValue(contato4);

        // Criando ListView para listagem dos Dados buscados do Firebase
        ListView listaContatos = findViewById(R.id.listaContatos); // Pegando a referencia do ListView do MainActivity.xml
        final ArrayAdapter<Contato> arrayContatos = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
        listaContatos.setAdapter(arrayContatos);

        // Read from the Database/Firebase
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<Contato> arrayListContatos = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.child("contatos").getChildren()){
                    String nome = ds.child("nome").getValue(String.class);
                    String email = ds.child("email").getValue(String.class);
                    arrayListContatos.add(new Contato(nome, email));
                }
                arrayContatos.clear();
                arrayContatos.addAll(arrayListContatos);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}
