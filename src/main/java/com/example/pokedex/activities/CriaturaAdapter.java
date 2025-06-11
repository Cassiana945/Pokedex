package com.example.pokedex.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.R;
import com.example.pokedex.database.CriaturaDatabase;
import com.example.pokedex.model.Criatura;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.ArrayList;

public class CriaturaAdapter extends RecyclerView.Adapter<CriaturaAdapter.ViewHolder> {

    Context contexto;
    ArrayList<Criatura> criaturas;
    private static int contadorDescobertas = 0;
    private InterstitialAd mInterstitialAd;

    public CriaturaAdapter(Context contexto, ArrayList<Criatura> criaturas) {
        this.contexto = contexto;
        this.criaturas = criaturas;
    }

    @NonNull
    @Override
    public CriaturaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(contexto)
                .inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CriaturaAdapter.ViewHolder holder, int position) {
        Criatura criatura = criaturas.get(position);
        holder.imgPokemon.setImageResource(criatura.getImgId());
        holder.textNome.setText(criatura.getNome());
        holder.textTipo.setText(criatura.getTipo());

        if (criatura.isDescoberto()) {
            holder.imgDescoberto.setImageResource(R.drawable.check);
        } else {
            holder.imgDescoberto.setImageResource(R.drawable.adicionar);
        }
    }

    @Override
    public int getItemCount() {
        return criaturas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textNome, textTipo;
        ImageView imgPokemon, imgDescoberto;
        CriaturaDatabase dbCriatura;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textNome = itemView.findViewById(R.id.nome);
            textTipo = itemView.findViewById(R.id.descricao);
            imgPokemon = itemView.findViewById(R.id.pokemon);
            imgDescoberto = itemView.findViewById(R.id.descoberto);
            dbCriatura = new CriaturaDatabase(contexto);


            AdRequest adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(contexto, "ca-app-pub-3940256099942544/1033173712", adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            mInterstitialAd = null;
                        }

                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            mInterstitialAd = interstitialAd;
                        }
                    });



            imgPokemon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Criatura criatura = criaturas.get(position);
                        Intent intent = new Intent(contexto, DetalheCriaturaActivity.class);
                        intent.putExtra("id", criatura.getId());
                        intent.putExtra("imageId", criatura.getImgId());
                        intent.putExtra("nome", criatura.getNome());
                        intent.putExtra("tipo", criatura.getTipo());
                        intent.putExtra("poder", criatura.getPoder());
                        intent.putExtra("descoberto", criatura.isDescoberto());
                        contexto.startActivity(intent);
                        ((Activity) contexto).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
                }
            });

            imgDescoberto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Criatura criatura = criaturas.get(position);
                        if (!criatura.isDescoberto()) {
                            criatura.setDescoberto(true);
                            dbCriatura.updateDescoberto(criatura.getId(), true);
                            notifyItemChanged(position);
                            Toast.makeText(contexto, "Você descobriu o famigerado " + criatura.getNome() + "! Parabéns, treinador!", Toast.LENGTH_LONG).show();
                            contadorDescobertas++;

                            if (contadorDescobertas % 3 == 0 && mInterstitialAd != null) {
                                mInterstitialAd.show((Activity) contexto);
                            }
                        } else {
                            Toast.makeText(contexto, criatura.getNome() + " já foi descoberto!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


        }
    }
}

