package br.com.ifpb.solarsoft.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import br.com.ifpb.solarsoft.R;
import br.com.ifpb.solarsoft.entities.Aplicacao;
import br.com.ifpb.solarsoft.entities.Paciente;
import br.com.ifpb.solarsoft.sql.AplicacaoDao;
import br.com.ifpb.solarsoft.sql.PacienteDao;
import br.com.ifpb.solarsoft.util.PacienteUtil;
import br.com.ifpb.solarsoft.util.WarningDialogHelper;

public class PacienteDetailsActivity extends AppCompatActivity {

    private final String TAG = "LOG";
    private Toolbar aToolbar;

    private PacienteDao pacienteDao;
    private Paciente paciente;
    private Integer pacienteId;

    private ImageView imgeFoto;
    private TextView tvNomeSobrenome, tvDataNascimmento, tvSexo;

    public static final String SEND_ID_TO_EDIT = "br.com.ifpb.solarsoft.SEND_ID_TO_EDIT";
    public static final String INTENT_KEY_ID = "br.com.ifpb.solarsoft.ID_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente_details);

        aToolbar = (Toolbar) findViewById(R.id.tb_main);
        aToolbar.setTitle("Main Activity");
        aToolbar.setSubtitle("Just Subtitle");
        setSupportActionBar(aToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pacienteDao = new PacienteDao(this);


        AplicacaoDao aplicacaoDao = new AplicacaoDao(this);
        Aplicacao apli = new Aplicacao();
        apli.setDataAplicacao("12/10/2012");
        apli.setAplicada(0);
        aplicacaoDao.save(apli, 1, 1);

        componentsInit();

        // Reucupera o um conteudo passado por intent.
        Bundle extras = getIntent().getExtras();

        // Verifica se o id é recuperado não é nulo.
        if(extras != null) {
            // Recupera o id do paciente passado pelo intent a partir de uma chave.
            pacienteId = extras.getInt(PacientesActivity.INTENT_KEY_ID);
            if(pacienteId == null) {
                // Se o id for nulo é setado o valor 0 (zero) ao id.
                pacienteId = 0;
            }
        }

        // Recupera um paciente do banco de dados a partir do id recuperado.
        paciente = pacienteDao.getById(pacienteId);
        Log.e("Paciente: ", paciente.toString());

        fillDetails();
    }

    /**
     * Inicia os componentes do layout
     */
    private void componentsInit() {
        imgeFoto = (ImageView) findViewById(R.id.img_foto_detalhes);
        tvNomeSobrenome = (TextView) findViewById(R.id.tv_nome_details);
        tvDataNascimmento = (TextView) findViewById(R.id.tv_data_details);
        tvSexo = (TextView) findViewById(R.id.tv_sexo_details);
    }

    /**
     * Preenche os detalhes do paciente no componentes correspondentes a cada um
     */
    private void fillDetails() {

        PacienteUtil pacienteUtil = new PacienteUtil(paciente, this);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        imgeFoto.setImageURI(pacienteUtil.requestPacientePhoto());
        tvNomeSobrenome.setText(paciente.getNome() + " " + paciente.getSobrenome());
        tvDataNascimmento.setText(sdf.format(paciente.getDataDeNascimento()));

        if(paciente.getSexo() == 'M')
            tvSexo.setText(getString(R.string.masculino));
        else
            tvSexo.setText(getString(R.string.feminino));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_paciente_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Ação do botãoo home (volta a tela de listagem dos pacientes)
        if(id == android.R.id.home) {
            Intent intent = new Intent(this, PacientesActivity.class);
            startActivity(intent);
            finish();
        }

        // Ação de editar o paciente (direciona a tela de cadastro do paciente)
        if (id == R.id.action_editar_paciente) {
            Intent intent = new Intent(SEND_ID_TO_EDIT);
            intent.putExtra(INTENT_KEY_ID, paciente.getId());
            startActivity(intent);
            finish();
        }

        // Ação de remover o paciente
        if (id == R.id.action_deletar_paciente) {
            new WarningDialogHelper(this, getString(R.string.confirmar),
                    getString(R.string.confirmar_delecao_paciente), getString(R.string.sim),
                    getString(R.string.nao), deletePaciente(), WarningDialogHelper.getEmpityAction(),
                    WarningDialogHelper.NO_ICON);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Remove/Deleta o paciente
     * @return Runnable que é definida uma ação a ser requisitada
     */
    private Runnable deletePaciente() {
        return new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), getString(R.string.info_paciente) + " "
                        + paciente.getNome() + " " + getString(R.string.info_deletar),
                        Toast.LENGTH_SHORT).show();

                pacienteDao.delete(paciente);
                Intent intent = new Intent(getApplicationContext(), PacientesActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }

    public void mostrarVacinas(View view) {
        Intent intent = new Intent(this, AplicacaoActivty.class);
        startActivity(intent);
    }
}
