package up.edu.br.aulafirebase;

public class Contato {

    private String nome;
    private String email;

    public Contato(){ }


    public Contato(String nome, String email){
        this.nome = nome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return "Nome: "+nome+"\nE-mail: "+email;
    }
}
