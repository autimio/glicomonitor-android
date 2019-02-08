package com.example.autimio.glicomonitor.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.autimio.glicomonitor.R;
import com.example.autimio.glicomonitor.models.Insulina;
import java.util.List;

/**
 * Created by autimio on 01/11/17.
 */

public class InsulinaAdapter extends RecyclerView.Adapter<InsulinaAdapter.MyViewHolder> {

    private List<Insulina> insulinaList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView qtdInsulina, data, hora;

        public MyViewHolder(View view) {
            super(view);
            qtdInsulina = (TextView) view.findViewById(R.id.textQtdInsulina);
            data = (TextView) view.findViewById(R.id.textDataInsulina);
            hora = (TextView) view.findViewById(R.id.textHoraInsulina);
        }
    }

    public InsulinaAdapter(List<Insulina> insulinaAll) {
        this.insulinaList = insulinaAll;
    }

    @Override
    public InsulinaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.insulina_list_row, parent, false);

        return new InsulinaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InsulinaAdapter.MyViewHolder holder, int position) {
        Insulina imc = insulinaList.get(position);
        holder.qtdInsulina.setText(imc.getQtdIsulina());
        holder.data.setText(imc.getData());
        holder.hora.setText(imc.getHora());
    }

    @Override
    public int getItemCount() {
        return insulinaList.size();
    }
}