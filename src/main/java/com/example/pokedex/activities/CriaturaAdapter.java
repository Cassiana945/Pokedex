package com.example.pokedex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex.R;
import com.example.pokedex.database.CriaturaDatabase;
import com.example.pokedex.model.Criatura;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity extends AppCompatActivity {

    EditText textNome, textPoder;
    ImageView btnRegistar, btnVerPokedex;
    AdView adView;
    String tipoSelecionado = "";
    CriaturaDatabase dbCriatura = new CriaturaDatabase(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textNome = findViewById(R.id.nome);
        textPoder = findViewById(R.id.poder);
        btnRegistar = findViewById(R.id.registar);
        btnVerPokedex = findViewById(R.id.ver_pokedex);
        adView = findViewById(R.id.adView);


        //////////////////////////Salvar Criatura

        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = textNome.getText().toString();
                String poder = textPoder.getText().toString();
                boolean descoberto = false;
                int imgId = R.drawable.default_pokemon;

                if (nome.isEmpty() || poder.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Nome e Poder são campos obrigatórios", Toast.LENGTH_SHORT).show();
                } else {
                    Criatura criatura = new Criatura(nome, tipoSelecionado, poder, descoberto, imgId);
                    dbCriatura.addCriatura(criatura);
                    Toast.makeText(MainActivity.this, "Criatura registrada com sucesso!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //////////////////////////Intent para SecondActivity

        btnVerPokedex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        ////////////////////////////////////////Spinner

        Spinner spinner = findViewById(R.id.tipos_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.lista_tipos,
                R.layout.item_spinner
        );
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoSelecionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this, "Escolha um Tipo de Pokemon", Toast.LENGTH_SHORT).show();
            }
        });


        //////////////////////////////////// Anúncio

        AdRequest adRequest = new AdRequest.Builder().build();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Toast.makeText(MainActivity.this, "Banner rodando...", Toast.LENGTH_SHORT).show();
            }
        });

        adView.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Toast.makeText(MainActivity.this, "Falha ao abrir o Banner. \nErro: " + loadAdError, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Toast.makeText(MainActivity.this, "Anúncio carregado!", Toast.LENGTH_SHORT).show();
            }
        });

        adView.loadAd(adRequest);

    }

}


