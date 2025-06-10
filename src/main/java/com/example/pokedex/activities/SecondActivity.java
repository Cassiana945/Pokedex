package com.example.pokedex.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.R;
import com.example.pokedex.database.CriaturaDatabase;
import com.example.pokedex.model.Criatura;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CriaturaAdapter adapter;
    ArrayList<Criatura> lista;
    CriaturaDatabase dbCriatura = new CriaturaDatabase(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        recyclerView = findViewById(R.id.recycler);
        lista = new ArrayList<>();


        lista.add(new Criatura("pikachu", "elétrico", "choque do trovão", false, R.drawable.pikachu));
        lista.add(new Criatura("duskull", "fantasma", "levitação", false, R.drawable.duskull));
        lista.add(new Criatura("mankey", "luta", "anger point", false, R.drawable.mankey));

        adapter = new CriaturaAdapter(SecondActivity.this, lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


}
