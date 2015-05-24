package br.com.ifpb.solarsoft.adapters;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.ifpb.solarsoft.R;
import br.com.ifpb.solarsoft.entities.Paciente;
import br.com.ifpb.solarsoft.util.PacienteUtil;

/**
 * Created by leonardo on 13/05/2015.
 */
public class PacienteAdapter extends BaseAdapter{

    private ImageView foto;
    private TextView idadePaciente, nomePaciente;

    private Context context;

    private ArrayList<Paciente> lista;

    private Paciente paciente;
    private PacienteUtil pacienteUtil;

    public PacienteAdapter(Context context, ArrayList<Paciente> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        paciente = lista.get(position);
        pacienteUtil = new PacienteUtil(paciente, context);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.list_item_pacientes, null);

        nomePaciente = (TextView) layout.findViewById(R.id.tv_nome_paciente);
        nomePaciente.setText(Html.fromHtml(paciente.getNome()));

        idadePaciente = (TextView) layout.findViewById(R.id.tv_idade_paciente);
        Calendar dataNasc = Calendar.getInstance();
        dataNasc.setTime(paciente.getDataDeNascimento());
        idadePaciente.setText(pacienteUtil.requestIdade());

        foto = (ImageView) layout.findViewById(R.id.img_paciente);
        foto.setImageURI(pacienteUtil.requestPacientePhoto());

        return layout;
    }



}
