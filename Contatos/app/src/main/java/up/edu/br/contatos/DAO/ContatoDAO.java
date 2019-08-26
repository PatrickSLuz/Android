package up.edu.br.contatos.DAO;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import up.edu.br.contatos.model.Contato;

public class ContatoDAO {

    static SQLiteDatabase conn;

    public static ContatoDAO criarInstancia() {
        conn = Conexao.capturarConexao();
        return new ContatoDAO();
    }

    public void salvar (Contato contato){
        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("telefone", contato.getTelefone());
        values.put("tipo", contato.getTipo());

        conn.insert("contato", null, values);
    }

}
