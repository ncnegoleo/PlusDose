package br.com.ifpb.solarsoft.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by leonardo on 18/05/2015.
 */
public abstract class DataBase {

    protected SQLiteDatabase db;

    public DataBase(Context context) {
        DatabaseHelper auxDb = new DatabaseHelper(context);
        db = auxDb.getWritableDatabase();
    }

}
