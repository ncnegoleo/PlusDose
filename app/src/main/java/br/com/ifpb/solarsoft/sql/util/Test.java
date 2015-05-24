package br.com.ifpb.solarsoft.sql.util;

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {

		SSColumn vacinaPk = new SSColumn("_id",
				SSColumn.INTEGER, true, true);
		SSColumn vacinaNome = new SSColumn("nome",
				SSColumn.TEXT, 50, false);

		SSTable vacinaTable = new SSTable("vacina", vacinaPk,
				vacinaNome);
		vacinaTable.setJoin("fk_dose_vacina", "dose", "id_dose", "_id",
				SSTable.CASCADE, SSTable.CASCADE);
		vacinaTable.setJoin("fk_dose_vacina", "dose", "id_dose", "_id",
				SSTable.NO_ACTION, SSTable.CASCADE);
		
		System.out.println(vacinaTable);
		
		System.out.println(vacinaTable.getTableName());
		String[] vacinaColumns = vacinaTable.getColumns();
		
		for (String s : vacinaColumns) {
			System.out.println(s);
		}
	}
}
