package com.example.schluesselgenerieren;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.security.SecureRandom;

public class MainActivity extends AppCompatActivity {

    EditText schluesselOut;
    Button generieren;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        schluesselOut = findViewById(R.id.schluesselOut);
        generieren = findViewById(R.id.generieren);
        databaseReference = FirebaseDatabase.getInstance().getReference("Restaurants").child("-NkF_dqyroONEdMqgfgC").child("schluessel");

        generieren.setOnClickListener(view -> {
            String key = generateKey();
            schluesselOut.setText(key);
            saveKeyToFirebase(key);
        });

    }

    public static String generateKey() {
        SecureRandom random = new SecureRandom();
        StringBuilder key = new StringBuilder();
        Model model = new Model();

        for (int i = 0; i < model.getKeyLength(); i++) {
            int randomIndex = random.nextInt(model.getKeyCharacters().length());
            char randomChar = model.getKeyCharacters().charAt(randomIndex);
            key.append(randomChar);
        }

        return key.toString();
    }

    private void saveKeyToFirebase(String key) {
        String id = databaseReference.push().getKey();
        databaseReference.child(id).setValue(key);
    }
}