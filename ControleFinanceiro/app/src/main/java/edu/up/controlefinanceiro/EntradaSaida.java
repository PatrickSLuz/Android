package edu.up.controlefinanceiro;

import java.io.Serializable;

public class EntradaSaida implements Serializable {
    private Long id;
    private Long tipo;
    private String data;
    private Double valor;
    private String descricao;
    private String latitude;
    private String longitude;

    public EntradaSaida(){
        
    }

    public EntradaSaida(Long tipo, String data, Double valor, String descricao){
        this.tipo = tipo;
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
    }
    
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getTipo() { return tipo; }

    public void setTipo(Long tipo) { this.tipo = tipo; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public Double getValor() { return valor; }

    public void setValor(Double valor) { this.valor = valor; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getLatitude() { return latitude; }

    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLongitude() { return longitude; }

    public void setLongitude(String longitude) { this.longitude = longitude; }
}
