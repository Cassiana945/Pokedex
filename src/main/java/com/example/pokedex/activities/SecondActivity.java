package com.example.pokedex.activities;

import android.os.Bundle;
import android.util.Log;

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
    List<Criatura> lista;
    CriaturaDatabase dbCriatura;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        dbCriatura = new CriaturaDatabase(this);
        recyclerView = findViewById(R.id.recycler);

        carregarCriaturas();

        adapter = new CriaturaAdapter(SecondActivity.this, new ArrayList<>(lista));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void carregarCriaturas() {
        lista = dbCriatura.findAllCriatura();

        if (lista.isEmpty()) {
            adicionarCriaturasPadrao();
            lista = dbCriatura.findAllCriatura();
        }
    }

    private void adicionarCriaturasPadrao() {
        try {
            Criatura pikachu = new Criatura("pikachu", "elétrico", "choque do trovão", false, R.drawable.pikachu);
            Criatura duskull = new Criatura("duskull", "fantasma", "levitação", false, R.drawable.duskull);
            Criatura mankey = new Criatura("mankey", "luta", "anger point", false, R.drawable.mankey);

            dbCriatura.addCriatura(pikachu);
            dbCriatura.addCriatura(duskull);
            dbCriatura.addCriatura(mankey);
        } catch (Exception e) {
            Log.e("SecondActivity", "Erro ao adicionar criaturas padrão", e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            lista.clear();
            lista.addAll(dbCriatura.findAllCriatura());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        if (dbCriatura != null) {
            dbCriatura.close();
        }
        super.onDestroy();
    }
}
