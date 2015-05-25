package br.com.ifpb.solarsoft.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.com.ifpb.solarsoft.R;
import br.com.ifpb.solarsoft.fragments.FragmentVacinasPendentes;
import br.com.ifpb.solarsoft.fragments.FragmentVacinasTomadas;

public class AplicacaoActivty extends AppCompatActivity {

    private Toolbar aToolbar;
    private final int TITULO = R.string.title_activity_vacinas;
    private final int SUBTITULO = R.string.subtitle_activity_vacinas;

    String[] list2;

    CustomPagerAdapter adapterViewPager;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplicacao);

        createToolbar();

//        if(savedInstanceState == null) {
//            FragmentVacinasPendentes fragVP = new FragmentVacinasPendentes();
//            FragmentVacinasTomadas fragVT = new FragmentVacinasTomadas();
//
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.add(R.id.pager, fragVP, "frag_vp");
//            ft.commit();
//        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapterViewPager = new CustomPagerAdapter(fm);
        viewPager.setAdapter(adapterViewPager);
    }

    class CustomPagerAdapter extends FragmentPagerAdapter {


        final String[] TAB_TITLES = new String[]{
                getString(R.string.tab_pendentes),
                getString(R.string.tab_tomadas)};

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                FragmentVacinasPendentes fvp = new FragmentVacinasPendentes();
                return fvp;
            } else {
                FragmentVacinasTomadas fvt = new FragmentVacinasTomadas();
                return fvt;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TAB_TITLES[position];
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Ação do botão home (botão de seta na toolbar)
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // Cria a toolbar da Activity
    private void createToolbar() {

        aToolbar = (Toolbar) findViewById(R.id.tb_main);
        aToolbar.setTitle(getString(TITULO));
        aToolbar.setSubtitle(getString(SUBTITULO));

        // Inicia o suporte ao toobar como uma actionbar
        setSupportActionBar(aToolbar);

        // Mostra o botão UP
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
