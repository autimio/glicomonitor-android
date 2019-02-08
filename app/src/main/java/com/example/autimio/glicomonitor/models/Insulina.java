package com.example.autimio.glicomonitor.models;

/**
 * Created by autimio on 01/11/17.
 */

public class Insulina {

    private Integer id;
    private String hora;
    private String qtdIsulina;
    private String data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getQtdIsulina() {
        return qtdIsulina;
    }

    public void setQtdIsulina(String qtdIsulina) {
        this.qtdIsulina = qtdIsulina;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Insulina{" +
                "qtdInsulina='" + qtdIsulina + '\'' +
                ", data='" + data + '\''+
                ", hora='" + hora + '\''+
                '}';
    }
}


