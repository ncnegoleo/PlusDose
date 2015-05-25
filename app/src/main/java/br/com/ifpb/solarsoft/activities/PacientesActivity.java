package br.com.ifpb.solarsoft.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.ifpb.solarsoft.R;
import br.com.ifpb.solarsoft.adapters.PacienteAdapter;
import br.com.ifpb.solarsoft.entities.Paciente;
import br.com.ifpb.solarsoft.sql.PacienteDao;

public class PacientesActivity extends AppCompatActivity {

    private final String TAG = "LOG";
    private Toolbar aToolbar;

    PacienteDao pacienteDao;
    ListView lvPacients;

    public static final String SEND_ID_TO_DETAILS = "br.com.ifpb.solarsoft.SEND_ID_TO_DETAILS";
    public static final String INTENT_KEY_ID = "br.com.ifpb.solarsoft.ID_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);

        aToolbar = (Toolbar) findViewById(R.id.tb_main);
        aToolbar.setTitle("Main Activity");
        aToolbar.setSubtitle("Just Subtitle");
        setSupportActionBar(aToolbar);

        pacienteDao = new PacienteDao(this);

        componentsInit();

        final ArrayList<Paciente> pacientes = pacienteDao.getAll();

        if(pacientes.size() <= 0) {
            //showToastTip();
        }

        lvPacients.setAdapter(new PacienteAdapter(this, pacientes));

        lvPacients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SEND_ID_TO_DETAILS);
                intent.putExtra(INTENT_KEY_ID, pacientes.get(position).getId());
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Inicia os componentes do Layout
     */
    private void componentsInit() {
        lvPacients = (ListView) findViewById(R.id.lv_pacientes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_pacientes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.ac_add_paciente) {
            Intent intent = new Intent(this, CadastroPacienteActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showToastTip() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.pacientes_empty_custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.toastText);
        text.setText(R.string.info_toast_paciente_lista_1);
        TextView text2 = (TextView) layout.findViewById(R.id.toastText2);
        text2.setText(getString(R.string.info_toast_paciente_lista_2));

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 10);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

}
