package br.com.ifpb.solarsoft.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.ifpb.solarsoft.R;
import br.com.ifpb.solarsoft.entities.Paciente;

/**
 * Created by leonardo on 16/05/2015.
 */
public class PacienteUtil{

    Paciente paciente;
    Context context;
    private int anos;
    private int meses;
    private int dias;

    public PacienteUtil(Paciente paciente, Context context) {
        this.paciente = paciente;
        this.context = context;
        setIdade();
    }


    public Uri requestPacientePhoto() {
        Uri uri;
        int idade = getAnos();
        String resource = ContexPaths.RESOUCER.getContexPath() + context.getPackageName() + "/";

        if(paciente.getFoto() == null || paciente.getFoto().equals("")) {
            if (idade >= 0 && idade <= 3) {
                uri  = Uri.parse(resource + R.drawable.baby_photo);
            } else if(idade > 3 && idade <= 12) {
                uri  = Uri.parse(resource + R.drawable.children_photo);
            } else if(idade > 12 && idade <= 20) {
                uri  = Uri.parse(resource + R.drawable.teenager_photo);
            } else if(idade > 20 && idade <= 60) {
                uri  = Uri.parse(resource + R.drawable.adult_photo);
            } else {
                uri  = Uri.parse(resource + R.drawable.elderly_photo);
            }
        } else {
            uri = Uri.parse(paciente.getFoto());
        }

        return uri;
    }

    public String requestIdade() {
        return anos + (anos > 1 ? " anos " : " ano ")
                + meses + (meses > 1 ? " meses " : " mes ")
                + " e " + dias + (dias > 1 ? " dias " : " dia ");
    }

    private void setIdade() {
        DateTime start = new DateTime(paciente.getDataDeNascimento());
        DateTime end = DateTime.now();

        Period p = new Period(start,end);

        this.dias = p.getDays();
        this.meses = p.getMonths();
        this.anos = p.getYears();
    }

    public int getAnos() {
        return anos;
    }

    public int getMeses() {
        return meses;
    }

    public int getDias() {
        return dias;
    }
}
