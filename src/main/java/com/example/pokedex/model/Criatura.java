package com.example.pokedex.model;

public class Criatura {

    private int id;
    private int imgId;
    private String nome;
    private String tipo;
    private String poder;
    private boolean descoberto;

    public Criatura() {
    }

    public Criatura(int id, String nome, String tipo, String poder, boolean descoberto, int imgId) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.poder = poder;
        this.descoberto = descoberto;
        this.imgId = imgId;
    }


    public Criatura(String nome, String tipo, String poder, boolean descoberto, int imgId) {
        this(0, nome, tipo, poder, descoberto, imgId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPoder() {
        return poder;
    }

    public void setPoder(String poder) {
        this.poder = poder;
    }

    public boolean isDescoberto() {
        return descoberto;
    }

    public void setDescoberto(boolean descoberto) {
        this.descoberto = descoberto;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }


}
