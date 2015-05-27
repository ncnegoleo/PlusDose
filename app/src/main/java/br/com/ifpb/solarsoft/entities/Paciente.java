package br.com.ifpb.solarsoft.entities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import br.com.ifpb.solarsoft.util.ContexPaths;

/**
 * Created by leonardo on 12/05/2015.
 */
public class Paciente {

    private int id;
    private String foto;
    private String nome;
    private String sobrenome;
    private Date dataDeNascimento;
    private char sexo;
    private ArrayList<Vacina> vacinas;
    private int listado;

    public Paciente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(Date dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public ArrayList<Vacina> getVacinas() {
        return vacinas;
    }

    public void setVacinas(ArrayList<Vacina> vacinas) {
        this.vacinas = vacinas;
    }

    public int getListado() {
        return listado;
    }

    public void setListado(int listado) {
        this.listado = listado;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "id=" + id +
                ", foto='" + foto + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", dataDeNascimento=" + dataDeNascimento +
                ", sexo=" + sexo +
                ", vacinas=" + vacinas +
                ", listado=" + listado +
                '}';
    }
}
