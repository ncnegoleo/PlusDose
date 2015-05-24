package br.com.ifpb.solarsoft.sql.util;

import java.util.ArrayList;

public class SSTable {

	private String sql = "";
	private String tableName;
	private ArrayList<String> columns = new ArrayList<>();
	
	public static String RESTRICT = "RESTRICT";
	public static String NO_ACTION = "";
	public static String CASCADE = "CASCADE";
	public static String SET_NULL = "SET_NULL";
	public static String SET_DEFAULT = "SET_DEFAULT";
	

	public SSTable(String tableName, SSColumn... column) {
		this.tableName = tableName;
		this.sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(\n\t";
		for (int i = 0; i <= (column.length - 1); i++) {
			this.columns.add(column[i].getColumn());
			this.sql += column[i];
			if (i != (column.length - 1))
				this.sql += ", \n\t";
		}
	}

	public void setJoin(String constraintName, String joinTableName,
			String foreignKey, String primaryKey, String onDelete,
			String onUpdate) {
		this.sql += ",  \n\tCONSTRAINT " + constraintName + " FOREIGN KEY ("
				+ primaryKey + ") " + "REFERENCES " + joinTableName + "("
				+ foreignKey + ")"
				+ (onDelete.equals("") ? "" : " ON DELETE " + onDelete) + " "
				+ (onUpdate.equals("") ? "" : " ON UPDATE " + onUpdate);
	}

	public String getTableName() {
		return tableName;
	}
	
	public String[] getColumns() {
		String[] aux = new String[columns.size()];
		return columns.toArray(aux);
	}

	public String drop() {
		return  "DROP TABLE IF EXISTS " + tableName;
	}

	public String create() {
		return this.sql + "\n);";
	}
}
