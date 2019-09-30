package up.edu.br.contatos;

import android.app.ListActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import up.edu.br.contatos.model.Contato;

public class ListAdapterRecycler extends RecyclerView.Adapter<ListAdapterRecycler.MyViewHolder> {

    private List<Contato> contatos;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtNome;
        public TextView txtTelefone;
        public MyViewHolder(View v) {
            super(v);
            txtNome = v.findViewById(R.id.txtNome);
            txtTelefone = v.findViewById(R.id.txtTelefone);
        }
    }

    // Construtor - Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapterRecycler(List<Contato> contatos) {
        this.contatos = contatos;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapterRecycler.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txtNome.setText(contatos.get(position).getNome());
        holder.txtTelefone.setText(contatos.get(position).getTelefone());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contatos.size();
    }
}