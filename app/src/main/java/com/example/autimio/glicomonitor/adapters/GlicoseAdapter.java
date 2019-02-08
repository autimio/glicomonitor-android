package com.example.autimio.glicomonitor.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.autimio.glicomonitor.R;
import com.example.autimio.glicomonitor.models.Glicose;
import java.util.List;

/**
 * Created by autimio on 01/11/17.
 */

public class GlicoseAdapter extends RecyclerView.Adapter<GlicoseAdapter.MyViewHolder> {

    private List<Glicose> glicoseList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView refeicao, taxaGlicose, data, hora;

        public MyViewHolder(View view) {
            super(view);
            refeicao = (TextView) view.findViewById(R.id.textRefeicaoGlicose);
            taxaGlicose = (TextView) view.findViewById(R.id.textTaxaGlicose);
            data = (TextView) view.findViewById(R.id.textDataGlicose);
            hora = (TextView) view.findViewById(R.id.textHoraGlicose);
        }
    }

    public GlicoseAdapter(List<Glicose> glicoseAll) {
        this.glicoseList = glicoseAll;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.glicose_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glicose glicose = glicoseList.get(position);
        holder.refeicao.setText(glicose.getRefeicao());
        holder.taxaGlicose.setText(glicose.getTaxaDeGlicose());
        holder.data.setText(glicose.getData());
        holder.hora.setText(glicose.getHora());
    }

    @Override
    public int getItemCount() {
        return glicoseList.size();
    }
}