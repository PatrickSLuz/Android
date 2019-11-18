package edu.up.controlefinanceiro;

import java.util.Date;

public class EntradaSaida {
    private Long id;
    private int tipo;
    private String data;
    private Double valor;
    private String Descricao;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public int getTipo() { return tipo; }

    public void setTipo(int tipo) { this.tipo = tipo; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public Double getValor() { return valor; }

    public void setValor(Double valor) { this.valor = valor; }

    public String getDescricao() { return Descricao; }

    public void setDescricao(String descricao) { Descricao = descricao; }
}
