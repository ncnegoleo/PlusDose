package br.com.ifpb.solarsoft.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import br.com.ifpb.solarsoft.R;
import br.com.ifpb.solarsoft.adapters.VacinaAdapter;
import br.com.ifpb.solarsoft.entities.Aplicacao;
import br.com.ifpb.solarsoft.entities.Item;
import br.com.ifpb.solarsoft.entities.SectionItem;
import br.com.ifpb.solarsoft.sql.AplicacaoDao;

/**
 * Created by leonardo on 24/05/2015.
 */
public class FragmentVacinasPendentes extends Fragment {

    private ListView lvPendentes;
    ArrayList<Item> items = new ArrayList<>();
    AplicacaoDao aplicacaoDao;
    private static final String TAG = FragmentVacinasPendentes.class.getSimpleName();
    private boolean isViewShown = false;

    private boolean menordeUmMes = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_vainas_pendentes, container, false);
        lvPendentes = (ListView) view.findViewById(R.id.lv_doses_pendentes);

        if (!isViewShown) {
            initDao();
        }

        lvPendentes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aplicacao appAux = (Aplicacao) items.get(position);
                appAux.setAplicada(1);
                aplicacaoDao.update(appAux, 1, appAux.getIdDose()); // Pegar via intent o id do paciente
                items.remove(position);
                lvPendentes.setAdapter(new VacinaAdapter(getActivity(), items));
            }
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: " + isVisibleToUser);

        if (getView() != null) {
            isViewShown = true;
            if(isVisibleToUser) {
                lvPendentes.setAdapter(new VacinaAdapter(getActivity(), items));
            }
        } else {
            isViewShown = false;
        }
    }

    public void initDao() {
        aplicacaoDao  = new AplicacaoDao(getActivity());
        lvPendentes.setAdapter(new VacinaAdapter(getActivity(), items));

        ArrayList<Aplicacao> aplicacaos = aplicacaoDao.getAplicacaoByPaciente(1, 0);

        for (Aplicacao app : aplicacaos) {
            if(app.getIndicacao() <= 0.1 && !menordeUmMes) {
                SectionItem si = new SectionItem("MENOR QUE UM MES");
                items.add(si);
                menordeUmMes = true;
            }
            items.add(app);
        }
    }

}
