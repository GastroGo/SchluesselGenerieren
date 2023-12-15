package com.example.schluesselgenerieren;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.SecureRandom;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText schluesselOut;
    Button generieren;
    String currentRestaurantId = "-NkF_dqyroONEdMqgfgC";
    int currentRestaurantEmployee = 1;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<MitarbeiterViewModel> list;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        schluesselOut = findViewById(R.id.schluesselOut);
        generieren = findViewById(R.id.generieren);

        generieren.setOnClickListener(view -> {
            String key = generateKey();
            schluesselOut.setText(key);
            this.saveKeyToFirebase(key);
        });

        recyclerView = findViewById(R.id.mitarbeiterView);
        ref = FirebaseDatabase.getInstance().getReference("Schluessel").child(currentRestaurantId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MitarbeiterViewModel user = dataSnapshot.getValue(MitarbeiterViewModel.class);
                    list.add(user);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
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
        ref.child(currentRestaurantId).child(String.valueOf(currentRestaurantEmployee)).setValue(key);
        currentRestaurantEmployee++;
    }
}