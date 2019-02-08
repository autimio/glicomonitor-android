package com.example.autimio.glicomonitor.models;

/**
 * Created by autimio on 01/11/17.
 */

public class Imc {

    private Integer id;
    private String altura;
    private String peso;
    private String data;
    private String hora;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
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
        return "Imc{" +
                "altura='" + altura + '\'' +
                ", peso='" + peso + '\'' +
                ", data='" + data + '\''+
                ", hora='" + hora + '\''+
                '}';
    }
}
