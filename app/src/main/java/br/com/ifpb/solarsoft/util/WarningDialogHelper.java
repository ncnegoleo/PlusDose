package br.com.ifpb.solarsoft.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Esta classe serve como ferramenta para a criação de um "Confrim Dialog"
 * onde chamará um diálogo apresentando uma pergunta e duas respostas que para
 * cada uma ocorrerá uma ação.
 *
 * @author leonardo.soares.ws@gmail.com
 */
public class WarningDialogHelper {

    public static int NO_ICON = 0;

    private AlertDialog dlg;
    private Activity activity;
    private String title;
    private String message;
    private String positiveButtonText;
    private String negativeButtonText;
    private int iconId = 0;
    // indica os procedimentos que ocoreão nas duas respostas
    private Runnable aProced = null;
    private Runnable bProced = null;

    /**
     * Construtor, que configura cria e mostra o dialogo e seus procedimentos.
     * @param activity activity no qual o dialogo vai ser gerado.
     * @param title titulo do alerta
     * @param message mensagem do alerta
     * @param positiveButtonText texto do botão que cancela
     * @param negativeButtonText texto do botão que confirma
     * @param aProced procedimento a
     * @param bProced procedimento b
     * @param iconId id do icone do alerta
     */
    public WarningDialogHelper(Activity activity, String title, String message, String positiveButtonText,
                               String negativeButtonText, Runnable aProced, Runnable bProced, int iconId) {
        this.activity = activity;
        this.title = title;
        this.message = message;
        this.positiveButtonText = positiveButtonText;
        this.negativeButtonText = negativeButtonText;
        this.aProced = aProced;
        this.bProced = bProced;
        this.iconId = iconId;
        getConfirmDialog(this.positiveButtonText, this.negativeButtonText,
                this.aProced, this.bProced, iconId);
    }

    /**
     *Construtor, que configura cria e mostra o dialogo e seus procedimentos.
     */
    public WarningDialogHelper(Activity activity, String title, String message, String positiveButtonText,
                               Runnable aProced, int iconId) {
        this.activity = activity;
        this.title = title;
        this.message = message;
        this.positiveButtonText = positiveButtonText;
        this.aProced = aProced;
        this.iconId = iconId;
        getAlertDialog(this.positiveButtonText, this.aProced, this.iconId);
    }


    private void getConfirmDialog(String positiveButtonText, String negativeButtonText,
                                  final Runnable aProced, final Runnable bProced, int iconId) {
        // configura e cria o dialogo
        dlg = new AlertDialog.Builder(this.activity)
                .setTitle(this.title) // titulo
                .setMessage(this.message) // mensagem
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    // procedimento a
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aProced.run();
                    }
                })
                .setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                    // procedimento b
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bProced.run();
                    }
                })
                .setIcon(iconId)
                .create(); // cria o alerta

        // mostra o dialogo
        dlg.show();
    }

    private void getAlertDialog(String positiveButtonText, final Runnable aProced, int iconId) {
        // configura e cria o dialogo
        dlg = new AlertDialog.Builder(this.activity)
                .setTitle(this.title) // titulo
                .setMessage(this.message) // mensagem
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    // procedimento a
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        aProced.run();
                    }
                })
                .setIcon(iconId)
                .create(); // cria o alerta

        // mostra o dialogo
        dlg.show();
    }

    public static Runnable getEmpityAction() {
        return new Runnable() {
            @Override
            public void run() {
                // TODO return empty action
            }
        };
    }

    /**
     * Retorna o dialogo criado.
     *
     * @return o dialogo criado.
     */
    public AlertDialog getDialog() {
        return dlg;
    }
}