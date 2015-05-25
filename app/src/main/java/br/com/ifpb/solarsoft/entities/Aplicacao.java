package br.com.ifpb.solarsoft.entities;

import java.util.Date;

/**
 * Created by leonardo on 21/05/2015.
 */
public class Aplicacao implements Item {

    private int id;
    private String dataAplicacao;
    private int aplicada;
    private int pacienteId ;
    private String nomeDose;
    private String nomeVacina;
    private int idDose;
    private double indicacao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDataAplicacao() {
        return dataAplicacao;
    }

    public void setDataAplicacao(String dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    public int isAplicada() {
        return aplicada;
    }

    public void setAplicada(int aplicada) {
        this.aplicada = aplicada;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getNomeDose() {
        return nomeDose;
    }

    public void setNomeDose(String nomeDose) {
        this.nomeDose = nomeDose;
    }

    public String getNomeVacina() {
        return nomeVacina;
    }

    public void setNomeVacina(String nomeVacina) {
        this.nomeVacina = nomeVacina;
    }

    public double getIndicacao() {
        return indicacao;
    }

    public void setIndicacao(double indicacao) {
        this.indicacao = indicacao;
    }

    public int getIdDose() {
        return idDose;
    }

    public void setIdDose(int idDose) {
        this.idDose = idDose;
    }

    @Override
    public boolean isSection() {
        return false;
    }

    @Override
    public String toString() {
        return "Aplicacao{" +
                "id=" + id +
                ", dataAplicacao='" + dataAplicacao + '\'' +
                ", aplicada=" + aplicada +
                ", pacienteId=" + pacienteId +
                ", nomeDose='" + nomeDose + '\'' +
                ", nomeVacina='" + nomeVacina + '\'' +
                ", idDose=" + idDose +
                ", indicacao=" + indicacao +
                '}';
    }
}
