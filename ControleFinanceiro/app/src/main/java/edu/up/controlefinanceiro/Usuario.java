package edu.up.controlefinanceiro;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String email;
    private String uid;
    private String nome;

    public Usuario(){ }

    public Usuario(String email, String uid, String nome){
        this.email = email;
        this.uid = uid;
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
