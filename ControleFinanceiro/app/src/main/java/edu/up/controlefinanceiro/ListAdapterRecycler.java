package edu.up.controlefinanceiro;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class ListAdapterRecycler extends RecyclerView.Adapter<ListAdapterRecycler.MyViewHolder> {
    
    private List<EntradaSaida> lancamentos;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtData;
        public TextView txtValor;
        public TextView txtDesc;
        public MyViewHolder(View v) {
            super(v);
            txtData = v.findViewById(R.id.txtData);
            txtValor = v.findViewById(R.id.txtValor);
            txtDesc = v.findViewById(R.id.txtDesc);
        }
    }

    // Construtor - Provide a suitable constructor (depends on the kind of dataset)
    public ListAdapterRecycler(List<EntradaSaida> lancamentos) {
        this.lancamentos = lancamentos;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapterRecycler.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_base, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txtData.setText(lancamentos.get(position).getData());

        Double valor = lancamentos.get(position).getValor();
        // Formatar para Moeda
        NumberFormat formatter = new DecimalFormat("#,##0.00");
        if(lancamentos.get(position).getTipo() == 0){
            holder.txtValor.setTextColor(Color.GREEN);
            holder.txtValor.setText("+ R$ " + formatter.format(valor));
        }else{
            holder.txtValor.setTextColor(Color.RED);
            holder.txtValor.setText("- R$ " + formatter.format(valor));
        }
        holder.txtDesc.setText(lancamentos.get(position).getDescricao());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return lancamentos.size();
    }
}
