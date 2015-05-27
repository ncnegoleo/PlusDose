package br.com.ifpb.solarsoft.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.ifpb.solarsoft.entities.Paciente;

/**
 * Created by leonardo on 18/05/2015.
 */
public class PacienteDao extends DataBase implements GenericDao<Paciente, Integer>{

    public PacienteDao(Context context) {
        super(context);
    }

    @Override
    public void create(Paciente paciente) {
        ContentValues valores = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        valores.put("nome", paciente.getNome());
        valores.put("sobrenome", paciente.getSobrenome());
        valores.put("data_nasc", sdf.format(paciente.getDataDeNascimento()));
        valores.put("sexo", paciente.getSexo() + "");
        valores.put("foto", paciente.getFoto());
        valores.put("listado", paciente.getListado());

        db.insert(DatabaseHelper.pacienteTable.getTableName(), null, valores);
    }

    @Override
    public Paciente update(Paciente paciente) {
        ContentValues valores = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        valores.put("nome", paciente.getNome());
        valores.put("sobrenome", paciente.getSobrenome());
        valores.put("data_nasc", sdf.format(paciente.getDataDeNascimento()));
        valores.put("sexo", paciente.getSexo() + "");
        valores.put("foto", paciente.getFoto());
        valores.put("listado", paciente.getListado());

        db.update(DatabaseHelper.pacienteTable.getTableName(), valores, "_id = ?",
                new String[]{""+paciente.getId()});

        return paciente;
    }

    @Override
    public boolean delete(Paciente paciente) {
        return db.delete(DatabaseHelper.pacienteTable.getTableName(), "_id = ?",
                new String[]{"" + paciente.getId()}) > 0;
    }

    @Override
    public Paciente getById(Integer id) {
        Paciente paciente = new Paciente();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate = new Date();

        Cursor cursor = db.query(DatabaseHelper.pacienteTable.getTableName(),
                DatabaseHelper.pacienteTable.getColumns(), "_id = ?",
                new String[]{""+id}, null, null, "nome ASC");

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {
                paciente.setId(cursor.getInt(0));
                paciente.setNome(cursor.getString(1));
                paciente.setSobrenome(cursor.getString(2));
                paciente.setSexo(cursor.getString(3).charAt(0));
                try {parsedDate = sdf.parse(cursor.getString(4));}
                catch (ParseException e) {}
                paciente.setDataDeNascimento(parsedDate);
                paciente.setFoto(cursor.getString(5));
                paciente.setListado(cursor.getInt(6));
            } while (cursor.moveToNext());
        }

        return paciente;
    }

    @Override
    public ArrayList<Paciente> getAll() {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date parsedDate = new Date();

        Cursor cursor = db.query(DatabaseHelper.pacienteTable.getTableName(),
                DatabaseHelper.pacienteTable.getColumns(), null, null, null, null, "nome ASC");

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {
                Paciente paciente = new Paciente();
                paciente.setId(cursor.getInt(0));
                paciente.setNome(cursor.getString(1));
                paciente.setSobrenome(cursor.getString(2));
                paciente.setSexo(cursor.getString(3).charAt(0));
                try {parsedDate = sdf.parse(cursor.getString(4));}
                catch (ParseException e) {}
                paciente.setDataDeNascimento(parsedDate);
                paciente.setFoto(cursor.getString(5));
                paciente.setListado(cursor.getInt(6));
                pacientes.add(paciente);
            } while (cursor.moveToNext());
        }

        return pacientes;
    }
}
