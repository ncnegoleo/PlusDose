package br.com.ifpb.solarsoft.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.ifpb.solarsoft.sql.util.SSColumn;
import br.com.ifpb.solarsoft.sql.util.SSTable;

/**
 * Created by leonardo on 18/05/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static SSTable pacienteTable;
    public static SSTable vacinaTable;
    public static SSTable doseTable;
    public static SSTable aplicacaoTable;

    SQLiteDatabase db;

    private static final String NOME_DB = "plusdosedb";
    private static final int VERSAO_DB = 32;

    public DatabaseHelper(Context context) {
        super(context, NOME_DB, null, VERSAO_DB);

        buildPacienteTable();
        buildVacinaTable();
        buildDoseTable();
        buildAplicacaoTable();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        db.execSQL(pacienteTable.create());
        db.execSQL(vacinaTable.create());
        db.execSQL(doseTable.create());
        db.execSQL(aplicacaoTable.create());

        db.execSQL("" +
                "CREATE TRIGGER main.delete_paciente BEFORE DELETE ON paciente FOR EACH ROW " +
                "BEGIN " +
                "DELETE FROM aplicacao WHERE aplicacao.id_paciente = old._id; " +
                "END;");

        insertVacinas();
        insertDoses();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(pacienteTable.drop());
        db.execSQL(vacinaTable.drop());
        db.execSQL(doseTable.drop());
        db.execSQL(aplicacaoTable.drop());

        onCreate(db);
    }

    private void buildPacienteTable() {

        SSColumn pacientePk             = new SSColumn("_id", SSColumn.INTEGER, true, true);
        SSColumn pacienteNome           = new SSColumn("nome", SSColumn.TEXT, false);
        SSColumn pacienteSobrenome      = new SSColumn("sobrenome", SSColumn.TEXT, false);
        SSColumn pacienteSexo           = new SSColumn("sexo", SSColumn.TEXT, false);
        SSColumn pacienteDataNascimento = new SSColumn("data_nasc", SSColumn.TEXT, false);
        SSColumn pacienteFoto           = new SSColumn("foto", SSColumn.TEXT, false);
        SSColumn pacienteListado        = new SSColumn("listado",SSColumn.INTEGER, false);

        pacienteTable = new SSTable("paciente", pacientePk, pacienteNome,
                pacienteSobrenome, pacienteSexo, pacienteDataNascimento, pacienteFoto, pacienteListado);
    }

    private void buildVacinaTable() {

        SSColumn vacinaPk        = new SSColumn("_id", SSColumn.INTEGER, true, true);
        SSColumn vacinaNome      = new SSColumn("nome", SSColumn.TEXT, false);
        SSColumn vacinaDescricao = new SSColumn("descricao", SSColumn.TEXT, true);

        vacinaTable = new SSTable("vacina", vacinaPk, vacinaNome, vacinaDescricao);
    }

    private void buildDoseTable() {

        SSColumn dosePk        = new SSColumn("_id", SSColumn.INTEGER, true, true);
        SSColumn doseName      = new SSColumn("nome", SSColumn.TEXT, false);
        SSColumn doseIndicacao = new SSColumn("indicacao", SSColumn.REAL, false);
        SSColumn doseFkVacina  = new SSColumn("id_vacina", SSColumn.INTEGER, false);

        doseTable = new SSTable("dose", dosePk,doseName, doseIndicacao, doseFkVacina);

        doseTable.setJoin("fk_dose_vacina", vacinaTable.getTableName(), doseFkVacina.getColumn(),
                "_id", SSTable.NO_ACTION, SSTable.NO_ACTION);
    }

    private void buildAplicacaoTable() {

        SSColumn aplicacaoPk         = new SSColumn("_id", SSColumn.INTEGER, true, true);
        SSColumn aplicacaoDataApl    = new SSColumn("data_aplicacao", SSColumn.TEXT, true);
        SSColumn aplicacaoAplicada   = new SSColumn("aplicada", SSColumn.INTEGER, true);
        SSColumn aplicacaoFkPaciente = new SSColumn("id_paciente", SSColumn.INTEGER, false);
        SSColumn aplicacaoFkDose     = new SSColumn("id_dose", SSColumn.INTEGER, false);

        aplicacaoTable = new SSTable("aplicacao", aplicacaoPk, aplicacaoDataApl,
                aplicacaoAplicada, aplicacaoFkPaciente, aplicacaoFkDose);

//        aplicacaoTable.setJoin("fk_aplicacao_paciente", pacienteTable.getTableName(),
//                aplicacaoFkPaciente.getColumn(), "_id", SSTable.CASCADE, SSTable.NO_ACTION);

        aplicacaoTable.setJoin("fk_aplicacao_dose", doseTable.getTableName(),
                aplicacaoFkDose.getColumn(), "_id", SSTable.NO_ACTION, SSTable.NO_ACTION);
    }

    private void insertVacinas() {

        String[][] vacinaData = new String[][] {
                {"BCG", "descricao"}, // BCG (ID 1)
                {"Hepatite B", "descricao"}, // Hepatite B (ID 2)
                {"Penta", "descricao"}, // Penta (ID 3)
                {"DTP", "descricao"}, // DTP
                {"VIP", "descricao"}, // VIP (ID 4)
                {"VOP", "descricao"}, // VOP (ID 5)
                {"Pneumo 10", "descricao"}, // Peneumococica 10 (ID 6)
                {"Rotavírus", "descricao"}, // Oral Rotavírus (ID 7)
                {"Meningo C", "descricao"}, // Menigo Conjugada C (ID 8)
                {"Febre Amarela", "descricao"}, // Febre Aramarela (ID 9)
                {"Hepatite A", "descricao"}, // Hepatite A (ID 10)
                {"Tríplice Viral", "descricao"}, // Tríplice Viral (ID 11)
                {"Tetra Viral", "descricao"}, // Tetra Viral (ID 12)
                {"HPV", "descrcao"}, // HPV (ID 13)
                {"Dupla Adulto", "descricao"}, // Dupla Adulto (ID 14)
                {"dTpa", "descricao"} // dTpa (ID 15)
        };

        for(int i = 0; i < vacinaData.length; i++) {
            db.execSQL("INSERT INTO " + vacinaTable.getTableName() + "('nome', 'descricao') " +
                    "VALUES('" + vacinaData[i][0] +"', '"+vacinaData[i][1]+"')");
        }
    }

    private void insertDoses() {

        String[][] doseData = new String[][] {
                // {'nome da dose' , faixa-etária, id da vacina}
                {"Dose Única", "0.0", "1"}, // 1ª Dose de Hepatite B
                {"1ª Dose", "0.0", "2"}, // 1ª Dose de Hepatite B
                {"2ª Dose", "19.0", "2"}, // 2ª Dose de Hepatite B
                {"3ª Dose", "29.0", "2"},  // 3ª Dose de Hepatite B
                {"1ª Dose", "0.2", "3"}, // 1ª Dose de Penta
                {"2ª Dose", "0.4", "3"}, // 2ª Dose de Penta
                {"3ª Dose", "0.6", "3"}, // 3ª Dose de Penta
                {"1º Reforço", "1.3", "4"}, // 1º Reforço de DTP
                {"2º Reforço", "4.0", "4"}, // 2º Reforço de DTP
                {"1ª Dose", "0.2", "5"}, // 1ª Dose de VIP
                {"2ª Dose", "0.4", "5"}, // 2ª Dose de Vip
                {"3ª Dose", "0.6", "6"} // 3ª Dose de Vop
        };

        for (int i = 0; i < doseData.length; i++) {
            db.execSQL(
                    "INSERT INTO " + doseTable.getTableName() + "(nome, indicacao, id_vacina) " +
                    "VALUES('"+doseData[i][0]+"', "+doseData[i][1]+", "+doseData[i][2]+")");
        }
    }

}
