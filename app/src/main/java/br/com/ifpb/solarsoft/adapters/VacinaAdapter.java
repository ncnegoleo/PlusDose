package br.com.ifpb.solarsoft.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.ifpb.solarsoft.R;
import br.com.ifpb.solarsoft.entities.Aplicacao;
import br.com.ifpb.solarsoft.entities.Item;
import br.com.ifpb.solarsoft.entities.SectionItem;

/**
 * Created by leonardo on 21/05/2015.
 */
public class VacinaAdapter extends ArrayAdapter<Item> {

    private Context context;
    private ArrayList<Item> items;
    private LayoutInflater inflater;

    public VacinaAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        Item i = items.get(position);
        if(i != null) {
            if(i.isSection()) {
                SectionItem si = (SectionItem) i;
                v = inflater.inflate(R.layout.list_item_section, null);
                v.setOnClickListener(null);
                v.setOnLongClickListener(null);
                v.setClickable(false);
                TextView tvSection = (TextView) v.findViewById(R.id.tv_section_title);

                tvSection.setText(si.getTitle());
            } else {
                Aplicacao apd = (Aplicacao) i;
                Log.e("APD", apd.toString());
                v = inflater.inflate(R.layout.list_item_vacina, null);
                TextView nomeVacina = (TextView) v.findViewById(R.id.tv_vacina);
                TextView nomeDose = (TextView) v.findViewById(R.id.tv_dose);
                TextView dataAplicacao = (TextView) v.findViewById(R.id.data_aplcacao);

                nomeVacina.setText(apd.getNomeVacina());
                nomeDose.setText(apd.getNomeDose());
                dataAplicacao.setText(apd.getDataAplicacao());
            }
        }

        return v;
    }
}
