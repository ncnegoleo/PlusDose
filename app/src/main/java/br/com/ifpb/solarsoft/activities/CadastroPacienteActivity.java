package br.com.ifpb.solarsoft.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import br.com.ifpb.solarsoft.R;
import br.com.ifpb.solarsoft.entities.Paciente;
import br.com.ifpb.solarsoft.sql.PacienteDao;
import br.com.ifpb.solarsoft.util.PacienteUtil;
import br.com.ifpb.solarsoft.util.WarningDialogHelper;
import br.com.ifpb.solarsoft.util.DateUtil;
import br.com.ifpb.solarsoft.security.ValidateForm;

public class CadastroPacienteActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {

    private final String TAG = "LOG";
    private Toolbar aToolbar;

    Paciente paciente;
    PacienteDao pacienteDao;
    PacienteUtil pacienteUtil;

    private ImageView imgFoto;
    private EditText eTNome, eTSobrenome, eTData;
    private RadioButton rbMasculino, rbFeminino;

    private char sexo = 'E';
    private String uriFoto = "";
    private String uuid;
    private Integer pacienteId;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private static final int LOAD_IMAGE_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_paciente);

        aToolbar = (Toolbar) findViewById(R.id.tb_main);
        aToolbar.setTitle("Main Activity");
        aToolbar.setSubtitle("Just Subtitle");
        setSupportActionBar(aToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pacienteDao = new PacienteDao(this);
        paciente = new Paciente();

        componentsInit();

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            pacienteId = extras.getInt(PacientesActivity.INTENT_KEY_ID);
            if(pacienteId != null) {
                paciente = pacienteDao.getById(pacienteId);
                fillForm();
            }
        }
    }

    private void componentsInit() {
        imgFoto = (ImageView) findViewById(R.id.img_foto_cadpaciente);
        eTNome = (EditText) findViewById(R.id.et_nome_paciente);
        eTSobrenome = (EditText) findViewById(R.id.et_sobrenome_paciente);
        eTData = (EditText) findViewById(R.id.et_data_paciente);
        rbMasculino = (RadioButton) findViewById(R.id.rb_sexo_m);
        rbFeminino = (RadioButton) findViewById(R.id.rb_sexo_f);
    }

    private void fillForm() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        pacienteUtil = new PacienteUtil(paciente, this);

        uriFoto = paciente.getFoto();
        imgFoto.setImageURI(pacienteUtil.requestPacientePhoto());
        eTNome.setText(paciente.getNome());
        eTSobrenome.setText(paciente.getSobrenome());
        eTData.setText(sdf.format(paciente.getDataDeNascimento()));

        if(paciente.getSexo() == 'M') {
            sexo = 'M';
            rbMasculino.setChecked(true);
            rbFeminino.setChecked(false);
        } else {
            sexo = 'F';
            rbMasculino.setChecked(false);
            rbFeminino.setChecked(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            uriFoto = getImageUri().getPath();
            imgFoto.setImageURI(Uri.parse(uriFoto));
        }

        if (requestCode == LOAD_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            uriFoto = data.getDataString();
            Log.e("PATH: ", uriFoto);
            imgFoto.setImageURI(Uri.parse(uriFoto));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro_paciente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent = new Intent(this, PacientesActivity.class);
            startActivity(intent);
            finish();
        }

        if (id == R.id.ac_cancela_cadpaciente) {
            Intent intent = new Intent(this, PacientesActivity.class);
            startActivity(intent);
            finish();
        }

        if (id == R.id.ac_confirma_cadpaciente) {
            validateForm();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String day = dayOfMonth <= 10 ? "0" + dayOfMonth : dayOfMonth + "";
        String month = (monthOfYear + 1) <= 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1) + "";
        eTData.setText(day + "/" + month + "/" + year);
    }

    public void captureByCamera(View view) {
        uuid = UUID.randomUUID().toString();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void loadImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/jpg");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Picture"),
                LOAD_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    private Uri getImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM", uuid + ".jpg");
        return Uri.fromFile(file);
    }

    public void getDateByDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rb_sexo_m:
                if (checked)
                    sexo = ((RadioButton) view).getText().charAt(0);
                break;
            case R.id.rb_sexo_f:
                if (checked)
                    sexo = ((RadioButton) view).getText().charAt(0);
                break;
        }
    }

    private void validateForm() {

        String dateMacthes = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))" +
                "\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})" +
                "$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])" +
                "|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])" +
                "|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";

        boolean b0 = ValidateForm.validadeField(eTNome, eTSobrenome);
        boolean b1 = ValidateForm.validadeField(new String[]{dateMacthes}, eTData);
        boolean b2 = sexo != 'E';

        if (b0 && b1 && b2) {
            Date dataNasc = DateUtil.parseStringToDate(eTData.getText().toString(), "dd/MM/yyyy");
            int b3 = DateUtil.compareDates(dataNasc, new Date());
            if (b3 != 1) {
                paciente.setNome(eTNome.getText().toString());
                paciente.setSobrenome(eTSobrenome.getText().toString());
                paciente.setSexo(sexo);
                paciente.setDataDeNascimento(dataNasc);
                paciente.setFoto(uriFoto);

                new WarningDialogHelper(this, getString(R.string.confirmar),
                        getString(R.string.confirmar_cadastro_paciente), getString(R.string.sim),
                        getString(R.string.nao), savarPaciente(), WarningDialogHelper.getEmpityAction(),
                        WarningDialogHelper.NO_ICON);
            } else {
                new WarningDialogHelper(this, getString(R.string.alerta),
                        getString(R.string.alerta_data_futura), getString(R.string.ok),
                        WarningDialogHelper.getEmpityAction(), R.drawable.ic_action_warning);
            }
        } else {
            new WarningDialogHelper(this, getString(R.string.alerta),
                    getString(R.string.alerta_campos_vazios), getString(R.string.ok),
                    WarningDialogHelper.getEmpityAction(), R.drawable.ic_action_warning);
        }
    }

    private Runnable savarPaciente() {
        return new Runnable() {
            @Override
            public void run() {

                if(paciente.getId() != 0) {
                    pacienteDao.update(paciente);
                } else {
                    pacienteDao.create(paciente);
                }

                Toast.makeText(getApplicationContext(), getString(R.string.expressao_paciente) + " "
                        + paciente.getNome() + " " + getString(R.string.expressao_salvar),
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), PacientesActivity.class);
                Log.e("Paciente Log:", paciente.toString());
                startActivity(intent);
                finish();
            }
        };
    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                    (CadastroPacienteActivity) getActivity(), year, month, day);

            dialog.getDatePicker().setMaxDate(new Date().getTime());

            // Create a new instance of DatePickerDialog and return it
            return dialog;
        }
    }
}
