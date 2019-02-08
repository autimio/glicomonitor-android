package com.example.autimio.glicomonitor.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.autimio.glicomonitor.R;
import com.example.autimio.glicomonitor.models.Imc;
import java.util.List;

/**
 * Created by autimio on 01/11/17.
 */

public class ImcAdapter extends RecyclerView.Adapter<ImcAdapter.MyViewHolder> {

    private List<Imc> imcList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView altura, peso, data, hora, imc, situacao;

        public MyViewHolder(View view) {
            super(view);
            imc = (TextView) view.findViewById(R.id.textCalculoImc);
            data = (TextView) view.findViewById(R.id.textDataImc);
            hora = (TextView) view.findViewById(R.id.textHoraImc);
            situacao = (TextView) view.findViewById(R.id.textSituacaoImc);
        }
    }

    public ImcAdapter(List<Imc> imcAll) {
        this.imcList = imcAll;
    }

    @Override
    public ImcAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.imc_list_row, parent, false);

        return new ImcAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImcAdapter.MyViewHolder holder, int position) {
        Imc imc = imcList.get(position);

        holder.data.setText(imc.getData());
        holder.hora.setText(imc.getHora());

        double altura = (Double.parseDouble(imc.getAltura().trim()));
        double peso = (Double.parseDouble(imc.getPeso().trim()));
        double calcImc = peso / (altura * altura);

        String resultado = String.format("%.1f", calcImc);
        holder.imc.setText(resultado);

        if(calcImc < 16) {
            holder.situacao.setText("Magreza grave");
        }

        if((calcImc >= 16) && (calcImc < 17)) {
            holder.situacao.setText("Magreza moderada");
        }

        if((calcImc >= 17) && (calcImc < 18.5)) {
            holder.situacao.setText("Magreza moderada");
        }

        if((calcImc >= 18.5) && (calcImc < 25)) {
            holder.situacao.setText("Saudável");
        }

        if((calcImc >= 25) && (calcImc < 30)) {
            holder.situacao.setText("Sobrepeso");
        }

        if((calcImc >= 30) && (calcImc < 35)) {
            holder.situacao.setText("Obesidade Grau I");
        }

        if((calcImc >= 35) && (calcImc < 40)) {
            holder.situacao.setText("Obesidade Grau II (severa)");
        }

        if(calcImc >= 40) {
            holder.situacao.setText("Obesidade Grau III (severa)");
        }

    }

    @Override
    public int getItemCount() {
        return imcList.size();
    }
}
