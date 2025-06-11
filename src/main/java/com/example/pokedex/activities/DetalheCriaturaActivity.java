package com.example.pokedex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pokedex.R;
import com.example.pokedex.database.CriaturaDatabase;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class DetalheCriaturaActivity extends AppCompatActivity {

    TextView textNome, textTipo, textPoder, textDescoberto;
    ImageView imgPokemon;
    ImageView btnExcluir, btnVoltar;
    CriaturaDatabase dbCriatura = new CriaturaDatabase(this);
    AdView adView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        textNome = findViewById(R.id.lbNome);
        textTipo = findViewById(R.id.lbTipo);
        textPoder = findViewById(R.id.lbPoder);
        textDescoberto = findViewById(R.id.lbDescoberto);
        imgPokemon = findViewById(R.id.imgPokemon);
        btnExcluir = findViewById(R.id.excluir);
        btnVoltar = findViewById(R.id.voltar);

        adView2 = findViewById(R.id.adView2);

        Intent i = getIntent();
        int id = i.getIntExtra("id", 0);
        int img = i.getIntExtra("imageId", -1);
        String nome = i.getStringExtra("nome");
        String tipo = i.getStringExtra("tipo");
        String poder = i.getStringExtra("poder");
        boolean descoberto = i.getBooleanExtra("descoberto", false);


        imgPokemon.setImageResource(img);
        textNome.setText(nome);
        textTipo.setText(tipo);
        textPoder.setText(poder);
        textDescoberto.setText(descoberto ? "sim" : "não");


        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetalheCriaturaActivity.this, SecondActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dbCriatura.excluirCriatura(id);
                    Toast.makeText(DetalheCriaturaActivity.this, "Criatura excluída com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetalheCriaturaActivity.this, SecondActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
            }
        });

        ////////////////////////////////Anúncio

        AdRequest adRequest = new AdRequest.Builder().build();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Toast.makeText(DetalheCriaturaActivity.this, "Banner rodando...", Toast.LENGTH_SHORT).show();
            }
        });

        adView2.setAdListener(new AdListener() {

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Toast.makeText(DetalheCriaturaActivity.this, "Falha ao abrir o Banner. \nErro: " + loadAdError, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Toast.makeText(DetalheCriaturaActivity.this, "Anúncio carregado!", Toast.LENGTH_SHORT).show();
            }
        });

        adView2.loadAd(adRequest);

    }

    }

