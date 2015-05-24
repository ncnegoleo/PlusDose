package br.com.ifpb.solarsoft.sql.util;

public class SSColumn {

	private String sql;
	private String column;

	public static String INTEGER = "INTEGER";
	public static String REAL = "REAL";
	public static String TEXT = "TEXT";
	public static String BLOB = "BLOB";

	public SSColumn(String name, String type, boolean pk,
					boolean autoincrement) {
		this.column = name;
		this.sql = name + " " + type + " " + (pk ? " PRIMARY KEY " : "")
				+ (autoincrement ? " AUTOINCREMENT" : "");
	}

	public SSColumn(String name, String type, boolean nullabe) {
		this.column = name;
		this.sql = name + " " + type + " " + (nullabe ? "" : " NOT NULL");
	}

	public SSColumn(String name, String type, int lenght, boolean nullabe) {
		this.column = name;
		this.sql = name + " " + type + "(" + lenght + ")"
				+ (nullabe ? "" : " NOT NULL");
	}

	public String getColumn() {
		return column;
	}
	
	@Override
	public String toString() {
		return this.sql;
	}

}
