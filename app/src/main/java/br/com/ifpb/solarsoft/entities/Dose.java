package br.com.ifpb.solarsoft.entities;

import java.util.Date;

/**
 * Created by leonardo on 12/05/2015.
 */
public class Dose {

    private int id;
    private String nome;
    private double indicacao;
    private Vacina vacina;

    public Dose() {
    }

    public Dose(String nome, double indicacao, Vacina vacina) {

        this.nome = nome;
        this.indicacao = indicacao;
        this.vacina = vacina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getIndicacao() {
        return indicacao;
    }

    public void setIndicacao(double indicacao) {
        this.indicacao = indicacao;
    }

    public Vacina getVacina() {
        return vacina;
    }

    public void setVacina(Vacina vacina) {
        this.vacina = vacina;
    }

    @Override
    public String toString() {
        return "Dose{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", indicacao=" + indicacao +
                ", vacina=" + vacina +
                '}';
    }
}


