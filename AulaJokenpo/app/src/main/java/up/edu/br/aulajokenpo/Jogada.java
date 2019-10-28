package up.edu.br.aulajokenpo;

public class Jogada {

    public Integer jogador;
    public Integer escolha;
    public String latitude;
    public String longitude;

    public Integer getJogador() {
        return jogador;
    }

    public void setJogador(Integer jogador) {
        this.jogador = jogador;
    }

    public Integer getEscolha() {
        return escolha;
    }

    public void setEscolha(Integer escolha) {
        this.escolha = escolha;
    }

    public String getLatitude() { return latitude; }

    public void setLatitude(String latitude) { this.latitude = latitude; }

    public String getLongitude() { return longitude; }

    public void setLongitude(String longitude) { this.longitude = longitude; }
}

