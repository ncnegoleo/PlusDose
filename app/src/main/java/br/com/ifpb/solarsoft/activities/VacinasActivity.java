package br.com.ifpb.solarsoft.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import br.com.ifpb.solarsoft.R;
import br.com.ifpb.solarsoft.adapters.VacinaAdapter;
import br.com.ifpb.solarsoft.entities.Aplicacao;
import br.com.ifpb.solarsoft.entities.Dose;
import br.com.ifpb.solarsoft.entities.Item;
import br.com.ifpb.solarsoft.entities.SectionItem;
import br.com.ifpb.solarsoft.entities.Vacina;
import br.com.ifpb.solarsoft.sql.AplicacaoDao;

public class VacinasActivity extends ActionBarActivity {

    private ListView lvVacinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacinas);

        componentsInit();

        ArrayList<Item> items = new ArrayList<>();

        AplicacaoDao aplicacaoDao = new AplicacaoDao(this);

        Aplicacao apli = new Aplicacao();
        apli.setDataAplicacao("12/10/2012");
        apli.setAplicada(0);

        aplicacaoDao.save(apli, 1, 1);

        ArrayList<Aplicacao> aplicacaos = aplicacaoDao.getAplicacaoByPaciente(1);

        for (Aplicacao app : aplicacaos) {
            items.add(app);
        }

        SectionItem si1 = new SectionItem("menos que 1 ano");

        Aplicacao apd1 = new Aplicacao();
        apd1.setNomeDose("Dose Única");
        apd1.setNomeVacina("BCG");
        apd1.setDataAplicacao("12/10/2014");

        SectionItem si2 = new SectionItem("2 anos e 3 meses");

        Aplicacao apd2 = new Aplicacao();
        apd2.setNomeDose("1ª Dose");
        apd2.setNomeVacina("Hepatite B");
        apd2.setDataAplicacao("12/10/2014");

        items.add(si1);
        items.add(apd1);
        items.add(si2);
        items.add(apd2);

        lvVacinas.setAdapter(new VacinaAdapter(this, items));
    }

    private void componentsInit() {
        lvVacinas = (ListView) findViewById(R.id.lv_doses);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vacinas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
