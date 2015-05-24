package br.com.ifpb.solarsoft.sql;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import br.com.ifpb.solarsoft.entities.Aplicacao;

/**
 * Created by leonardo on 22/05/2015.
 */
public class AplicacaoDao extends DataBase {


    public AplicacaoDao(Context context) {
        super(context);
    }


    public void save(Aplicacao aplicacao, int pacienteId, int doseId) {

        Log.e("APLICACAO: ", "INSERT INTO aplicacao " +
                "(data_aplicacao, aplicada, id_paciente, id_dose) " +
                "VALUES ('" + aplicacao.getDataAplicacao() + "', " + aplicacao.isAplicada() + ", "
                + pacienteId + ", " + doseId + ");");

        db.execSQL("INSERT INTO aplicacao" +
                "(data_aplicacao, aplicada, id_paciente, id_dose) " +
                "VALUES('" + aplicacao.getDataAplicacao() + "', " + aplicacao.isAplicada() + ", "
                + pacienteId + ", " + doseId + ")");
    }

    public Aplicacao update(Aplicacao aplicacao, int pacienteId, int doseId) {

        db.execSQL("UPDATE TABLE aplicacao " +
                "SET data_aplicacao = " + aplicacao.getDataAplicacao() + ", " +
                "aplicada = " + aplicacao.isAplicada() + ", " +
                "WHERE _id = " + aplicacao.getId() + " AND " +
                "id_paciente = " + pacienteId + " AND " +
                "id_dose = " + doseId);

        return aplicacao;
    }

    public ArrayList<Aplicacao> getAplicacaoByPaciente(int pacienteId) {

        ArrayList<Aplicacao> aplicacoes = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                "SELECT " +
                    "aplicacao._id, aplicacao.data_aplicacao, " +
                    "aplicacao.aplicada, dose.nome, vacina.nome " +
                "FROM dose INNER JOIN vacina " +
                    "ON dose.id_vacina = vacina. _id " +
                "INNER JOIN aplicacao " +
                    "ON aplicacao.id_dose = dose._id " +
                "WHERE aplicacao.id_paciente = " + pacienteId + " " +
                    "AND aplicacao.aplicada = 0 " +
                "ORDER BY indicacao ASC", null);

        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            Aplicacao aplicacao = new Aplicacao();
            aplicacao.setId(cursor.getInt(0));
            aplicacao.setDataAplicacao(cursor.getString(1));
            aplicacao.setAplicada(cursor.getInt(2));
            aplicacao.setNomeDose(cursor.getString(3));
            aplicacao.setNomeVacina(cursor.getString(4));

            aplicacoes.add(aplicacao);

            cursor.moveToNext();
        }

        return aplicacoes;
    }
}
