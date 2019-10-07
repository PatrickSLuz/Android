package up.edu.br.aulafirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Contato contato1 = new Contato("Patrick","email@gmail.com");

        Contato contato2 = new Contato("Jão","jao@gmail.com");

        // Write a message to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("contatos").child("C001").setValue(contato1);
        myRef.child("contatos").child("C002").setValue(contato2);
    }
}
