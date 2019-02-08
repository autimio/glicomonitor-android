package com.example.autimio.glicomonitor.models;

/**
 * Created by autimio on 01/11/17.
 */

public class Glicose {

    private Integer id;
    private String refeicao;
    private String taxaDeGlicose;
    private String data;
    private String hora;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRefeicao() {return refeicao;}

    public void setRefeicao(String refeicao) {
        this.refeicao = refeicao;
    }

    public String getTaxaDeGlicose() {
        return taxaDeGlicose;
    }

    public void setTaxaDeGlicose(String taxaDeGlicose) {
        this.taxaDeGlicose = taxaDeGlicose;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "Glicose{" +
                "refeicao='" + refeicao + '\'' +
                ", taxa='" + taxaDeGlicose + '\'' +
                ", data='" + data + '\''+
                ", hora='" + hora + '\''+
                '}';
    }
}
