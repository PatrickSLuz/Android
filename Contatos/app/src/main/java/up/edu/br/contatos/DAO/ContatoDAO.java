package up.edu.br.contatos.DAO;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import up.edu.br.contatos.model.Contato;

public class ContatoDAO {

    static SQLiteDatabase conn;

    public static ContatoDAO criarInstancia() {
        conn = Conexao.capturarConexao();
        return new ContatoDAO();
    }

    public boolean salvar (Contato contato){
        List<Contato> listaContatos = listar();

        for (Contato c : listaContatos) {
            if (c.getNome().equals(contato.getNome())){
                return false;
            }
        }

        ContentValues values = new ContentValues();

        values.put("nome", contato.getNome());
        values.put("sobrenome", contato.getSobrenome());
        values.put("telefone", contato.getTelefone());
        values.put("tipo", contato.getTipo());
        values.put("email", contato.getEmail());
        values.put("cep", contato.getCep());
        values.put("cpf", contato.getCpf());

        // INSERIR
        conn.insert("contato", null, values);
        return true;
    }

    public void alterar (Contato contato){
        ContentValues values = new ContentValues();

        values.put("nome", contato.getNome());
        values.put("sobrenome", contato.getSobrenome());
        values.put("telefone", contato.getTelefone());
        values.put("tipo", contato.getTipo());
        values.put("email", contato.getEmail());
        values.put("cep", contato.getCep());
        values.put("cpf", contato.getCpf());

        conn.update("contato", values, "id=:id", new String[] {contato.getId().toString()});
    }

    public void deletar (Contato contato){
        conn.delete("contato", "id=:id", new String[] {contato.getId().toString()});
    }

    public List<Contato> listar(){
        Cursor c = conn.query("contato", new String[] {"id","nome", "sobrenome", "telefone", "tipo", "email", "cep", "cpf"}, null, null, null, null, "nome");

        List<Contato> listaContatos = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                Contato contato = new Contato();
                contato.setId(c.getLong(0));
                contato.setNome(c.getString(1));
                contato.setSobrenome(c.getString(2));
                contato.setTelefone(c.getString(3));
                contato.setTipo(c.getString(4));
                contato.setEmail(c.getString(5));
                contato.setCep(c.getString(6));
                contato.setCpf(c.getString(7));
                listaContatos.add(contato);
            }while(c.moveToNext());
        }
        return listaContatos;
    }

}
