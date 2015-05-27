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

    private final String LOG = this.getClass().getSimpleName();

    public AplicacaoDao(Context context) {
        super(context);
    }

    /**
     * insere todas as doses necessárias para o paciente.
     * @param pacienteId id do paciente.
     */
    public void saveAllDoses(int pacienteId) {

        // Recupera a quantidade das doses cadastradas no sistemas
        int count = getDosesCout();

        // Percorre todas doses adicionando uma referencia para o paciente
        for (int i = 1; i <= count; i++) {

            Log.i(LOG, "INSERT INTO aplicacao " +
                    "(data_aplicacao, aplicada, id_paciente, id_dose)" +
                    "VALUES ('Não aplicada', 0, " + pacienteId + ", " + i + ")");

            db.execSQL("INSERT INTO aplicacao " +
                    "(data_aplicacao, aplicada, id_paciente,  id_dose) " +
                    "VALUES ('Não aplicada', 0, " + pacienteId + ", " + i + ")");
        }
    }

    /**
     * Atualiza uma dose do paciente a partir de um objeto aplicação.
     * @param aplicacao aplicação a ser atualizada.
     * @param pacienteId id do Paciente
     * @param doseId id da Dose
     * @return aplicação atualizada.
     */
    public Aplicacao update(Aplicacao aplicacao, int pacienteId, int doseId) {

        Log.i(LOG, "UPDATE aplicacao " +
                "SET data_aplicacao = '" + aplicacao.getDataAplicacao() + "', " +
                "aplicada = " + aplicacao.isAplicada() + " " +
                "WHERE _id = " + aplicacao.getId() + " AND " +
                "id_paciente = " + pacienteId + " AND " +
                "id_dose = " + doseId);

        db.execSQL("UPDATE aplicacao " +
                "SET data_aplicacao = '" + aplicacao.getDataAplicacao() + "', " +
                "aplicada = " + aplicacao.isAplicada() + " " +
                "WHERE _id = " + aplicacao.getId() + " AND " +
                "id_paciente = " + pacienteId + " AND " +
                "id_dose = " + doseId);

        return aplicacao;
    }

    /**
     * Recupera todas as doses relacionadas a um paciente.
     * @param pacienteId id do paciente
     * @param aplicada busca por doses não aplicadas (0) ou aplicadas (1)
     * @return lista de doses.
     */
    public ArrayList<Aplicacao> getAplicacaoByPaciente(int pacienteId, int aplicada) {

        ArrayList<Aplicacao> aplicacoes = new ArrayList<>();

        Log.i(LOG,"SELECT " +
                "aplicacao._id, aplicacao.data_aplicacao, " +
                "aplicacao.aplicada, dose.nome, vacina.nome, " +
                "dose._id, dose.indicacao " +
                "FROM dose INNER JOIN vacina " +
                "ON dose.id_vacina = vacina. _id " +
                "INNER JOIN aplicacao " +
                "ON aplicacao.id_dose = dose._id " +
                "WHERE aplicacao.id_paciente = " + pacienteId + " " +
                "AND aplicacao.aplicada = " + aplicada + " " +
                "ORDER BY indicacao ASC");

        Cursor cursor = db.rawQuery(
                "SELECT " +
                    "aplicacao._id, aplicacao.data_aplicacao, " +
                    "aplicacao.aplicada, dose.nome, vacina.nome, " +
                    "dose._id, dose.indicacao " +
                "FROM dose INNER JOIN vacina " +
                    "ON dose.id_vacina = vacina. _id " +
                "INNER JOIN aplicacao " +
                    "ON aplicacao.id_dose = dose._id " +
                "WHERE aplicacao.id_paciente = " + pacienteId + " " +
                    "AND aplicacao.aplicada = " + aplicada + " " +
                "ORDER BY indicacao ASC", null);

        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {

            Aplicacao aplicacao = new Aplicacao();

            aplicacao.setId(cursor.getInt(0));
            aplicacao.setDataAplicacao(cursor.getString(1));
            aplicacao.setAplicada(cursor.getInt(2));
            aplicacao.setNomeDose(cursor.getString(3));
            aplicacao.setNomeVacina(cursor.getString(4));
            aplicacao.setIdDose(cursor.getInt(5));
            aplicacao.setIndicacao(cursor.getDouble(6));
            aplicacoes.add(aplicacao);

            cursor.moveToNext();
        }

        return aplicacoes;
    }

    private int getDosesCout() {
        Cursor cursor = db.rawQuery("SELECT * FROM dose", null);
        return cursor.getCount();
    }

}
